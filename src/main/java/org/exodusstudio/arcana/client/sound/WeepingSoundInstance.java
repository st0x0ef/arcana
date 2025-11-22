package org.exodusstudio.arcana.client.sound;

import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;

public class WeepingSoundInstance extends SimpleSoundInstance {
    public WeepingSoundInstance(SoundEvent soundEvent, SoundSource source, float volume, float pitch, RandomSource random, BlockPos pos) {
        super(soundEvent, SoundSource.BLOCKS, 2.0f, 1.0f, random, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f);
    }
}
