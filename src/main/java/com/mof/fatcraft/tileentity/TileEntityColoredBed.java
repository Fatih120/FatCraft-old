package com.mof.fatcraft.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;

public class TileEntityColoredBed extends TileEntity {
	private static final String VARIABLENAME = "_color";
	private int _color;
	
	public int getColor() {
		return _color;
	}
	
	public void setColor(int color) {
		_color = MathHelper.clamp_int(color, 0, 15);
	}
	
	@Override
	public void writeToNBT(NBTTagCompound var1)
	{
		var1.setInteger(VARIABLENAME, _color);
	    super.writeToNBT(var1);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound var1)
	{
		_color = MathHelper.clamp_int(var1.getInteger(VARIABLENAME), 0, 15);
	    super.readFromNBT(var1);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tileTag = new NBTTagCompound();
		this.writeToNBT(tileTag);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, tileTag);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.func_148857_g());
	}
}
