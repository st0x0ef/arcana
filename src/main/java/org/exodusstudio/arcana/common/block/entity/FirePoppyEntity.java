package org.exodusstudio.arcana.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.exodusstudio.arcana.common.block.FirePoppy;
import org.exodusstudio.arcana.common.particle.ParticleRegistry;
import org.exodusstudio.arcana.common.registry.BlockEntityRegistry;

public class FirePoppyEntity extends BlockEntity {

    private int delayValue = 0;

    public FirePoppyEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.FIRE_POPPY_BE.get(), pos, blockState);
    }

    public static void spawnParticles(BlockPos pos, Level level){
        ((ServerLevel) level).sendParticles(ParticleRegistry.FIRE_PARTICLE.get(),
                pos.getX() + 0.5, pos.getY() + 0.72, pos.getZ() + 0.5, 1, 0, 0, 0, 0.005);
    }

    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, FirePoppyEntity be){

        boolean isDayTime = level.getDayTime() % 24000 < 13200;
        boolean isNightTime = level.getDayTime() % 24000 >= 13200;
        boolean isSkyVisible = level.canSeeSky(blockPos.above());

        boolean isAwake = isDayTime && isSkyVisible;

        if (blockState.getValue(FirePoppy.AWAKE) != isAwake){
            level.setBlock(blockPos, blockState.setValue(FirePoppy.AWAKE, isAwake), Block.UPDATE_ALL);
        }

        if (!isAwake){
            be.delayValue++;
            boolean flag = be.delayValue % 2 == 0;
            if (flag){
                spawnParticles(blockPos, level);
            }
            if (be.delayValue >= 1000){be.delayValue = 0;}
        }
    }
}
