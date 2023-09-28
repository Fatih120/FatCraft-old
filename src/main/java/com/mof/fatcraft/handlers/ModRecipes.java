package com.mof.fatcraft.handlers;

import com.mof.fatcraft.item.ModItems;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;

import java.util.ArrayList;

/** Manages this mod's recipes. */
public class ModRecipes
{
    /** Registers this mod's recipes. */
    public static void registerModRecipes()
    {
        // Remove the recipe for the original bed item and add the recipes for our colored bed item's subitems.
        removeRecipe(new ItemStack(Items.bed));
            for (int i = 0; i < 16; i++)
            {
                GameRegistry.addRecipe(new ItemStack(ModItems.colored_bed, 1, i), "xxx", "yyy", 'x', new ItemStack(Blocks.wool, 1, i), 'y', Blocks.planks);
                //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.colored_bed, 1, i), new ItemStack(Items.dye, 1, i), new ItemStack(ModItems.colored_bed));
                //GameRegistry.addShapelessRecipe(new ItemStack(ModItems.colored_bed, 1, i), new ItemStack(Items.dye, 1, i), new ItemStack(Items.bed));
            }

        removeRecipe(new ItemStack(Blocks.hopper));
        GameRegistry.addRecipe(new ItemStack(Blocks.hopper), "x x", "xyx", " x ", 'x', Blocks.stone, 'y', Items.redstone);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.clay_ball, 4), Blocks.dirt, Blocks.sand, Blocks.gravel, Items.water_bucket);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.flint_and_steel), Items.iron_ingot, Items.flint);
        GameRegistry.addRecipe(new ItemStack(Blocks.web), "x x", " x ", "x x", 'x', Items.string);


        GameRegistry.addShapelessRecipe(new ItemStack(ModItems.cured_rotten_flesh), Items.rotten_flesh, Items.slime_ball);
        GameRegistry.addSmelting(ModItems.cured_rotten_flesh, new ItemStack(Items.leather), 2);

    }

    /** Removes all recipes from the CraftingManager's recipe list that have an output equal to par1ItemStack. */
    private static void removeRecipe(ItemStack par1ItemStack)
    {
        ArrayList<?> recipes = (ArrayList<?>) CraftingManager.getInstance().getRecipeList();

        for (int i = 0; i < recipes.size(); i++)
        {
            IRecipe tmpRecipe = (IRecipe) recipes.get(i);
            if (ItemStack.areItemStacksEqual(par1ItemStack, tmpRecipe.getRecipeOutput())) recipes.remove(i);
        }
    }
}
