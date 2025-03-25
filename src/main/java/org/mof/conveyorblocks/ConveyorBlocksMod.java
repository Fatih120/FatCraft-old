package org.mof.conveyorblocks;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.List;
import static org.mof.conveyorblocks.ConveyorBlocksMod.*;

@Mod(modid = MOD_ID, version = MOD_VERSION, name = MOD_NAME, acceptedMinecraftVersions = "[1.7.10]")
final public class ConveyorBlocksMod {
  public final static String MOD_ID = "conveyorblocks";
  public final static String MOD_NAME = "Conveyor Blocks";
  public final static String MOD_VERSION = "1.0";
  public final static Logger Logger = LogManager.getLogger(ConveyorBlocksMod.MOD_ID);

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    GameRegistry.registerBlock(new BlockConveyor(), "conveyor");
  }
  @EventHandler
  public void init(FMLInitializationEvent event) {
    MinecraftForge.EVENT_BUS.register(this);
    FMLCommonHandler.instance().bus().register(this);
  }
  @SubscribeEvent
  public void onWorldTick(WorldTickEvent event) {
    // HACK: make a copy of the entity list before iterating over it since whatever
    //       we're doing seems to trigger modifications to the original
    @SuppressWarnings("unchecked")
    List<Entity> entities = new ArrayList<>((List<Entity>) event.world.loadedEntityList);
    entities.stream()
      .filter(e -> !(e instanceof EntityPlayer)) // players are handled separately by onPlayerTick
      .forEach(ConveyorBlocksMod::handleConveyorMovement);
  }
  @SubscribeEvent
  public void onPlayerTick(PlayerTickEvent event) {
    handleConveyorMovement(event.player);
  }

  static public void handleConveyorMovement(Entity entity) {
    Vec3 pos = findAdjacentConveyorBlock(entity);
    if (pos == null) return;
    int direction = getConveyorBlockDirection(entity.worldObj, pos.xCoord, pos.yCoord, pos.zCoord);
    Vec3 delta = computeConveyorBlockDelta(direction);
    if (entity instanceof EntityItem)
      entity.addVelocity(delta.xCoord * 0.03, 0, delta.zCoord * 0.03);
    else if (entity instanceof EntityLivingBase ||
             entity instanceof EntityMinecart ||
             entity instanceof EntityBoat)
      entity.moveEntity(delta.xCoord * 0.1, 0, delta.zCoord * 0.1);
  }
  public static Vec3 findAdjacentConveyorBlock(Entity entity) {
    // FIXME: this is currently biased towards blocks at lower coordinates
    if (!entity.onGround || entity.boundingBox == null)
      return null;
    int x0 = MathHelper.floor_double(entity.boundingBox.minX),
        x1 = MathHelper.ceiling_double_int(entity.boundingBox.maxX),
        y = MathHelper.floor_double(entity.boundingBox.minY) - 1,
        z0 = MathHelper.floor_double(entity.boundingBox.minZ),
        z1 = MathHelper.ceiling_double_int(entity.boundingBox.maxZ);
    for (int x = x0; x < x1; x++)
      for (int z = z0; z < z1; z++)
        if (entity.worldObj.getBlock(x, y, z) instanceof BlockConveyor)
          return Vec3.createVectorHelper(x, y, z);
    return null;
  }
  public static Vec3 computeConveyorBlockDelta(int direction) {
    double x = 0, y = 0, z = 0;
    switch (direction) {
      case 0: z = 1; break; // north
      case 1: x = -1; break; // east
      case 2: z = -1; break; // south
      case 3: x = 1; break; // west
    }
    return Vec3.createVectorHelper(x, y, z);
  }
  public static int getConveyorBlockDirection(World world, int x, int y, int z) {
    return world.getBlockMetadata(x, y, z) % 4;
  }
  public static int getConveyorBlockDirection(World world, double x, double y, double z) {
    return getConveyorBlockDirection(world, MathHelper.floor_double(x),
                                            MathHelper.floor_double(y),
                                            MathHelper.floor_double(z));
  }
}
