package org.mof.conveyorblocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

final public class BlockConviation extends Block {
  final public static String NAME = "conviation";
  final public static BlockConviation INSTANCE = new BlockConviation();

  private BlockConviation() {
    super(Material.rock);
  }
  @Override
  public boolean isCollidable() {
    return false;
  }
  @Override
  public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
    return true;
  }
  @Override
  public boolean isOpaqueCube() {
    return false;
  }
  @Override
  public int getRenderType() {
    return -1;
  }
  @Override
  public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    // self-destruct if the block below us isn't a conveyor anymore
    if (!(world.getBlock(x, y - 1, z) instanceof BlockConveyor))
      world.setBlockToAir(x, y, z);
  }
  @Override
  public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z) {
    return null;
  }
  @Override
  public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    if (!entity.onGround || entity instanceof EntityFX)
      return;
    //assert world.getBlock(x, y - 1, z) instanceof BlockConveyor;

    int direction = BlockConveyor.getDirection(world, x, y - 1, z);
    double dx = 0, dy = 0, dz = 0;
    double d = 0.1; // multiplier
    switch (direction) {
      case 0: dz = d; break; // north
      case 1: dx = -d; break; // east
      case 2: dz = -d; break; // south
      case 3: dx = d; break; // west
    }
    entity.addVelocity(dx, dy, dz);
  }
}
