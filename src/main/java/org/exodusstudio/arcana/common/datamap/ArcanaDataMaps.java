package org.exodusstudio.arcana.common.datamap;

import org.exodusstudio.arcana.Arcana;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;

@EventBusSubscriber(modid = Arcana.MODID)
public class ArcanaDataMaps {
    public static final DataMapType<Item, ArcanaDataHolder> DATA_HOLDER = DataMapType.builder(
            Arcana.id("data_holder"), Registries.ITEM, ArcanaDataHolder.CODEC
    ).synced(ArcanaDataHolder.CODEC, false).build();

    @SubscribeEvent
    public static void register(RegisterDataMapTypesEvent event) {
        event.register(DATA_HOLDER);
    }
}
