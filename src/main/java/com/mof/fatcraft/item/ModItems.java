package com.mof.fatcraft.item;

//import com.agadar.bettervanilla.help.ModConfigurations;
import com.mof.fatcraft.handlers.RegisterHelper;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems 
{
	// Items (Miscellaneous)
	public static Item colored_bed;
	public static Item doll_patchouli;
	public static Item doll_kitty;

	public static void registerModItems()
	{
		colored_bed = new ItemColoredBed();
		doll_patchouli = new ItemDollPatchouli();
		doll_kitty = new ItemDollKitty();
		RegisterHelper.registerItem(colored_bed);
		RegisterHelper.registerItem(doll_patchouli);
		RegisterHelper.registerItem(doll_kitty);
	}
}
