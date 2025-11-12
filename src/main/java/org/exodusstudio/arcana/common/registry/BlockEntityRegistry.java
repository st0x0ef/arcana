package org.exodusstudio.arcana.common.registry;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.block.entity.BoilerBlockEntity;
import org.exodusstudio.arcana.common.block.entity.ResearchTableEntity;

import java.util.function.Supplier;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Arcana.MODID);

    public static final Supplier<BlockEntityType<ResearchTableEntity>> RESEARCH_TABLE_BE =
            BLOCK_ENTITIES.register("research_table_be",
                    () -> new BlockEntityType<>(ResearchTableEntity::new, BlockRegistry.RESEARCH_TABLE.get())
            );
    public static final Supplier<BlockEntityType<BoilerBlockEntity>> BOILER_BE =
            BLOCK_ENTITIES.register("boiler_be", () -> new BlockEntityType<>(BoilerBlockEntity::new, BlockRegistry.BOILER_BLOCK.get())
            );

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
