package org.exodusstudio.arcana.common.boiler_recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

public record BoilerRecipe(Map<Item, Integer> inputs, ItemStack output) {

    @Override
    public ItemStack output() {
        return output.copy();
    }
}
