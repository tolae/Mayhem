package com.spitfire.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.spitfire.game.misc.BodyEditorLoader;

/**
 * Holds all the information for a projectile. This is unique to the instance of the object and will
 * be deleted alongside the projectile it defines.
 */
public class ProjectileDef {

    //-----Fields
    final String name; //The name of the projectile
    final int max_velocity; //The top velocity a projectile to get to.
    final int max_bounces; //The total amount of bounces the projectile has in its life time.
    final int damage;
    final BodyDef body_def; //The internal information for a Box2D body.
    final FixtureDef fixture_def; //The internal information for a Box2D fixture.
    final BodyEditorLoader loader; //The loader for this projectile

    int width, height; //The projectiles texture width and height

    //-----Constructors
    /**
     * Base constructor for the projectile definition.
     * @param n name: The name of the projectile
     * @param mv max_velocity: The top velocity a projectile to get to
     * @param mb max_bounces: The total amount of bounces the projectile has in its life time
     * @param bd body_def: The internal information for a Box2D body
     * @param fd fixture_def: The internal information for a Box2D fixture
     */
    public ProjectileDef(String n, int mv, int mb, int damage, BodyDef bd, FixtureDef fd, BodyEditorLoader bel) {
        name = n;
        max_velocity = mv;
        max_bounces = mb;
        this.damage = damage;
        body_def = bd;
        fixture_def = fd;
        loader = bel;
    }

    /**
     * Copy constructor.
     * @param pd projectile_def: The projectile definition
     */
    public ProjectileDef(ProjectileDef pd) {
        this(pd.name, pd.max_velocity, pd.max_bounces, pd.damage, pd.body_def, pd.fixture_def, pd.loader);

        this.width = pd.width;
        this.height = pd.height;
    }
    //-----Methods

    //-----Getters and Setters
    public void setPosition(float x_pos, float y_pos) {
        this.body_def.position.set(x_pos, y_pos);
    }

    public void setVelocity(Vector2 velocity, float scale) {
        this.body_def.linearVelocity.set(velocity.scl(scale * max_velocity));
    }
}
