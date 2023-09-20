package com.mof.fatcraft.item;

import java.util.List;

import com.mof.fatcraft.Fatcraft;
import com.mof.fatcraft.block.BlockColoredBed;
import com.mof.fatcraft.block.ModBlocks;
import com.mof.fatcraft.tileentity.TileEntityColoredBed;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBed;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemColoredBed extends ItemBed 
{
	public static final String[] bedNames = new String[] {"white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black"};
	@SideOnly(Side.CLIENT)
    private IIcon[] bedIcons;
	
	public ItemColoredBed() 
	{
		super();
        this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setHasSubtypes(true);	// This allows the item to be marked as a metadata item.
        this.setMaxDamage(0);  		// This makes it so the item doesn't have the damage bar at the bottom of its icon.
        this.setMaxStackSize(1);
        this.setUnlocalizedName("colored_bed");
        this.setTextureName(Fatcraft.MODID + ":" + getUnlocalizedName().substring(5));
	}	
	
	@Override
    public IIcon getIconFromDamage(int par1) 
	{
		int j = MathHelper.clamp_int(par1, 0, 15);
        return this.bedIcons[j];
    }
	
	@Override
	public void registerIcons(IIconRegister iconRegister)
    {
		bedIcons = new IIcon[bedNames.length];	
		
		for (int i = 0; i < bedNames.length; i++)
        {
            this.bedIcons[i] = iconRegister.registerIcon(this.getIconString() + "_" + bedNames[i]);
        }
    }

	@SuppressWarnings("unchecked")
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTabs, @SuppressWarnings("rawtypes") List list)
    {
        for (int j = 0; j < 16; ++j)
        {
            list.add(new ItemStack(item, 1, j));
        }
    }
	
	@Override
	public String getUnlocalizedName(ItemStack itemStack)
    {
        int i = MathHelper.clamp_int(itemStack.getItemDamage(), 0, 15);
        return super.getUnlocalizedName() + "." + bedNames[i];
    }
	
	@Override
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int hand, float par8, float par9, float par10)
    {
        if (world.isRemote)
        {
            return true;
        }
        else if (hand != 1)
        {
            return false;
        }
        else
        {
            ++y;
            BlockColoredBed blockbed = (BlockColoredBed)ModBlocks.colored_bed;
            int i1 = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
            byte b0 = 0;
            byte b1 = 0;

            if (i1 == 0)
            {
                b1 = 1;
            }

            if (i1 == 1)
            {
                b0 = -1;
            }

            if (i1 == 2)
            {
                b1 = -1;
            }

            if (i1 == 3)
            {
                b0 = 1;
            }

            if (player.canPlayerEdit(x, y, z, hand, itemStack) && player.canPlayerEdit(x + b0, y, z + b1, hand, itemStack))
            {
                if (world.isAirBlock(x, y, z) && world.isAirBlock(x + b0, y, z + b1) && World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) && World.doesBlockHaveSolidTopSurface(world, x + b0, y - 1, z + b1))
                {
                    world.setBlock(x, y, z, blockbed, i1, 3);
                    ((TileEntityColoredBed)world.getTileEntity(x, y, z)).setColor(itemStack.getItemDamage());

                    if (world.getBlock(x, y, z) == blockbed)
                    {
                        world.setBlock(x + b0, y, z + b1, blockbed, i1 + 8, 3);
                        ((TileEntityColoredBed)world.getTileEntity(x + b0, y, z + b1)).setColor(itemStack.getItemDamage());
                    }

                    --itemStack.stackSize;
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
    }
}
