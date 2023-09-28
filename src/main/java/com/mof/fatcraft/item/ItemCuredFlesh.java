package com.mof.fatcraft.item;

import com.mof.fatcraft.Fatcraft;
import net.minecraft.item.Item;

public class ItemCuredFlesh extends Item {

    public ItemCuredFlesh() {
        super();
        this.setCreativeTab(Fatcraft.tabFatCraft);
        this.setMaxDamage(0); // This makes it so the item doesn't have the damage bar at the bottom of its icon.
        this.setUnlocalizedName("cured_rotten_flesh");
        this.setTextureName(Fatcraft.MODID + ":cured_rotten_flesh");
        }
    }