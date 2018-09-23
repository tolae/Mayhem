package com.spitfire.game.model;

import com.badlogic.gdx.utils.Pool;

/**
 * Interface for all in-game objects.
 */
public interface IEntity extends Pool.Poolable {

    /**
     * Called when this entity death conditions are met. Cleans up the object, freeing its memory
     * back into the pool from which it came. The entity MUST be in-active (isActive = false).
     */
    void onDeath();
}
