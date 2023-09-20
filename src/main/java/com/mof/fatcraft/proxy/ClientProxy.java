package com.mof.fatcraft.proxy;

import java.util.UUID;

import com.mof.fatcraft.block.RendererTEBlockDoll;
import com.mof.fatcraft.tileentity.TileEntityBlockDoll;
import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class ClientProxy extends CommonProxy {

    public ClientProxy() {
        super();
    }

    @Override
    public Object getClientGuiElement(final int ID, final EntityPlayer player, final World world, final int X, final int Y, final int Z) {
        final TileEntity te = world.getTileEntity(X, Y, Z);

        if (te == null)
            return null;

        return null;
    }

    @Override
    public void registerRenderers() {
        // NO-OP
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockDoll.class, new RendererTEBlockDoll());
    }

    @Override
    public EntityPlayer getPlayer(UUID playerID) {
        if (Minecraft.getMinecraft().theWorld != null) {
            for (Object object : Minecraft.getMinecraft().theWorld.playerEntities) {
                EntityPlayer player = (EntityPlayer) object;
                if (player.getUniqueID().equals(playerID))
                    return player;
            }
        }

        return null;
    }

}
