package org.exodusstudio.arcana.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.exodusstudio.arcana.common.block.entity.FirePoppyEntity;
import org.exodusstudio.arcana.common.block.entity.WeepingPetalEntity;
import org.exodusstudio.arcana.common.registry.BlockEntityRegistry;
import org.jetbrains.annotations.Nullable;


public class WeepingPetal extends BaseEntityBlock {
    public WeepingPetal(Properties properties){super(properties);}

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(WeepingPetal::new);
    }

    public static final VoxelShape SHAPE = Block.box(6.0, 0.0, 5.0, 10.0, 14.0, 10.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        if (blockEntityType != BlockEntityRegistry.WEEPING_PETAL_BE.get()) return null;

        return level.isClientSide()
                ? (lvl, pos, st, be) -> WeepingPetalEntity.clientTick(lvl, pos, st, (WeepingPetalEntity) be)
                : (lvl, pos, st, be) -> WeepingPetalEntity.serverTick(lvl, pos, st, (WeepingPetalEntity) be);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new WeepingPetalEntity(blockPos, blockState);
    }
}
