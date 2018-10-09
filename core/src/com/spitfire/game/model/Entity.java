package com.spitfire.game.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.spitfire.game.controller.EnumManager;

/**
 * Entity class that contains generic procedures all entities have.
 */
public abstract class Entity implements IEntity {

    //-----Fields

    protected TextureAtlas texture_atlas; //The textures which describe this entity
    protected boolean isActive = false; //Determines if the entity is still in use.
    protected Body body; //Contains the physical body of the entity
    protected EnumManager.EntityType type; //The type of the entity this is
    //-----Constructors

    //-----Methods

    public void onDeath() {
        //Create explosions here or any death related logic
        isActive = false;
    }

    //-----Getters and Setters

    /**
     * Determines if the entity is currently active or not.
     * @return true - if the entity is active, false otherwise.
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Sets the entity active.
     * @param active if the entity should be active or not.
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    public TextureAtlas getTextureAtlas() {
        return texture_atlas;
    }

    public Body getBody() {
        return body;
    }

    public abstract String getName();
}
