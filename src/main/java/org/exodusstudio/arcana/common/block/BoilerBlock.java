package org.exodusstudio.arcana.common.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.transfer.item.ItemResource;
import org.exodusstudio.arcana.common.block.entity.BoilerBlockEntity;
import org.exodusstudio.arcana.common.capabilities.ModCapabilities;
import org.exodusstudio.arcana.common.inventory.BoilerInventory;
import org.exodusstudio.arcana.common.registry.BlockEntityRegistry;
import org.jetbrains.annotations.Nullable;

public class BoilerBlock extends BaseEntityBlock {
    public static final BooleanProperty BOILING = BooleanProperty.create("boiling");
    public static final IntegerProperty WATER_LEVEL = IntegerProperty.create("water_level", 0, 3);

    public static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0);
    public static final MapCodec<BoilerBlock> CODEC = simpleCodec(BoilerBlock::new);

    public BoilerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(getStateDefinition().any().setValue(BOILING, false));
        this.registerDefaultState(getStateDefinition().any().setValue(WATER_LEVEL, 0));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BOILING);
        builder.add(WATER_LEVEL);
    }

    public BlockEntity newBlockEntity(BlockPos pos, BlockState state){
        return new BoilerBlockEntity(pos, state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
/*
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
*/
    /* Block Entity */

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return !level.isClientSide() ? createTickerHelper(blockEntityType, BlockEntityRegistry.BOILER_BE.get(), BoilerBlockEntity::serverTick) : null;
    }


    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (level.getBlockEntity(pos) instanceof BoilerBlockEntity boilerBlockEntity) {
            BoilerInventory inventory = boilerBlockEntity.getInventory();
            ItemResource resource = ItemResource.of(stack);

            if (!stack.isEmpty() && !stack.is(Items.WATER_BUCKET)){
                if (!level.isClientSide()){
                    for (int i = 0; i < inventory.getSlots(); i++) {
                        ItemStack current = inventory.getStackInSlot(i);
                        if (!current.isEmpty() && ItemStack.isSameItemSameComponents(current, stack)){
                            if (current.getCount() >= inventory.getMaxStackSize()){
                                System.out.println("slot " + i + " is full");
                                return InteractionResult.SUCCESS;
                            }
                        }
                        int inserted = inventory.insert(i, resource, 1, null);
                        if (inserted > 0){
                            stack.shrink(inserted);
                            level.playSound(null, pos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 2, 1);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }

                return InteractionResult.SUCCESS;
            }

            if (stack.is(Items.WATER_BUCKET) && boilerBlockEntity.getWaterAmmount() < 3){
                if (!level.isClientSide()) {
                    boilerBlockEntity.addWater(3);
                    if (!player.isCreative()) player.setItemInHand(hand, new ItemStack(Items.BUCKET));
                    level.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 2, 1);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

}


