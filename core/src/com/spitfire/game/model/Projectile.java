package com.spitfire.game.model;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Main object that is fired from the turret. Doesn't collide with other projectiles.
 */
public class Projectile extends Entity {

    //-----Fields

    private ProjectileDef projectile_def; //Internal projectile information

    //-----Methods

    /**
     * Initializes the projectile for use. The object becomes active after initialization.
     * @param pd - Internal information of the projectile
     */
    void init(ProjectileDef pd, MyWorld w) {
        //Save a copy of the projectile definition
        projectile_def = new ProjectileDef(pd);
        //Grab the texture region associated
        texture_atlas = w.game.resource_manager.getAsset(
            projectile_def.name, TextureAtlas.class);
        projectile_def.width = texture_atlas.getRegions().get(0).originalWidth;
        projectile_def.height = texture_atlas.getRegions().get(0).originalHeight;
        //Create and save body
        body = w.world.createBody(projectile_def.body_def);
        projectile_def.loader.attachFixture(body, projectile_def.name, projectile_def.fixture_def, 60f);
        body.setUserData(this);
        //Body initial velocity, if any
        body.setLinearVelocity(projectile_def.current_velocity);
    }

    @Override
    public void reset() {
        //Clean the projectile definition
        projectile_def = null;
        //Clean the body
        body.setUserData(null);
    }

    @Override
    public void onDeath() {
        super.onDeath();
    }

    @Override
    public String getName() { return projectile_def.getName(); }
}
