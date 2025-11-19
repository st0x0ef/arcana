package org.exodusstudio.arcana.common.block.water_lillie;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.registries.DeferredBlock;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class lillie_block extends Block {

    private final DeferredBlock<sunken_lillie> waterLillieBlock;

    public lillie_block(DeferredBlock<sunken_lillie> waterLilie, Properties properties) {
        super(properties);
        waterLillieBlock = waterLilie;
    }



    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if(this.scanForWater(level, pos)){
            level.setBlock(pos, this.waterLillieBlock.get().defaultBlockState(), 3);
        }

    }


    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean movedByPiston) {
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 20);
        }
    }

    @Override
    protected BlockState updateShape(BlockState state, LevelReader level, ScheduledTickAccess scheduledTickAccess, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, RandomSource random) {

        if(this.scanForWater(level, pos)){
            scheduledTickAccess.scheduleTick(pos, this, 20);
        }

        return super.updateShape(state, level, scheduledTickAccess, pos, direction, neighborPos, neighborState, random);
    }

    protected boolean scanForWater(BlockGetter level, BlockPos pos) {
        for (Direction dir : Direction.values()) {
            if (dir == Direction.DOWN) continue; // skip DOWN

            FluidState fluid = level.getFluidState(pos.relative(dir));

            // If ANY side is NOT water → fail
            if (!fluid.is(FluidTags.WATER)) {
                return false;
            }
        }

        // All checked directions have water
        return true;
    }
}
