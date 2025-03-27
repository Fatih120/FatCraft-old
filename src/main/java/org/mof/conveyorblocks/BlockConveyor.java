package org.mof.conveyorblocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import static org.mof.conveyorblocks.ConveyorBlocksMod.MOD_ID;

final public class BlockConveyor extends BlockDirectional {
  final public static String NAME = "conveyor";
  final public static BlockConveyor INSTANCE = new BlockConveyor();

  private BlockConveyor() {
    super(Material.iron);
    setBlockName(NAME);
    setBlockTextureName(MOD_ID + ":" + NAME);
    setCreativeTab(CreativeTabs.tabRedstone);
    maxY = 0.1;
  }
  @Override
  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
    // taken from BlockPumpkin::onBlockPlacedBy
    int direction = MathHelper.floor_double(entity.rotationYaw * 4 / 360 + 2.5) % 4;
    world.setBlockMetadataWithNotify(x, y, z, direction, 2);
  }
  @Override
  public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    if (!canBlockStay(world, x, y, z)) {
      dropBlockAsItem(world, x, y, z, 0, 0);
      world.setBlockToAir(x, y, z);
    }
  }
  @Override
  public boolean canBlockStay(World world, int x, int y, int z) {
    return !world.isAirBlock(x, y - 1, z);
  }
  @Override
  public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    if (!entity.onGround || entity instanceof EntityFX)
      return;

    int direction = world.getBlockMetadata(x, y, z) % 4;
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

  private IIcon frontIcon; 
  private IIcon sideIcon;
  private IIcon topIcon;
  private IIcon bottomIcon;

  @Override @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister iconRegister) {
    frontIcon = iconRegister.registerIcon(getTextureName() + "_front");
    sideIcon = iconRegister.registerIcon(getTextureName() + "_side");
    topIcon = sideIcon;
    bottomIcon = iconRegister.registerIcon(getTextureName() + "_bottom");
    blockIcon = sideIcon;
  }
  @Override @SideOnly(Side.CLIENT)
  public IIcon getIcon(int unkA, int unkB) {
    // based on BlockPumpkin::getIcon
    // TODO: fix this
    return unkA == 1 ? topIcon :
           unkA == 0 ? bottomIcon :
           unkB == 2 && unkA == 2 ? frontIcon :
           unkB == 3 && unkA == 5 ? frontIcon :
           unkB == 0 && unkA == 3 ? frontIcon :
           unkB == 1 && unkA == 4 ? frontIcon :
           sideIcon;
  }
  @Override
  public boolean isOpaqueCube() {
    return false;
  }
  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }
}