package org.exodusstudio.arcana.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;


public class WeepingPetal extends Block {
    public WeepingPetal(Properties properties){super(properties);}

    public static final VoxelShape SHAPE = Block.box(6.0, 0.0, 5.0, 10.0, 14.0, 10.0);

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
