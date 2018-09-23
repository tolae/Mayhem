package com.spitfire.game.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/**
 * Main object and enemy in the game. Each enemy runs from right to left. The player loses if too
 * many enemies or a boss reaches the other side.
 */
public class Enemy extends Entity {

    //-----Fields

    private EnemyDef enemy_def; //Internal enemy information

    //-----Constructors

    //-----Methods

    /**
     * Initializes the enemy based on the definition and the world its on.
     * @param ed enemy_def: Definition of the enemy
     * @param w world: The world in which the enemy exists
     */
    public void init(EnemyDef ed, MyWorld w) {
        //Save a copy of the enemy definition
        enemy_def = new EnemyDef(ed);
        //Grab the texture region associated
        texture_atlas = w.game.resource_manager.getAsset(
            enemy_def.name, TextureAtlas.class);
        //Create and save the enemy body
        body = w.world.createBody(enemy_def.body_def);
        fixture = body.createFixture(enemy_def.fixture_def);
        body.setUserData(this);
    }

    @Override
    public void reset() {

    }

    @Override
    public void onDeath() {
        super.onDeath();
    }

    @Override
    public String getName() { return enemy_def.name; }

    //-----Getters and Setters
}
