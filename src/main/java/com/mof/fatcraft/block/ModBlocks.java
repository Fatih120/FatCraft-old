package com.mof.fatcraft.block;

//import com.agadar.bettervanilla.help.ModConfigurations;
import com.mof.fatcraft.handlers.RegisterHelper;
import net.minecraft.block.Block;

/** Manages all mod blocks. */
public class ModBlocks 
{
	// Blocks
	public static Block colored_bed;


	/** Instantiates and registers all mod blocks. */
	public static void registerModBlocks()
	{
		colored_bed = new BlockColoredBed();
		RegisterHelper.registerBlock(colored_bed);
	}
}