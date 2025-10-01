package org.exodusstudio.arcana.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.block.entity.ResearchTableEntity;
import org.exodusstudio.arcana.common.data_attachment.PlayerAttachmentHandler;
import org.exodusstudio.arcana.common.registry.DataComponentRegistry;
import org.exodusstudio.arcana.common.registry.ItemRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

public class ResearchTable extends BaseEntityBlock {
    public static final EnumProperty<RT_State> RT_ACTIVATED;
    public static final EnumProperty<Direction> FACING;
    public static final MapCodec<ResearchTable> CODEC = simpleCodec(ResearchTable::new);
    private boolean IsCompleted = false;
    private BlockPos masterPos = null;
    private static final VoxelShape TableShape = Block.box(0, 0,0, 16, 14, 16);

    @Override
    protected @NotNull VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return TableShape;
    }

    public ResearchTable() {
        this(Properties.ofFullCopy(Blocks.BAMBOO_BLOCK)
                .strength(2.0f)
                .sound(SoundType.WOOD));
    }

    public ResearchTable(Properties properties) {
        super(properties);
        registerDefaultState(this.defaultBlockState()
                .setValue(RT_ACTIVATED, RT_State.OFF));
    }

    @Override
    protected @NotNull InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if(level.isClientSide()) return InteractionResult.SUCCESS;

        Item item = stack.getItem();
        if(item == ItemRegistry.SCRIBBLING_TOOL.get() && state.getValue(RT_ACTIVATED) == RT_State.OFF)
        {
            useScribblingToolOn(state, level, pos, player);
            return InteractionResult.SUCCESS;
        }

        if(state.getValue(RT_ACTIVATED) != RT_State.OFF && item == ItemRegistry.SCRIBBLED_NOTE.get())
        {
            useScribbledNoteOn(stack, level, pos, player);
            return InteractionResult.SUCCESS;
        }

        if((level.getBlockEntity(pos) instanceof ResearchTableEntity researchTableEntity) && stack.isEmpty() && !level.isClientSide())
        {
            ((ServerPlayer) player).openMenu(new SimpleMenuProvider(researchTableEntity, Component.literal("Research Table")), pos);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.SUCCESS;
    }

    static {
        RT_ACTIVATED = EnumProperty.create("rt_activated", RT_State.class);
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }

    protected void useScribblingToolOn(BlockState state, Level level, BlockPos pos, Player player)
    {
        //This should return a list with the new state (OFF, ON_LEFT, ON_RIGHT), the position of the other block and its direction
        List<Object> listReturn = researchTableAround(level, pos, state);
        RT_State tableState = (RT_State) listReturn.getFirst();
        if(tableState != RT_State.OFF)
        {
            BlockPos neighBlockPos = (BlockPos) listReturn.get(1);
            Direction newDirection = (Direction) listReturn.get(2);

            //Turn our block
            if(newDirection != state.getValue(FACING))
            {
                state = state.setValue(FACING, newDirection);
            }

            //Change the RT_ACTIVATED values;
            RT_State otherTableState = tableState == RT_State.ON_LEFT ? RT_State.ON_RIGHT : RT_State.ON_LEFT;
            BlockState selectedState, neighState;
            selectedState = state.setValue(RT_ACTIVATED, (RT_State)listReturn.getFirst());
            neighState = state.setValue(RT_ACTIVATED, otherTableState);
            level.setBlock(pos, selectedState, 3);
            level.setBlock(neighBlockPos, neighState, 3);
            IsCompleted = true;
            //The block selected is always the slave one.
            masterPos = neighBlockPos;
        }
        else
        {
            player.displayClientMessage(Component.translatable("arcana.message.research_table_not_found"), true);
        }
    }

    protected void useScribbledNoteOn(ItemStack stack, Level level, BlockPos pos, Player player)
    {
        BlockPos masterRealPos = masterPos == null ? pos : masterPos;
        if(level.getBlockEntity(masterRealPos) instanceof ResearchTableEntity researchTableEntity)
        {
            String researchName = stack.get(DataComponentRegistry.SCRIBBLED_NOTE_DATA).researchName();
            Arcana.LOGGER.info("Trying to study new research : " + researchName);
            if(PlayerAttachmentHandler.GetSpecificKnowledgeProgression(player, researchName) != 0)
            {
                player.displayClientMessage(Component.translatable("arcana.message.research_table_note_found"), true);
                return;
            }

            PlayerAttachmentHandler.UpdateKnowledgeProgression(player, researchName, 1);
            Arcana.LOGGER.info("I studied " + researchName);
            level.playSound(player, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1f, 1f);
            stack.shrink(1);
            return;
        }
    }


    @Override
    public @NotNull BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        RT_State rtState = state.getValue(RT_ACTIVATED);

        if (rtState != RT_State.OFF) {
            Direction facing = state.getValue(FACING);

            //Look for the other block
            BlockPos linkedPos = (rtState == RT_State.ON_LEFT) ? pos.relative(facing.getClockWise()) : pos.relative(facing.getCounterClockWise());
            BlockState linkedState = level.getBlockState(linkedPos);

            //Basic bug checks
            if (linkedState.getBlock() instanceof ResearchTable
                    && linkedState.getValue(RT_ACTIVATED) != RT_State.OFF
                    && linkedState.getValue(RT_ACTIVATED) != rtState) {

                //Remove the other block TODO : Check if it makes bug with the notes in it (remove = destroy ??)
                level.setBlock(linkedPos, Blocks.AIR.defaultBlockState(), 3);
                Block.popResource(level, linkedPos, new ItemStack(this));
                level.gameEvent(GameEvent.BLOCK_DESTROY, linkedPos, GameEvent.Context.of(player, linkedState));
            }

        }

        //Then destroy the current one
        super.playerWillDestroy(level, pos, state, player);

        return state;
    }

    private List<Object> researchTableAround(Level level, BlockPos currentPos, BlockState blockState)
    {
        Direction facing = blockState.getValue(FACING);
        BlockPos[] blockPos;
        //All directions, considering all the facing direction (16 cases)
        switch (facing)
        {
            case Direction.NORTH:
                if(isBlockResearchTable(level, currentPos.east()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.east(), Direction.NORTH);
                if(isBlockResearchTable(level, currentPos.west()))
                    return Arrays.asList(RT_State.ON_RIGHT, currentPos.west(), Direction.NORTH);
                if(isBlockResearchTable(level, currentPos.north()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.north(), Direction.WEST);
                if(isBlockResearchTable(level, currentPos.south()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.south(), Direction.EAST);

            case Direction.SOUTH:
                if(isBlockResearchTable(level, currentPos.east()))
                    return Arrays.asList(RT_State.ON_RIGHT, currentPos.east(), Direction.SOUTH);
                if(isBlockResearchTable(level, currentPos.west()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.west(), Direction.SOUTH);
                if(isBlockResearchTable(level, currentPos.north()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.north(), Direction.WEST);
                if(isBlockResearchTable(level, currentPos.south()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.south(), Direction.EAST);

            case Direction.EAST:
                if(isBlockResearchTable(level, currentPos.north()))
                    return Arrays.asList(RT_State.ON_RIGHT, currentPos.north(), Direction.EAST);
                if(isBlockResearchTable(level, currentPos.south()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.south(), Direction.EAST);
                if(isBlockResearchTable(level, currentPos.east()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.east(), Direction.NORTH);
                if(isBlockResearchTable(level, currentPos.west()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.west(), Direction.SOUTH);

            case Direction.WEST:
                if(isBlockResearchTable(level, currentPos.north()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.north(), Direction.WEST);
                if(isBlockResearchTable(level, currentPos.south()))
                    return Arrays.asList(RT_State.ON_RIGHT, currentPos.south(), Direction.WEST);
                if(isBlockResearchTable(level, currentPos.east()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.east(), Direction.NORTH);
                if(isBlockResearchTable(level, currentPos.west()))
                    return Arrays.asList(RT_State.ON_LEFT, currentPos.west(), Direction.SOUTH);

        }
        return List.of(RT_State.OFF);
    }

    private boolean isBlockResearchTable(Level level, BlockPos pos)
    {
        BlockState adjacentState = level.getBlockState(pos);
        return  adjacentState.getBlock() instanceof ResearchTable && adjacentState.getValue(RT_ACTIVATED) == RT_State.OFF;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState()
                .setValue(RT_ACTIVATED, RT_State.OFF)
                .setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(RT_ACTIVATED);
        builder.add(FACING);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ResearchTableEntity(blockPos, blockState);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean movedByPiston) {
        if(state.getBlock() != level.getBlockState(pos).getBlock()) {
            if(level.getBlockEntity(pos) instanceof ResearchTableEntity) {
                level.updateNeighbourForOutputSignal(pos, this);
            }
        }

        super.affectNeighborsAfterRemoval(state, level, pos, movedByPiston);
    }
}
