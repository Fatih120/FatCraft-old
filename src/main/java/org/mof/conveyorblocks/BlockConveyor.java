package org.mof.conveyorblocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
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
    this.setCreativeTab(CreativeTabs.tabRedstone);
  }
  @Override
  public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {
    // taken from BlockPumpkin::onBlockPlacedBy
    int l = MathHelper.floor_double(entity.rotationYaw * 4 / 360 + 2.5) % 4;
    world.setBlockMetadataWithNotify(x, y, z, l, 2);
  }
  @Override
  public int onBlockPlaced(World world, int x, int y, int z, int facing, float hitX, float hitY, float hitZ, int meta) {
    if (world.getBlock(x, y + 1, z) instanceof BlockAir)
      world.setBlock(x, y + 1, z, BlockConviation.INSTANCE);
    return super.onBlockPlaced(world, x, y, z, facing, hitX, hitY, hitZ, meta);
  }
  @Override
  public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
    if (world.getBlock(x, y + 1, z) instanceof BlockAir)
      world.setBlock(x, y + 1, z, BlockConviation.INSTANCE);
  }
  public static int getDirection(World world, int x, int y, int z) {
    return world.getBlockMetadata(x, y, z) % 4;
  }

  private IIcon frontIcon; 
  private IIcon sideIcon;
  private IIcon topIcon;
  private IIcon bottomIcon;

  @Override @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister iconRegister) {
    this.frontIcon = iconRegister.registerIcon(this.getTextureName() + "_front");
    this.sideIcon = iconRegister.registerIcon(this.getTextureName() + "_side");
    this.topIcon = this.sideIcon;
    this.bottomIcon = iconRegister.registerIcon(this.getTextureName() + "_bottom");
    this.blockIcon = this.sideIcon;
  }
  @Override @SideOnly(Side.CLIENT)
  public IIcon getIcon(int unkA, int unkB) {
    // based on BlockPumpkin::getIcon
    // TODO: fix this
    return unkA == 1 ? this.topIcon :
           unkA == 0 ? this.bottomIcon :
           unkB == 2 && unkA == 2 ? this.frontIcon :
           unkB == 3 && unkA == 5 ? this.frontIcon :
           unkB == 0 && unkA == 3 ? this.frontIcon :
           unkB == 1 && unkA == 4 ? this.frontIcon :
           this.sideIcon;
  }
}