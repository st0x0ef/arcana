package org.exodusstudio.arcana.common.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.exodusstudio.arcana.Arcana;

public class TagRegistry {
    public static final TagKey<Item> FUNDAMENTAL = ItemTags.create(ResourceLocation.fromNamespaceAndPath(Arcana.MODID, "fundamental"));
    public static final TagKey<Block> FUNDAMENTALB = BlockTags.create(ResourceLocation.fromNamespaceAndPath(Arcana.MODID, "fundamentalb"));
}
