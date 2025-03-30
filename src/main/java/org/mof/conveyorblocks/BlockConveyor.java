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
    this.setBlockName(NAME);
    this.setBlockTextureName(MOD_ID + ":" + NAME);
    this.setBlockBounds(0, 0, 0, 1, 0.0625F, 1);
    this.setCreativeTab(CreativeTabs.tabRedstone);
  }
  @Override
  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
    int direction = MathHelper.floor_double(entity.rotationYaw * 4 / 360 + 2.5) % 4;
    world.setBlockMetadataWithNotify(x, y, z, direction, 2);
  }
  @Override
  public boolean canBlockStay(World world, int x, int y, int z) {
    return !world.isAirBlock(x, y - 1, z);
  }
  @Override
  public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    if (!this.canBlockStay(world, x, y, z)) {
      this.dropBlockAsItem(world, x, y, z, 0, 0);
      world.setBlockToAir(x, y, z);
    }
  }
  @Override
  public boolean canPlaceBlockAt(World world, int x, int y, int z) {
    return super.canPlaceBlockAt(world, x, y, z) && this.canBlockStay(world, x, y, z);
  }
  @Override
  public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
    if (!entity.onGround || entity instanceof EntityFX)
      return;

    int direction = world.getBlockMetadata(x, y, z) % 4;
    double dx = 0, dy = 0, dz = 0;
    double d = 0.1; // multiplier
    switch (direction) {
      case 0: dz = d; break; // south
      case 1: dx = -d; break; // west
      case 2: dz = -d; break; // north
      case 3: dx = d; break; // east
    }
    entity.addVelocity(dx, dy, dz);
  }

  @SideOnly(Side.CLIENT)
  private IIcon[] icons;

  @Override @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister iconRegister) {
    this.icons = new IIcon[] {
      iconRegister.registerIcon(getTextureName() + "_south"),
      iconRegister.registerIcon(getTextureName() + "_west"),
      iconRegister.registerIcon(getTextureName() + "_north"),
      iconRegister.registerIcon(getTextureName() + "_east"),
    };
    this.blockIcon = this.icons[3];
  }
  @Override @SideOnly(Side.CLIENT)
  public IIcon getIcon(int side, int meta) {
    return this.icons[meta % 4];
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