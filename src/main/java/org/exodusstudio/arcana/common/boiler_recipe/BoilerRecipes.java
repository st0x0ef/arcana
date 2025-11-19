package org.exodusstudio.arcana.common.boiler_recipe;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.exodusstudio.arcana.common.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoilerRecipes {
    private static final List<BoilerRecipe> RECIPES = new ArrayList<>();

    static {
        Map<Item, Integer> alchSteel = new HashMap<>();
        alchSteel.put(Items.IRON_INGOT, 1);
        alchSteel.put(ItemRegistry.WEEPING_POWDER.asItem(), 1);
        alchSteel.put(ItemRegistry.NHIL_POWDER.asItem(), 1);
        RECIPES.add(new BoilerRecipe(alchSteel, new ItemStack(ItemRegistry.ALCHEMICAL_STEEL.asItem())));
/*
        Map<Item, Integer> nitor = new HashMap<>();
        nitor.put(ItemRegistry.NHIL_POWDER.asItem(), 1);
        nitor.put(Items.COAL, 1);
        RECIPES.add(new BoilerRecipe(nitor, new ItemStack(ItemRegistry.NITOR.asItem())));
*/
        Map<Item, Integer> cheese = new HashMap<>();
        cheese.put(Items.MILK_BUCKET, 1);
        cheese.put(ItemRegistry.NHIL_POWDER.asItem(), 1);
        RECIPES.add(new BoilerRecipe(cheese, new ItemStack(Items.SPONGE)));
    }

    public static List<BoilerRecipe> getRecipes(){
        return RECIPES;
    }
}
