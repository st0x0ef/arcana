package org.exodusstudio.arcana.common.particle;


import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.exodusstudio.arcana.Arcana;

import java.util.function.Supplier;

public class ParticleRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
            DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Arcana.MODID);


    public static final Supplier<SimpleParticleType> EVAPORATION_PARTICLE =
            PARTICLE_TYPES.register("evaporation_particle", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> BOILING_PARTICLE =
            PARTICLE_TYPES.register("boiling_particle", () -> new SimpleParticleType(true));

    public static final Supplier<SimpleParticleType> RESEARCH_PARTICLE =
            PARTICLE_TYPES.register("research_particle", () -> new SimpleParticleType(true));

    public static void register(IEventBus eventBus){
        PARTICLE_TYPES.register(eventBus);
    }
}

