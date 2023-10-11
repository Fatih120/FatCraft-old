package org.mof.modernbeds;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemColoredBed extends ItemBlock {
    public ItemColoredBed(Block p_i45328_1_) {
        super(p_i45328_1_);
        maxStackSize = 1;
    }

    @Override // COPY-PASTED FROM BlockBed::onItemUse
    public boolean onItemUse(
        ItemStack itemStack, EntityPlayer player, World world,
        int x, int y, int z,
        int side,
        float hitX, float hitY, float hitZ
    ) {
        if (world.isRemote) return true;
        if (side != 1) return false;

        ++y;

        int a = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        byte xx = 0, zz = 0;

        if (a == 0) zz = 1;
        if (a == 1) xx = -1;
        if (a == 2) zz = -1;
        if (a == 3) xx = 1;

        if (
            !player.canPlayerEdit(x, y, z, side, itemStack) ||
            !player.canPlayerEdit(x + xx, y, z + zz, side, itemStack) ||
            !world.isAirBlock(x, y, z) ||
            !world.isAirBlock(x + xx, y, z + zz) ||
            !World.doesBlockHaveSolidTopSurface(world, x, y - 1, z) ||
            !World.doesBlockHaveSolidTopSurface(world, x + xx, y - 1, z + zz)
        )
            return false;

        world.setBlock(x, y, z, field_150939_a, a, 3);

        if (world.getBlock(x, y, z) == field_150939_a)
            world.setBlock(x + xx, y, z + zz, field_150939_a, a + 8, 3);

        --itemStack.stackSize;
        return true;
    }
}
