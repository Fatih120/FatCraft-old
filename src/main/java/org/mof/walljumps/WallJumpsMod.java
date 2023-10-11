package org.mof.walljumps;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import java.util.Hashtable;
import static org.mof.walljumps.WallJumpsMod.*;

@Mod(modid = MOD_ID, version = MOD_VERSION, name = MOD_NAME, acceptedMinecraftVersions = "[1.7.10]")
public final class WallJumpsMod {
    public static final String MOD_ID = "walljumps";
    public static final String MOD_NAME = "Wall-Jumps";
    public static final String MOD_VERSION = "1.0";


    private final Hashtable<EntityPlayer, State> states = new Hashtable<>();

    public State getState(EntityPlayer player) {
        return states.getOrDefault(player, State.Null);
    }

    public void setState(EntityPlayer player, State state) {
        if (state == State.Null)
            states.remove(player);
        else
            states.put(player, state);
    }


    @EventHandler
    public void onInit(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingUpdateEvent event) {
        if (!(event.entity instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.entity;

        if (player.capabilities.isFlying) {
            setState(player, State.Null);
            return;
        }

        State state = getState(player);
        state = state.next(player);
        state.effect(player);
        setState(player, state);
    }
}
