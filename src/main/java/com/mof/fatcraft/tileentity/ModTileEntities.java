package com.mof.fatcraft.tileentity;

//import com.agadar.bettervanilla.help.ModConfigurations;

import com.mof.fatcraft.Fatcraft;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModTileEntities 
{
	public static void registerModTileEntities()
	{
		// Register the tile entity that is responsible for storing the bed's direction.
		GameRegistry.registerTileEntity(TileEntityColoredBed.class, Fatcraft.MODID + "_TileEntityBedColor");
	}
}
