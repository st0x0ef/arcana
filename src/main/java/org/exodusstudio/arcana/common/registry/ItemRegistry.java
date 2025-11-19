package org.exodusstudio.arcana.common.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.exodusstudio.arcana.Arcana;
import org.exodusstudio.arcana.common.item.*;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Arcana.MODID);

    public static final DeferredItem<Item> SCRIBBLING_TOOL = ITEMS.registerItem(
            "scribbling_tool",
             ArcanaScribblingToolItem::new,
            P -> P.stacksTo(1));

    public static final DeferredItem<Item> SCRIBBLED_NOTE = ITEMS.registerItem(
            "scribbled_note",
            ArcanaScribbledNoteItem::new,
            P -> P.stacksTo(1));
    public static final DeferredItem<Item> OLD_NOTE = ITEMS.registerItem("old_note",
            ArcanaOldNoteItem::new,
            P -> P.stacksTo(1));
    public static final DeferredItem<Item> MATE = ITEMS.registerItem("mate",
            ArcanaMateItem::new,
            P -> P.stacksTo(1));
    public static final DeferredItem<Item> PROTO_WAND = ITEMS.registerItem(
            "proto_wand", ArcanaProtoWand::new,
            P -> P.stacksTo(1));
    public static final DeferredItem<BlockItem> NITOR = ITEMS.registerSimpleBlockItem("nitor",
            () -> BlockRegistry.NITOR_BLOCK.get(), //dont follow the yellow it lies
            P -> P.stacksTo(1));

    public static final DeferredItem<BlockItem> LILLIE_BLOCK_ITEM = ITEMS.registerItem("lillie_item",
            props -> new LillieBlockItem(BlockRegistry.LILLIE_BLOCK.get(), props),
            P -> P);


    public static final DeferredItem<Item> NHIL_CRYSTAL = ITEMS.registerSimpleItem("nhil_crystal");
    public static final DeferredItem<Item> INK_BOTTLE = ITEMS.registerSimpleItem("ink_bottle");
    public static final DeferredItem<Item> ANCIENT_FEATHER = ITEMS.registerSimpleItem("ancient_feather");
    public static final DeferredItem<Item> NHIL_POWDER = ITEMS.registerSimpleItem("nhil_powder");
    public static final DeferredItem<Item> WEEPING_POWDER = ITEMS.registerSimpleItem("weeping_powder");
    public static final DeferredItem<Item> ALCHEMICAL_STEEL = ITEMS.registerSimpleItem("alchemical_steel");



    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
