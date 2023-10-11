package org.mof.modernbeds;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import static org.mof.modernbeds.ModernBedsMod.*;

@Mod(modid = MOD_ID, version = MOD_VERSION, name = MOD_NAME, acceptedMinecraftVersions = "[1.7.10]")
final public class ModernBedsMod {
    public final static String MOD_ID = "modernbeds";
    public final static String MOD_NAME = "Modern Beds";
    public final static String MOD_VERSION = "1.0";

    @Mod.EventHandler
    public void initBeds(FMLPreInitializationEvent event) {
        Item[] items = new Item[16];

        for (int i = 0; i < 16; i++) {
            BlockColoredBed block = new BlockColoredBed(i);
            GameRegistry.registerBlock(block, ItemColoredBed.class, ItemDye.field_150921_b[i] + "_bed");

            items[i] = Item.getItemFromBlock(block);

            GameRegistry.addRecipe(
                new ItemStack(block),
                "XXX", "YYY",
                'X', new ItemStack(Blocks.wool, 1, ~i & 0xF),
                'Y', Blocks.planks
            );
        }

        for (int i = 0; i < 16; i++) {
            Item to = items[i];

            for (Item from : items)
                GameRegistry.addShapelessRecipe(
                    new ItemStack(to),
                    new ItemStack(from),
                    new ItemStack(Items.dye, 1, i)
                );
        }
    }
}
