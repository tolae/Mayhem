package com.spitfire.game.controller;

/**
 * Contains all the enums to be used in the game.
 */
public class EnumManager {
    /**
     * Describes the style in which the enemies will appear in a particular wave
     */
    public enum FormationStyle {
        NONE,
        LINE_1,
    }
    /**
     * Describes what kind of entity is being used
     */
    public enum EntityType {
        UNDEFINED(0x0000),
        PROJECTILE(0x0001),
        ENEMY(0x0002),
        WALL(0x0004),
        ALL(0xFFFF);

        public short val;

        EntityType(int val) {
            this.val = (short) val;
        }

        public EntityType getType(short val) {
            int ordinal = 31 - Integer.numberOfLeadingZeros(val);
            if (ordinal <= -1) //Undefined
                return EntityType.UNDEFINED;
            if (ordinal > EntityType.values().length) //Out of bounds
                return EntityType.UNDEFINED;
            return EntityType.values()[ordinal];
        }
    }
}
