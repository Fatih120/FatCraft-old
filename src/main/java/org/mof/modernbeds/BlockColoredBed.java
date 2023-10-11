package org.mof.modernbeds;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBed;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDye;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Random;
import static org.mof.modernbeds.ModernBedsMod.MOD_ID;

public class BlockColoredBed extends BlockBed {
    BlockColoredBed(int colorId) {
        setBlockName("bed." + ItemDye.field_150923_a[colorId]);
        setBlockTextureName(MOD_ID + ":" + ItemDye.field_150921_b[colorId] + "_bed");
        setHardness(0.2F);
        setCreativeTab(CreativeTabs.tabDecorations);
        disableStats();
    }

    @Override @SideOnly(Side.CLIENT)
    public String getItemIconName() {
        return getTextureName();
    }

    @Override @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public Item getItemDropped(int metadata, Random random, int fortune) {
        return isBlockHeadOfBed(metadata) ? Item.getItemById(0) : Item.getItemFromBlock(this);
    }

    @Override
    public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player) {
        return true;
    }

    @Override
    public void onFallenUpon(World world, int x, int y, int z, Entity entity, float fallDistance) {
        entity.fallDistance *= 0.5F;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity.motionY > -0.2 || entity.isSneaking()) return;

        entity.motionY *= -0.9;
        entity.isAirBorne = true;
    }
}
