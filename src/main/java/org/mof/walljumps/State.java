package org.mof.walljumps;

import net.minecraft.entity.player.EntityPlayer;
import javax.vecmath.Vector2d;
import static org.mof.walljumps.Utils.findWallCollidedWithEntity;
import static org.mof.walljumps.Utils.launchEntityForward;

public abstract class State {
    private State() {}

    /** Returns a new state given the current state and the player's state */
    public abstract State next(EntityPlayer player);
    /** Side effect executed on every update, after state changes */
    public void effect(EntityPlayer player) {}

    public static final State Null = new State() {
        @Override
        public State next(EntityPlayer player) {
            if (player.onGround || !player.isSneaking())
                return this;

            Vector2d wall = findWallCollidedWithEntity(player);
            if (wall == null) return this;

            Vector2d pos = new Vector2d(player.posX, player.posZ);

            return new Clinging(pos, wall, 0);
        }
    };

    public static final class Clinging extends State {
        /** The player's position */
        public final Vector2d pos;
        /** X, Y coordinates of the wall the player is on */
        public final Vector2d wall;
        /** Number of updates processed since the player started clinging */
        public final int age;

        Clinging(Vector2d pos, Vector2d wall, int age) {
            this.pos = pos;
            this.wall = wall;
            this.age = age;
        }

        @Override
        public State next(EntityPlayer player) {
            if (player.onGround)
                return Null;
            else if (player.isSneaking())
                return new Clinging(pos, wall, age + 1);
            else
                return new Jumped(wall, 0);
        }

        @Override
        public void effect(EntityPlayer player) {
            player.posX = pos.x;
            player.posZ = pos.y;
            player.motionX = 0;
            player.motionY = age < 40 ? 0 : -0.05;
            player.motionZ = 0;
        }
    }

    public static final class Jumped extends State {
        public final Vector2d prevWall;
        public final int age;

        Jumped(Vector2d prevWall, int age) {
            this.prevWall = prevWall;
            this.age = age;
        }

        @Override
        public State next(EntityPlayer player) {
            if (age < 1)
                return new Jumped(prevWall, age + 1);
            else
                return new Airborne(prevWall);
        }

        @Override
        public void effect(EntityPlayer player) {
            launchEntityForward(player);
        }
    }

    static final class Airborne extends State {
        public final Vector2d prevWall;

        Airborne(Vector2d prevWall) {
            this.prevWall = prevWall;
        }

        @Override
        public State next(EntityPlayer player) {
            if (player.onGround) return Null;
            if (!player.isSneaking()) return this;

            Vector2d wall = findWallCollidedWithEntity(player);
            if (wall == null || wall.equals(prevWall)) return this;

            Vector2d pos = new Vector2d(player.posX, player.posZ);

            return new Clinging(pos, wall, 0);
        }
    }
}
