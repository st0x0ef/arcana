package org.exodusstudio.arcana.common.data.theorie;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Supplier;

public class Theory {
    private final int id;
    private final String research_name;
    private final Supplier<Block> relatedBlock;
    private final Supplier<Item> relatedItem;
    //either of the suppliers could be set to null in order to use 1 function for both Items and Blocks

    public Theory(int id, String research_name, Supplier<Block> relatedBlock, Supplier<Item> relatedItem)
    {
        this.id = id;
        this.research_name = research_name;
        this.relatedBlock = relatedBlock;
        this.relatedItem = relatedItem;
    }


    public String getResearchName()
    {
        return research_name;
    }

    public int getId()
    {
        return id;
    }

    public Block getRelatedBlock()
    {
        return relatedBlock.get();
    }

}
