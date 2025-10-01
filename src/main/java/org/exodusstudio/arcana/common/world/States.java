package org.exodusstudio.arcana.common.world;

import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.data.State;
import org.exodusstudio.arcana.common.event.ArcanaRegistries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

public class States {
    public static final ResourceKey<State> SOLID = key("solid");
    public static final ResourceKey<State> LIQUID = key("liquid");
    public static final ResourceKey<State> GASEOUS = key("gaseous");

    public static void bootstrap(BootstrapContext<State> context) {
        context.register(SOLID, new State("arcana.state.solid"));
        context.register(LIQUID, new State("arcana.state.liquid"));
        context.register(GASEOUS, new State("arcana.state.gaseous"));
    }

    private static ResourceKey<State> key(String id) {
        return ResourceKey.create(ArcanaRegistries.STATE_KEY, Arcana.id(id));
    }
}
