package org.exodusstudio.arcana.common.block.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.exodusstudio.arcana.client.sound.WeepingSoundInstance;
import org.exodusstudio.arcana.common.registry.BlockEntityRegistry;
import org.exodusstudio.arcana.common.registry.SoundsRegistry;


public class WeepingPetalEntity extends BlockEntity {

    private int weepingDelay = 0;
    private int nextWeepDelay = 100;
    private boolean musicStopped = false;
    private WeepingSoundInstance currentSound;



    public WeepingPetalEntity(BlockPos pos, BlockState blockState) {
        super(BlockEntityRegistry.WEEPING_PETAL_BE.get(), pos, blockState);
    }


    public static void serverTick(Level level, BlockPos blockPos, BlockState blockState, WeepingPetalEntity be){


    }

    public static void clientTick(Level level, BlockPos blockPos, BlockState blockState, WeepingPetalEntity be){
        be.weepingDelay++;

        if (be.weepingDelay >= be.nextWeepDelay){
            float pitch = 0.9f + level.random.nextFloat() * 0.2f;
            float volume = 0.8f + level.random.nextFloat() * 0.4f;

            be.currentSound = new WeepingSoundInstance(SoundsRegistry.WEEPING.get(), SoundSource.BLOCKS, 2.0f, pitch, RandomSource.create(1), blockPos);
            Minecraft.getInstance().getSoundManager().play(be.currentSound);

            be.nextWeepDelay = 200 + level.random.nextInt(100);
            be.weepingDelay = 0;
        }

        if (Minecraft.getInstance().player == null) return;

        double distanceSqr = Minecraft.getInstance().player.distanceToSqr(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
        double stopDistance = 7.0;

        if (distanceSqr < stopDistance * stopDistance && !be.musicStopped){
            Minecraft.getInstance().getMusicManager().stopPlaying();
            be.musicStopped = true;
        }

        if (distanceSqr >= stopDistance * stopDistance && be.musicStopped){
            Minecraft.getInstance().getMusicManager().tick();
            be.musicStopped = false;
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        if (level != null && level.isClientSide() && currentSound != null){
            Minecraft.getInstance().getSoundManager().stop(currentSound);
            currentSound = null;
        }
        if (this.musicStopped){
            Minecraft.getInstance().getMusicManager().stopPlaying();
            this.musicStopped = false;
        }
    }
}
