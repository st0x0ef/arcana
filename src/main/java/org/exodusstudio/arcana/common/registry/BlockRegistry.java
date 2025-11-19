package org.exodusstudio.arcana.common.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.exodusstudio.arcana.Arcana;

//import org.exodusstudio.arcana.common.block.NimbusStone;
import org.exodusstudio.arcana.common.block.*;
import org.exodusstudio.arcana.common.block.water_lillie.lillie_block;
import org.exodusstudio.arcana.common.block.water_lillie.lily_padBlock;
import org.exodusstudio.arcana.common.block.water_lillie.sunken_lillie;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class BlockRegistry {

    //Eliminated SetId now neoforge handles it automatixally

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(Arcana.MODID);

    private static final Map<DeferredBlock<?>, Supplier<Item>> BLOCK_TO_ITEM = new HashMap<>();


    public static final DeferredBlock<ResearchTable> RESEARCH_TABLE = registerSpecificBlock("research_table",
            ResearchTable::new,
            props -> props.strength(5.0F, 6.0F).requiresCorrectToolForDrops().noOcclusion(),
            props -> props.stacksTo(1));

    public static final DeferredBlock<NitorBlock> NITOR_BLOCK = registerSpecificBlock("nitor_block",
            NitorBlock::new,
            props -> props.noCollision().instabreak().noOcclusion(),
            props -> props.stacksTo(1));

    public static final DeferredBlock<BoilerBlock> BOILER_BLOCK = registerSpecificBlock("boiler",
            BoilerBlock::new,
            props -> props.strength(5.3f, 6.0f).noOcclusion(),
            props -> props.stacksTo(1));

    public static final DeferredBlock<WeepingPetal> WEEPING_PETAL = registerSpecificBlock("weeping_petal",
            WeepingPetal::new,
            props -> props.instabreak().noOcclusion(),
            props -> props.stacksTo(1));

    public static final DeferredBlock<sunken_lillie> SUNKEN_LILLIE = registerSpecificBlock("sunken_lillie",
            sunken_lillie::new,
            p -> p.instabreak().pushReaction(PushReaction.DESTROY).noOcclusion(),
            props -> props);
    public static final DeferredBlock<FirePoppy> FIRE_POPPY = registerSpecificBlock("fire_poppy",
            FirePoppy::new,
            props -> props.instabreak().lightLevel(state -> state.getValue(FirePoppy.AWAKE) ? 0 : 8).noCollision(),
            props -> props.stacksTo(1));

    public static final DeferredBlock<lillie_block> LILLIE_BLOCK = registerSpecificBlock("lillie",
            props -> new lillie_block(SUNKEN_LILLIE, props),
            P -> P.noOcclusion().pushReaction(PushReaction.DESTROY),
            props -> props);
    public static final DeferredBlock<lily_padBlock> LILY_PAD_BLOCK = registerSpecificBlock("lily_pad_block",
            lily_padBlock::new,
            p -> p.instabreak().noOcclusion(),
            p -> p.stacksTo(1)
            );


    //public static final DeferredBlock<NimbusStone> NIMBUS_STONE = registerSpecificBlock("nimbus_stone", NimbusStone.class,
    //        BlockBehaviour.Properties.of().strength(2.0f, 1.0f).requiresCorrectToolForDrops().noOcclusion(),
    //        new Item.Properties().stacksTo(64));



    private static <T extends Block> DeferredBlock<T> registerSpecificBlock(
            String name,
            Function<BlockBehaviour.Properties, T> blockConstructor,
            UnaryOperator<BlockBehaviour.Properties> blockProperties,
            UnaryOperator<Item.Properties> itemProperties
    ) {
        DeferredBlock<T> toReturn = BLOCKS.registerBlock(name, blockConstructor, blockProperties);
        Supplier<Item> itemSupplier = registerBlockItem(name, toReturn, itemProperties);
        BLOCK_TO_ITEM.put(toReturn, itemSupplier);
        return toReturn;
    }

    private static DeferredBlock<Block> registerBasicBlock(
            String name,
            UnaryOperator<BlockBehaviour.Properties> blockProperties,
            UnaryOperator<Item.Properties> itemProperties
    ) {
        DeferredBlock<Block> toReturn = BLOCKS.registerBlock(name, Block::new, blockProperties);
        Supplier<Item> itemSupplier = registerBlockItem(name, toReturn, itemProperties);
        BLOCK_TO_ITEM.put(toReturn, itemSupplier);
        return toReturn;
    }

    private static <T extends Block> Supplier<Item> registerBlockItem(
            String name,
            DeferredBlock<T> block,
            UnaryOperator<Item.Properties> itemProperties
    ) {
        DeferredItem<BlockItem> deferredItem = ItemRegistry.ITEMS.registerSimpleBlockItem(name, block::get, itemProperties);
        return deferredItem::get;
    }

    public static void register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }

    public static Supplier<Item> getItemFromBlock(DeferredBlock<?> block){
        return BLOCK_TO_ITEM.getOrDefault(block, () -> null);
    }
}
