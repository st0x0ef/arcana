package org.exodusstudio.arcana.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;


public class EvaporationParticle extends SingleQuadParticle {

    private final SpriteSet spriteSet;
    public EvaporationParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet.get(random));

        this.gravity = -0.5f;
        this.lifetime = 20;
        this.friction = 0.85f;
        this.setSpriteFromAge(spriteSet);
        this.quadSize = 0.4f;
        this.setSize(0.2f, 0.2f);
        this.spriteSet = spriteSet;
        this.rCol = 10;
        this.gCol = 10;
        this.bCol = 10;
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
            return new EvaporationParticle(clientLevel, pX, pY, pZ, this.spriteSet, pXSpeed, pYSpeed, pZSpeed, random);
        }
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
    }
}

