package org.mof.conveyorblocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.mof.conveyorblocks.ConveyorBlocksMod.*;

@Mod(modid = MOD_ID, version = MOD_VERSION, name = MOD_NAME, acceptedMinecraftVersions = "[1.7.10]")
final public class ConveyorBlocksMod {
  public final static String MOD_ID = "conveyorblocks";
  public final static String MOD_NAME = "Conveyor Blocks";
  public final static String MOD_VERSION = "1.0";
  public final static Logger LOG = LogManager.getLogger(ConveyorBlocksMod.MOD_ID);

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    GameRegistry.registerBlock(BlockConveyor.INSTANCE, BlockConveyor.NAME);
  }
}
