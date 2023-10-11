package org.mof.walljumps;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import javax.vecmath.Vector2d;

public final class Utils {
    public static Vector2d findWallCollidedWithEntity(Entity entity) {
        World world = entity.worldObj;
        AxisAlignedBB boundingBox = entity.boundingBox.expand(0.25, 0, 0.25);
        
        int minX = MathHelper.floor_double(boundingBox.minX);
        int maxX = MathHelper.floor_double(boundingBox.maxX + 1.0);
        int minY = MathHelper.floor_double(boundingBox.minY);
        int maxY = MathHelper.floor_double(boundingBox.maxY + 1.0);
        int minZ = MathHelper.floor_double(boundingBox.minZ);
        int maxZ = MathHelper.floor_double(boundingBox.maxZ + 1.0);

        if (boundingBox.minX < 0.0) minX--;
        if (boundingBox.minY < 0.0) minY--;
        if (boundingBox.minZ < 0.0) minZ--;

        for (int x = minX; x < maxX; x++) {
            for (int y = minY; y < maxY; y++) {
                for (int z = minZ; z < maxZ; z++) {
                    Block block = world.getBlock(x, y, z);

                    if (block.getMaterial() != Material.air)
                        return new Vector2d(x, z);
                }
            }
        }

        return null;
    }

    public static void launchEntityForward(Entity entity) {
        double radians = Math.toRadians(entity.rotationYaw + 90);

        entity.motionX = Math.cos(radians) * 0.3;
        entity.motionY = 0.4;
        entity.motionZ = Math.sin(radians) * 0.3;
    }
}
