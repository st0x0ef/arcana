package org.exodusstudio.arcana.common.capabilities;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.fluid_storage.BoilerFluidStorage;
import org.exodusstudio.arcana.common.inventory.BoilerInventory;

public class ModCapabilities {
    public static final BlockCapability<BoilerInventory, Direction> BOILER_BLOCK_HANDLER =
            BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(Arcana.MODID, "boiler_item_handler"),
            BoilerInventory.class);

    public static final BlockCapability<BoilerFluidStorage, Direction> BOILER_FLUID_HANDLER =
            BlockCapability.createSided(ResourceLocation.fromNamespaceAndPath(Arcana.MODID, "boiler_fluid_handler"),
                    BoilerFluidStorage.class);

    public static void register(){

    }
}
