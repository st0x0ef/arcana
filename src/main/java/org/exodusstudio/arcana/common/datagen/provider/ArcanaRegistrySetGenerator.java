package org.exodusstudio.arcana.common.datagen.provider;

import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.event.ArcanaRegistries;
import org.exodusstudio.arcana.common.world.States;
import org.exodusstudio.arcana.common.world.Structures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ArcanaRegistrySetGenerator extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(ArcanaRegistries.STATE_KEY, States::bootstrap)
            .add(ArcanaRegistries.STRUCTURE_KEY, Structures::bootstrap);

    public ArcanaRegistrySetGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Arcana.MODID));
    }
}
