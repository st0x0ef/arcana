package org.exodusstudio.arcana.common.world;

import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.data.Structure;
import org.exodusstudio.arcana.common.event.ArcanaRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class Structures {
    public static final ResourceKey<Structure> AMORPHOUS = key("amorphous");
    public static final ResourceKey<Structure> PULVERIZED = key("pulverized");
    public static final ResourceKey<Structure> PULVERIZED_ROUGH = key("pulverized_rough");
    public static final ResourceKey<Structure> NON_NEWTONIAN = key("non_newtonian");
    public static final ResourceKey<Structure> CRYSTALLINE = key("crystalline");
    public static final ResourceKey<Structure> SUPER_SATURATED = key("super_saturated");
    public static final ResourceKey<Structure> HIGHLY_VISCOUS = key("highly_viscous");

    public static void bootstrap(BootstrapContext<Structure> context) {
        context.register(AMORPHOUS, new Structure("arcana.structure.amorphous"));
        context.register(PULVERIZED, new Structure("arcana.structure.pulverized"));
        context.register(PULVERIZED_ROUGH, new Structure("arcana.structure.pulverized_rough"));
        context.register(NON_NEWTONIAN, new Structure("arcana.structure.non_newtonian"));
        context.register(CRYSTALLINE, new Structure("arcana.structure.crystalline"));
        context.register(SUPER_SATURATED, new Structure("arcana.structure.super_saturated"));
        context.register(HIGHLY_VISCOUS, new Structure("arcana.structure.highly_viscous"));
    }

    private static ResourceKey<Structure> key(String id) {
        return ResourceKey.create(ArcanaRegistries.STRUCTURE_KEY, Arcana.id(id));
    }
}
