package org.exodusstudio.arcana.common.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

public class FireParticle extends SingleQuadParticle {

    private final SpriteSet spriteSet;
    public FireParticle(ClientLevel level, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed, RandomSource random) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed, spriteSet.get(random));

        this.gravity = -0.02f;
        this.lifetime = 14;
        this.friction = 0.18f;
        this.setSpriteFromAge(spriteSet);
        this.quadSize = 0.08f;
        this.setSize(0.03f, 0.03f);
        this.spriteSet = spriteSet;
    }

    @Override
    protected Layer getLayer() {return Layer.OPAQUE;}

    public static class Provider implements ParticleProvider<SimpleParticleType>{
        private final SpriteSet spriteSet;
        public Provider(SpriteSet spriteSet) {this.spriteSet = spriteSet;}


        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel,
                                                 double v, double v1, double v2, double v3, double v4, double v5, RandomSource randomSource) {
            return new FireParticle(clientLevel, v, v1, v2, spriteSet, v3, v4, v5, randomSource);
        }
    }

    @Override
    public void tick() {
        this.setSpriteFromAge(spriteSet);
        super.tick();
    }
}
