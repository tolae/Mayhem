package com.spitfire.game.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.CircleShape;

/**
 * Main object and enemy in the game. Each enemy runs from right to left. The player loses if too
 * many enemies or a boss reaches the other side.
 */
public class Enemy extends Entity {

    //-----Fields
    private EnemyDef enemy_def; //Internal enemy information
    private int health;

    //-----Constructors

    //-----Methods

    /**
     * Initializes the enemy based on the definition and the world its on.
     * @param ed enemy_def: Definition of the enemy
     * @param w world: The world in which the enemy exists
     */
    void init(EnemyDef ed, MyWorld w) {
        //Save a copy of the enemy definition
        enemy_def = new EnemyDef(ed);
        //Grab the texture region associated
        texture_atlas = w.game.resource_manager.getAsset(
            enemy_def.name, TextureAtlas.class);
        int width = texture_atlas.getRegions().get(0).originalWidth;
        int height = texture_atlas.getRegions().get(0).originalHeight;
        //Create and save the enemy body
        body = w.world.createBody(enemy_def.body_def);
        //Move body to spawn location
        body.setTransform(
                enemy_def.width * width + w.game.camera.viewportWidth,
                enemy_def.height * height + w.game.camera.viewportHeight / 2f,
                0);
        body.setLinearVelocity(enemy_def.max_velocity * -1, 0f);
        //Create and save fixture
        enemy_def.loader.attachFixture(body, enemy_def.name, enemy_def.fixture_def, 60f);
        //Save this to body
        body.setUserData(this);
        //Save texture region width
        enemy_def.width = width;
        enemy_def.height = height;
        //Set initial health
        this.health = enemy_def.health;
    }

    @Override
    public void reset() {

    }

    @Override
    public void onDeath() {
        super.onDeath();
    }

    private void isDead() {
        if (health < 0)
            onDeath();
    }

    @Override
    public String getName() { return enemy_def.name; }

    /**
     * Called when enemy collided with a wall. Damage is based off current velocity.
     */
    public void hit() {
        this.health -= (int) (body.getLinearVelocity().len() * 0.1f);

        isDead();
    }

    /**
     * Called when enemy collided with another enemy. Damage is based off current velocity
     * @param other_enemy the enemy this enemy collided with.
     */
    public void hit(Enemy other_enemy) {
        this.health -= (int) ((body.getLinearVelocity().len() + other_enemy.body.getLinearVelocity().len()) * 0.05f);

        isDead();
    }

    /**
     * Called when enemy collided with a projectile. Damage is based off the projectile damage.
     * @param projectile the projectile this enemy collided with
     */
    public void hit(Projectile projectile) {
        this.health -= projectile.getDamage();

        isDead();
    }

    //-----Getters and Setters
}
