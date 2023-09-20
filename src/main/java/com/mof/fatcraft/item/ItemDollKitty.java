package com.mof.fatcraft.item;

import com.mof.fatcraft.Fatcraft;
import net.minecraft.item.Item;

public class ItemDollKitty extends Item {

    public ItemDollKitty()
    {
        super();
        this.setMaxDamage(0);
        this.setUnlocalizedName("dollKitty");
        this.setTextureName(Fatcraft.MODID + ":dollKitty");
    }
}
