package com.spitfire.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Holds all the information for a projectile. This is unique to the instance of the object and will
 * be deleted alongside the projectile it defines.
 */
public class ProjectileDef {

    //-----Fields
    protected final String name; //The name of the projectile
    protected final int max_velocity; //The top velocity a projectile to get to.
    protected final int max_bounces; //The total amount of bounces the projectile has in its life time.
    protected final BodyDef body_def; //The internal information for a Box2D body.
    protected final FixtureDef fixture_def; //The internal information for a Box2D fixture.

    protected Vector2 current_velocity; //The initial velocity of the projectile
    protected int current_bounces; //The initial bounce count of the projectile

    //-----Constructors
    /**
     * Base constructor for the projectile definition.
     * @param n name: The name of the projectile
     * @param mv max_velocity: The top velocity a projectile to get to
     * @param cv current_velocity: The initial velocity of the projectile
     * @param mb max_bounces: The total amount of bounces the projectile has in its life time
     * @param cb current_bounces: The initial bounce count of the projectile
     * @param bd body_def: The internal information for a Box2D body
     * @param fd fixture_def: The internal information for a Box2D fixture
     */
    public ProjectileDef(String n, int mv, Vector2 cv, int mb, int cb, BodyDef bd, FixtureDef fd) {
        name = n;
        max_velocity = mv;
        current_velocity = cv;
        max_bounces = mb;
        current_bounces = cb;
        body_def = bd;
        fixture_def = fd;
    }

    /**
     * Copy constructor.
     * @param pd projectile_def: The projectile definition
     */
    public ProjectileDef(ProjectileDef pd) {
        this(pd.name, pd.max_velocity, pd.current_velocity,
                pd.max_bounces, pd.current_bounces, pd.body_def, pd.fixture_def);
    }

    /**
     * Constructor with zero'd initials. Requires a name.
     */
    public ProjectileDef(String n, int mv, int mb, BodyDef bd, FixtureDef fd) {
        this(n, mv, new Vector2(0,0), mb, 0, bd, fd);
    }

    //-----Methods

    //-----Getters and Setters


    public int getMaxVelocity() {
        return max_velocity;
    }

    public int getMaxBounces() {
        return max_bounces;
    }

    public String getName() {
        return name;
    }

    public BodyDef getBodyDef() {
        return body_def;
    }

    public FixtureDef getFixtureDef() {
        return fixture_def;
    }

    public Vector2 getCurrentVelocity() {
        return current_velocity;
    }

    public void setCurrentVelocity(Vector2 cv) {
        this.current_velocity = cv;
    }

    public int getCurrentBounces() {
        return current_bounces;
    }

    public void setCurrentBounces(int cb) {
        this.current_bounces = cb;
    }
}
