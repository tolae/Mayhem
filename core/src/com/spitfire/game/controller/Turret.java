package com.spitfire.game.controller;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.spitfire.game.model.MyWorld;
import com.spitfire.game.model.ProjectileDef;

/**
 * This is the container for all turret related logic. It handles firing the projectile, and the
 * crunching of its velocity.
 */
public class Turret {

    //-----Fields
    private static final int TURRET_WIDTH = 50; //To be removed
    private static final int TURRET_HEIGHT = 50; //To be removed
    private static final float TURRET_RADIUS = 64; //The radius of the base of the turret
    //How far a projectile can be pulled to reach maximum velocity
    private static final float MAXIMUM_PULL_DISTANCE = TURRET_RADIUS * 1.5f;
    //Squared version of the maximum pull distance
    private static final float MAXIMUM_PULL_DISTANCE2 = MAXIMUM_PULL_DISTANCE*MAXIMUM_PULL_DISTANCE;

    private ProjectileDef descriptor; //Contains the name of the projectile to fire
    public Rectangle touchable; //The physical part that registers if the turret is touched or not
    //-----Constructors

    /**
     * Default constructor.
     * @param x the x position
     * @param y the y position
     * @param pd ProjectileDef: The projectile descriptor this turret fires
     */
    public Turret(float x, float y, ProjectileDef pd) {
        touchable = new Rectangle(x, y, TURRET_WIDTH, TURRET_HEIGHT);
        descriptor = pd;
    }

    //-----Methods
    /**
     * Details whether this turret should begin firing or not.
     * @param tp TouchPoint: The user touch location
     * @return true if tp lies within the turret's base, false otherwise
     */
    public boolean start(Vector3 tp) {
        if (!touchable.contains(tp.x, tp.y)) return false;

        return true;
    }

    /**
     * Returns the initial descriptor of the projectile. The current velocity is the magnitude,
     * which is a percentage of the maximum velocty.
     * @param tp TouchPoint: The end touch point
     * @return the initial descriptor of the projectile for the world to create
     */
    public void fire(Vector3 tp, final MyWorld w) {
        Vector2 end_loci = new Vector2(tp.x, tp.y);
        Vector2 start_loci = new Vector2();

        float magnitude = MathUtils.clamp(
                end_loci.dst2(touchable.getCenter(start_loci))/MAXIMUM_PULL_DISTANCE2,
                0.25f,
                1f);

        descriptor.setCurrentVelocity(
                end_loci.sub(start_loci).nor().scl(-1f * magnitude * descriptor.getMaxVelocity()));

        w.createProjectile(descriptor);
    }

    //-----Getters and Setters

}
