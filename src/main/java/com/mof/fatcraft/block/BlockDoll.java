package com.mof.fatcraft.block;

import com.mof.fatcraft.tileentity.TileEntityBlockDoll;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDoll extends BlockContainer {
    private static final String name = "Doll";
    public BlockDoll() {
    super(Material.cloth);
    this.setBlockName(name);
    }

    @Override
    public boolean renderAsNormalBlock(){
    return false;
    }

    @Override
    public int getRenderType(){
    return -1;
    }

    @Override
    public boolean isOpaqueCube(){
    return false;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
    return new TileEntityBlockDoll();
    }
}
