package org.exodusstudio.arcana.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class ResearchParticle extends SingleQuadParticle {

    private final SpriteSet spriteSet;
    public ResearchParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet.get(random));

        this.gravity = 0.2f;
        this.lifetime = 60;
        this.friction = 0.9f;
        this.setSpriteFromAge(spriteSet);
        this.spriteSet = spriteSet;

    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel,
                                                 double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed, RandomSource random) {
            return new ResearchParticle(clientLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed, random);
        }
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
    }
}
