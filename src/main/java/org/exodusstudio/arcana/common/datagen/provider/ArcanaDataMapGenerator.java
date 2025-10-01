package org.exodusstudio.arcana.common.datagen.provider;

import org.exodusstudio.arcana.common.data.State;
import org.exodusstudio.arcana.common.data.Structure;
import org.exodusstudio.arcana.common.datamap.ArcanaDataHolder;
import org.exodusstudio.arcana.common.datamap.ArcanaDataMaps;
import org.exodusstudio.arcana.common.event.ArcanaRegistries;
import org.exodusstudio.arcana.common.world.States;
import org.exodusstudio.arcana.common.world.Structures;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.DataMapProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class ArcanaDataMapGenerator extends DataMapProvider {
    public ArcanaDataMapGenerator(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        HolderGetter<State> states = provider.lookupOrThrow(ArcanaRegistries.STATE_KEY);
        HolderGetter<Structure> structures = provider.lookupOrThrow(ArcanaRegistries.STRUCTURE_KEY);

        Builder<ArcanaDataHolder, Item> holders = builder(ArcanaDataMaps.DATA_HOLDER);

        holders.add(Items.COBBLESTONE.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.STONE.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.GRASS_BLOCK.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID))
        ), false);
        holders.add(Items.DIRT.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID))
        ), false);
        holders.add(Items.GRAVEL.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.PULVERIZED_ROUGH))
        ), false);
        holders.add(Items.GLASS.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.MAGMA_BLOCK.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.NETHERRACK.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.SOUL_SAND.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.NON_NEWTONIAN))
        ), false);
        holders.add(Items.BONE_BLOCK.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.END_STONE.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.LAPIS_LAZULI.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.REDSTONE.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.PULVERIZED))
        ), false);
        holders.add(Items.REDSTONE_BLOCK.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.NETHERITE_INGOT.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.COAL.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.STONE.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.GLOWSTONE.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.GLOWSTONE_DUST.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.PULVERIZED))
        ), false);
        holders.add(Items.SAND.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.PULVERIZED), structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.PRISMARINE_SHARD.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.SNOW.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.PULVERIZED), structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.ICE.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.OBSIDIAN.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.DIAMOND.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.EMERALD.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.GOLD_INGOT.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.IRON_INGOT.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.QUARTZ.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.AMETHYST_SHARD.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.COPPER_INGOT.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
        holders.add(Items.BLAZE_ROD.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.BLAZE_POWDER.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.PULVERIZED))
        ), false);
        holders.add(Items.BONE_MEAL.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.PULVERIZED))
        ), false);
        holders.add(Items.SUGAR.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.PULVERIZED))
        ), false);
        holders.add(Items.GUNPOWDER.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.PULVERIZED))
        ), false);
        holders.add(Items.WATER_BUCKET.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.LIQUID))
        ), false);
        holders.add(Items.LAVA_BUCKET.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.LIQUID))
        ), false);
        holders.add(Items.MILK_BUCKET.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.LIQUID))
        ), false);
        holders.add(Items.SLIME_BALL.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.LIQUID)),
                List.of(structures.getOrThrow(Structures.SUPER_SATURATED), structures.getOrThrow(Structures.HIGHLY_VISCOUS))
        ), false);
        holders.add(Items.MAGMA_CREAM.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.LIQUID)),
                List.of(structures.getOrThrow(Structures.HIGHLY_VISCOUS))
        ), false);
        holders.add(Items.BONE.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.FLINT.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.BREEZE_ROD.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.AMORPHOUS))
        ), false);
        holders.add(Items.CLAY_BALL.builtInRegistryHolder(), new ArcanaDataHolder(
                List.of(states.getOrThrow(States.SOLID)),
                List.of(structures.getOrThrow(Structures.CRYSTALLINE))
        ), false);
    }
}
