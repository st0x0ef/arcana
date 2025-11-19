package org.exodusstudio.arcana.common.datagen.provider;

import org.exodusstudio.arcana.Arcana;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.exodusstudio.arcana.common.registry.BlockRegistry;
import org.exodusstudio.arcana.common.registry.ItemRegistry;

public class ArcanaEnUsGenerator extends LanguageProvider {
    public ArcanaEnUsGenerator(PackOutput output) {
        super(output, Arcana.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("message.arcana", "You found a old note in the floor, use it and press %1$s to see the information on it");
        add("key.categories.arcana", "Arcana");

        add("advancements.arcana.root.description", "The Path of the Arcane Alchemist");
        add("advancements.arcana.ink.description", "what this is used for? ink ofc");
        add("advancements.arcana.root.title", "Arcana");
        add("advancements.arcana.ink.title", "A Ink bottle?");

        add("arcana.state.solid", "Solid");
        add("arcana.state.liquid", "Liquid");
        add("arcana.state.gaseous", "Gaseous");

        add("arcana.block_entity.research_table", "Research Table");

        //Max Length: 352 char
        //add("gui.widget.1", "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXZZZZYYYYAAAAAA");
        add("gui.widget.1", "Ages ago the best arcane alchemist roamed the land, he left clues of his inventions in different places, but fearing for the worst he locked them behind with magic, now you, descendant of the first alchemist need to search for the power your ancestor left, try searching for a village that alchemist left one of his prototypes in there");


        add("arcana.structure.amorphous", "Amorphous");
        add("arcana.structure.pulverized", "Pulverized");
        add("arcana.structure.pulverized_rough", "Pulverized (Rough)");
        add("arcana.structure.non_newtonian", "Non-Newtonian");
        add("arcana.structure.crystalline", "Crystalline");
        add("arcana.structure.super_saturated", "Super-Saturated");
        add("arcana.structure.highly_viscous", "Highly Viscous");

        add("tooltip.arcana.shift_to_view", "Press shift to view item structure");
        add("itemGroup.arcana.arcana_creative_tab", "Arcana");
        add("arcana.message.scribbling_tool_no_map", "You need more paper to take notes");
        add("arcana.message.research_table_not_found", "Place two research tables nearby");
        add("arcana.message.research_table_note_found", "You already studied this");
        add("arcana.message.notes_already_taken", "Cant take more notes of this...");
        addItem(ItemRegistry.SCRIBBLING_TOOL, "Scribbling Tool");
        addItem(ItemRegistry.SCRIBBLED_NOTE, "Scribbled Note");
        addItem(ItemRegistry.NHIL_CRYSTAL, "Nhil Crystal");
        addItem(ItemRegistry.NHIL_POWDER, "Nhil Powder");
        addItem(ItemRegistry.OLD_NOTE, "Old Note");
        addItem(ItemRegistry.INK_BOTTLE, "Ink Bottle");
        addItem(ItemRegistry.ANCIENT_FEATHER, "Ancient Feather");
        addBlock(BlockRegistry.RESEARCH_TABLE, "Research Table");
        addBlock(BlockRegistry.BOILER_BLOCK, "Boiler");
        addItem(BlockRegistry.getItemFromBlock(BlockRegistry.RESEARCH_TABLE), "Research Table");
        //addItem(BlockRegistry.getItemFromBlock(BlockRegistry.NIMBUS_STONE),"Nimbus Stone");
        addItem(ItemRegistry.MATE, "Matecitoooo");
        addItem(BlockRegistry.getItemFromBlock(BlockRegistry.BOILER_BLOCK), "Boiler");
        addItem(ItemRegistry.PROTO_WAND, "Proto Wand");
        addBlock(BlockRegistry.WEEPING_PETAL, "Weeping Petal");
        addItem(BlockRegistry.getItemFromBlock(BlockRegistry.WEEPING_PETAL), "Weeping Petal");
        addBlock(BlockRegistry.FIRE_POPPY, "Fire Poppy");
        addItem(BlockRegistry.getItemFromBlock(BlockRegistry.FIRE_POPPY), "Fire Poppy");
        addItem(ItemRegistry.WEEPING_POWDER, "Weeping Powder");
        addItem(ItemRegistry.ALCHEMICAL_STEEL, "Alchemical Steel");
    }
}
