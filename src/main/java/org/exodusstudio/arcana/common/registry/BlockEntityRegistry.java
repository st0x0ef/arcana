package org.exodusstudio.arcana.common.registry;


import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.block.entity.BoilerBlockEntity;
import org.exodusstudio.arcana.common.block.entity.FirePoppyEntity;
import org.exodusstudio.arcana.common.block.entity.ResearchTableEntity;

import java.util.Set;
import java.util.function.Supplier;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Arcana.MODID);

    public static final Supplier<BlockEntityType<ResearchTableEntity>> RESEARCH_TABLE_BE =
            BLOCK_ENTITIES.register("research_table_be",
                    () -> new BlockEntityType<>(ResearchTableEntity::new, Set.of(BlockRegistry.RESEARCH_TABLE.get()))
            );
    public static final Supplier<BlockEntityType<BoilerBlockEntity>> BOILER_BE =
            BLOCK_ENTITIES.register("boiler_be",
                    () -> new BlockEntityType<>(BoilerBlockEntity::new, Set.of(BlockRegistry.BOILER_BLOCK.get()))
            );
    public static final Supplier<BlockEntityType<FirePoppyEntity>> FIRE_POPPY_BE =
            BLOCK_ENTITIES.register("fire_poppy_be",
                    () -> new BlockEntityType<>(FirePoppyEntity::new, Set.of(BlockRegistry.FIRE_POPPY.get()))
            );

    public static void register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
