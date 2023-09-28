package com.mof.fatcraft;

import com.mof.fatcraft.handlers.ModRecipes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mof.fatcraft.handlers.Config;
import com.mof.fatcraft.proxy.CommonProxy;
import com.mof.fatcraft.block.ModBlocks;
import com.mof.fatcraft.item.ModItems;
import com.mof.fatcraft.tileentity.ModTileEntities;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Fatcraft.MODID, version = Fatcraft.VERSION, name = Fatcraft.NAME)
public class Fatcraft {
    public static final String MODID = "fatcraft";
    public static final String NAME = "Fatcraft";
    public static final String VERSION = "@VERSION@";

    public static SimpleNetworkWrapper packetHandler;

    @SidedProxy(clientSide = "com.mof.fatcraft.proxy.ClientProxy", serverSide = "com.mof.fatcraft.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static final Logger logger = LogManager.getLogger("Fatcraft");
    public static final int howCoolAmI = Integer.MAX_VALUE;

    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRenderers();
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        // NO-OP
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        Config.load(event);


        ModBlocks.registerModBlocks();
        ModItems.registerModItems();
        ModTileEntities.registerModTileEntities();
        ModRecipes.registerModRecipes();

        NetworkRegistry.INSTANCE.registerGuiHandler(this, proxy);
    }


    public static CreativeTabs tabFatCraft = new CreativeTabs("FatCraft") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Items.dye;
        }
    };
}
