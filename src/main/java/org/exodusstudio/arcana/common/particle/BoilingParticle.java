package org.exodusstudio.arcana.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class BoilingParticle extends SingleQuadParticle {
    public int i = 0;

    private final SpriteSet spriteSet;
    public BoilingParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet.get(random));

        this.gravity = -0.7f;
        this.lifetime = 30;
        this.spriteSet = spriteSet;
        this.friction = 0.8f;
        this.quadSize = 0.12f;
        this.setSize(0.02f, 0.02f);
        this.setSpriteFromAge(spriteSet);
        this.rCol = 0;
        this.gCol = 0;
        this.bCol = 0;
        yd = ySpeed;

    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }


    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet){
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel,
                                                 double pX, double pY, double pZ, double pXSPeed, double pYSpeed, double pZSpeed, RandomSource randomSource) {
            return new BoilingParticle(clientLevel, pX, pY, pZ, this.spriteSet, pXSPeed, pYSpeed, pZSpeed, randomSource);
        }
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
       while (this.level.getFluidState(BlockPos.containing(this.x, this.y , this.z)).is(FluidTags.WATER) && this.age > 28 && this.age < 30 &&
               this.level.getFluidState(BlockPos.containing(this.x, this.y + 0.1, this.z)).is(FluidTags.WATER))
       {
           this.age = this.age - 1;
           i = i + 1;
           if (i > 100) {
               this.age = 30;
           }
       }

        if (!this.level.getFluidState(BlockPos.containing(this.x, this.y + 0.1, this.z)).is(FluidTags.WATER) &&
                this.level.getFluidState(BlockPos.containing(this.x, this.y - 0.2, this.z)).is(FluidTags.WATER))
        {
            this.gravity = -0.001f;
            yd = 0f;
        }

        if (!this.removed && !this.level.getFluidState(BlockPos.containing(this.x, this.y, this.z)).is(FluidTags.WATER))
        {
            this.remove();
        }
    }
}
