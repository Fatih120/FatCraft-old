package com.mof.fatcraft.block;

import com.mof.fatcraft.item.ModItems;
import com.mof.fatcraft.tileentity.TileEntityColoredBed;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Iterator;
import java.util.Random;

public class BlockColoredBed extends BlockDirectional implements ITileEntityProvider {
    public static final int[][] field_149981_a = new int[][]{{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    @SideOnly(Side.CLIENT)
    private IIcon[][] bedEndIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[][] bedSideIcons;
    @SideOnly(Side.CLIENT)
    private IIcon[][] bedTopIcons;
    @SuppressWarnings("unused")
    private static final String __OBFID = "CL_00000198";

    public BlockColoredBed() {
        super(Material.cloth);
        this.setBounds();
        this.setHardness(0.2F);
        this.setBlockName("colored_bed_block");
        this.disableStats();
        //new ResourceLocation(Fatcraft.MODID + ":textures/blocks/beds/")
        //        this.setBlockTextureName("fatcraft:" + getUnlocalizedName().substring(5));
        this.setBlockTextureName("fatcraft:" + getUnlocalizedName().substring(5));
        this.isBlockContainer = true;
    }

    /**
     * Called upon block activation (right-click on the block.)
     */
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float side, float hitx, float hity) {
        if (world.isRemote) {
            return true;
        } else {
            int i1 = world.getBlockMetadata(x, y, z);

            if (!isBlockHeadOfBed(i1)) {
                int j1 = getDirection(i1);
                x += field_149981_a[j1][0];
                z += field_149981_a[j1][1];

                if (world.getBlock(x, y, z) != this) {
                    return true;
                }

                i1 = world.getBlockMetadata(x, y, z);
            }

            if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(x, z) != BiomeGenBase.hell) {
                if (func_149976_c(i1)) {
                    EntityPlayer entityplayer1 = null;
                    @SuppressWarnings("rawtypes")
                    Iterator iterator = world.playerEntities.iterator();

                    while (iterator.hasNext()) {
                        EntityPlayer entityplayer2 = (EntityPlayer) iterator.next();

                        if (entityplayer2.isPlayerSleeping()) {
                            ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;

                            if (chunkcoordinates.posX == x && chunkcoordinates.posY == y && chunkcoordinates.posZ == z) {
                                entityplayer1 = entityplayer2;
                            }
                        }
                    }

                    if (entityplayer1 != null) {
                        player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.occupied", new Object[0]));
                        return true;
                    }

                    func_149979_a(world, x, y, z, false);
                }

                EntityPlayer.EnumStatus enumstatus = player.sleepInBedAt(x, y, z);

                if (enumstatus == EntityPlayer.EnumStatus.OK) {
                    func_149979_a(world, x, y, z, true);
                    return true;
                } else {
                    if (enumstatus == EntityPlayer.EnumStatus.NOT_POSSIBLE_NOW) {
                        player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.noSleep", new Object[0]));
                    } else if (enumstatus == EntityPlayer.EnumStatus.NOT_SAFE) {
                        player.addChatComponentMessage(new ChatComponentTranslation("tile.bed.notSafe", new Object[0]));
                    }

                    return true;
                }
            } else {
                double d2 = (double) x + 0.5D;
                double d0 = (double) y + 0.5D;
                double d1 = (double) z + 0.5D;
                world.setBlockToAir(x, y, z);
                int k1 = getDirection(i1);
                x += field_149981_a[k1][0];
                z += field_149981_a[k1][1];

                if (world.getBlock(x, y, z) == this) {
                    world.setBlockToAir(x, y, z);
                    d2 = (d2 + (double) x + 0.5D) / 2.0D;
                    d0 = (d0 + (double) y + 0.5D) / 2.0D;
                    d1 = (d1 + (double) z + 0.5D) / 2.0D;
                }

                world.newExplosion((Entity) null, (double) ((float) x + 0.5F), (double) ((float) y + 0.5F),
                        (double) ((float) z + 0.5F), 5.0F, true, true);
                return true;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5) {
        int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        int color = 0;

        TileEntityColoredBed tileEntity = (TileEntityColoredBed) par1IBlockAccess.getTileEntity(par2, par3, par4);
        if (tileEntity != null) {
            color = tileEntity.getColor();
        }

        if (par5 == 0) {
            return Blocks.planks.getBlockTextureFromSide(par5);
        } else {
            int k = getDirection(meta);
            int l = Direction.bedDirection[k][par5];
            int i1 = isBlockHeadOfBed(meta) ? 1 : 0;
            return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? (l != 5 && l != 4 ? this.bedTopIcons[color][i1] : this.bedSideIcons[color][i1]) : this.bedEndIcons[color][i1];
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister par1IconRegister) {
        this.blockIcon = Blocks.planks.getBlockTextureFromSide(0);
        IIcon head_end = par1IconRegister.registerIcon(this.getTextureName() + "_head_end");
        this.bedTopIcons = new IIcon[][]{
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_white"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_white")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_orange"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_orange")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_magenta"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_magenta")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_light_blue"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_light_blue")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_yellow"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_yellow")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_lime"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_lime")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_pink"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_pink")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_gray"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_gray")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_silver"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_silver")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_cyan"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_cyan")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_purple"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_purple")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_blue"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_blue")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_brown"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_brown")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_green"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_green")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_red"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_red")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_top_black"), par1IconRegister.registerIcon(this.getTextureName() + "_head_top_black")}};
        this.bedEndIcons = new IIcon[][]{
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_white"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_orange"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_magenta"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_light_blue"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_yellow"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_lime"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_pink"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_gray"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_silver"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_cyan"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_purple"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_blue"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_brown"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_green"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_red"), head_end},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_end_black"), head_end}};
        this.bedSideIcons = new IIcon[][]{
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_white"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_white")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_orange"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_orange")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_magenta"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_magenta")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_light_blue"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_light_blue")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_yellow"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_yellow")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_lime"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_lime")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_pink"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_pink")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_gray"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_gray")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_silver"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_silver")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_cyan"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_cyan")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_purple"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_purple")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_blue"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_blue")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_brown"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_brown")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_green"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_green")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_red"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_red")},
                new IIcon[]{par1IconRegister.registerIcon(this.getTextureName() + "_feet_side_black"), par1IconRegister.registerIcon(this.getTextureName() + "_head_side_black")}};
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType() {
        return 14;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc.)
     */
    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc. to this block.
     */
    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_) {
        this.setBounds();
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    @Override
    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        int l = p_149695_1_.getBlockMetadata(p_149695_2_, p_149695_3_, p_149695_4_);
        int i1 = getDirection(l);

        if (isBlockHeadOfBed(l)) {
            if (p_149695_1_.getBlock(p_149695_2_ - field_149981_a[i1][0], p_149695_3_, p_149695_4_ - field_149981_a[i1][1]) != this) {
                p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);
            }
        } else if (p_149695_1_.getBlock(p_149695_2_ + field_149981_a[i1][0], p_149695_3_, p_149695_4_ + field_149981_a[i1][1]) != this) {
            p_149695_1_.setBlockToAir(p_149695_2_, p_149695_3_, p_149695_4_);

            if (!p_149695_1_.isRemote) {
                this.dropBlockAsItem(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, l, 0);
            }
        }
    }

    @Override
    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        /*
         * Returns whether this bed block is the head of the bed.
         */
        return isBlockHeadOfBed(p_149650_1_) ? Item.getItemById(0) : ModItems.colored_bed;
    }

    private void setBounds() {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
    }

    /**
     * Returns whether this bed block is the head of the bed.
     */
    public static boolean isBlockHeadOfBed(int p_149975_0_) {
        return (p_149975_0_ & 8) != 0;
    }

    public static boolean func_149976_c(int p_149976_0_) {
        return (p_149976_0_ & 4) != 0;
    }

    public static void func_149979_a(World p_149979_0_, int p_149979_1_, int p_149979_2_, int p_149979_3_, boolean p_149979_4_) {
        int l = p_149979_0_.getBlockMetadata(p_149979_1_, p_149979_2_, p_149979_3_);

        if (p_149979_4_) {
            l |= 4;
        } else {
            l &= -5;
        }

        p_149979_0_.setBlockMetadataWithNotify(p_149979_1_, p_149979_2_, p_149979_3_, l, 4);
    }

    public static ChunkCoordinates func_149977_a(World p_149977_0_, int p_149977_1_, int p_149977_2_, int p_149977_3_, int p_149977_4_) {
        int i1 = p_149977_0_.getBlockMetadata(p_149977_1_, p_149977_2_, p_149977_3_);
        int j1 = BlockDirectional.getDirection(i1);

        for (int k1 = 0; k1 <= 1; ++k1) {
            int l1 = p_149977_1_ - field_149981_a[j1][0] * k1 - 1;
            int i2 = p_149977_3_ - field_149981_a[j1][1] * k1 - 1;
            int j2 = l1 + 2;
            int k2 = i2 + 2;

            for (int l2 = l1; l2 <= j2; ++l2) {
                for (int i3 = i2; i3 <= k2; ++i3) {
                    if (World.doesBlockHaveSolidTopSurface(p_149977_0_, l2, p_149977_2_ - 1, i3) && !p_149977_0_.getBlock(l2, p_149977_2_, i3).getMaterial().isOpaque() && !p_149977_0_.getBlock(l2, p_149977_2_ + 1, i3).getMaterial().isOpaque()) {
                        if (p_149977_4_ <= 0) {
                            return new ChunkCoordinates(l2, p_149977_2_, i3);
                        }

                        --p_149977_4_;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Drops the block items with a specified chance of dropping the specified items
     */
    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        /*if (!isBlockHeadOfBed(p_149690_5_))
        {
            super.dropBlockAsItemWithChance(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, p_149690_5_, p_149690_6_, 0);
        }*/
    }

    /**
     * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
     * and stop pistons
     */
    @Override
    public int getMobilityFlag() {
        return 1;
    }

    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_) {
        return ModItems.colored_bed;
    }

    // copied this from the old BlockBedOverride
    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int j) {
        if (!world.isRemote && !isBlockHeadOfBed(j) && world.getGameRules().getGameRuleBooleanValue("doTileDrops")) {
            int color = 0;
            TileEntityColoredBed tileEntity = (TileEntityColoredBed) world.getTileEntity(x, y, z);
            if (tileEntity != null) {
                color = tileEntity.getColor();
            }

            float f = 0.7F;
            double d0 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d1 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            double d2 = (double) (world.rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(world, (double) x + d0, (double) y + d1, (double) z + d2,
                    new ItemStack(ModItems.colored_bed, 1, color));
            entityitem.delayBeforeCanPickup = 10;
            world.spawnEntityInWorld(entityitem);
        }

        super.breakBlock(world, x, y, z, block, j);
        world.removeTileEntity(x, y, z);
    }

    /**
     * Called when the block is attempted to be harvested
     */
    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int p_149681_5_, EntityPlayer player) {
        if (player.capabilities.isCreativeMode && isBlockHeadOfBed(p_149681_5_)) {
            int i1 = getDirection(p_149681_5_);
            x -= field_149981_a[i1][0];
            z -= field_149981_a[i1][1];

            if (world.getBlock(x, y, z) == this) {
                world.setBlockToAir(x, y, z);
            }
        }
    }


    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityColoredBed();
    }

    public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player) {
        return this == ModBlocks.colored_bed;
    }

    public void jump(Entity entity) {
        entity.motionY = 0.0F;
        entity.fallDistance = -0.5F;
        entity.addVelocity(0.0F, 0.5F, 0.0F);

    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity.isCollidedVertically && (entity.posY > y) && (!entity.isSneaking()))
            jump(entity);
    }
}