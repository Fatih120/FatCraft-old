package com.mof.fatcraft.item;

import com.mof.fatcraft.Fatcraft;
import net.minecraft.item.Item;

public class ItemDollPatchouli extends Item {
    public ItemDollPatchouli()
    {
        super();
        this.setMaxDamage(0); // This makes it so the item doesn't have the damage bar at the bottom of its icon.
        //this.setMaxStackSize(1);
        this.setUnlocalizedName("dollPatchouli");
        this.setTextureName(Fatcraft.MODID + ":dollPatchouli");
    }
}
