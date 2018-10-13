package com.spitfire.game.controller;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
import com.spitfire.game.model.MyWorld;
import com.spitfire.game.model.ProjectileDef;

/**
 * This is the container for all turret related logic. It handles firing the projectile, and the
 * crunching of its velocity.
 */
public class Turret {

    //-----Fields
    private static final float TURRET_RADIUS = 64; //The radius of the base of the turret
    //How far a projectile can be pulled to reach maximum velocity
    private static final float MAXIMUM_PULL_DISTANCE = TURRET_RADIUS * 1.5f;
    //Squared version of the maximum pull distance
    private static final float MAXIMUM_PULL_DISTANCE2 = MAXIMUM_PULL_DISTANCE*MAXIMUM_PULL_DISTANCE;

    private ProjectileDef descriptor; //Contains the name of the projectile to fire
    public Circle touchable; //The physical part that registers if the turret is touched or not
    public Circle bounds; //Bounds for how far the projectile can be pulled
    private Vector2 point; //The point where the user drags the projectile to shoot
    private Vector2 point_percent;
    public final TurretStyle turret_style; //The TextureRegion representing the turret
    public boolean touched;
    //-----Constructors
    /**
     * Default constructor.
     * @param x the x position
     * @param y the y position
     * @param projectile_def: The projectile descriptor this turret fires
     */
    public Turret(float x, float y, ProjectileDef projectile_def, final TurretStyle style) {
        touchable = new Circle(x, y, TURRET_RADIUS);
        bounds = new Circle(x, y, MAXIMUM_PULL_DISTANCE);
        descriptor = projectile_def;
        point = new Vector2(x,y);
        point_percent = new Vector2();
        turret_style = style;
        touched = false;
    }

    //-----Methods
    /**
     * Returns the initial descriptor of the projectile. The current velocity is the magnitude,
     * which is a percentage of the maximum velocty.
     * @param tp TouchPoint: The end touch point
     */
    public void fire(Vector3 tp, final MyWorld w) {
        Vector2 end_loci = new Vector2(tp.x, tp.y);
        Vector2 start_loci = new Vector2(touchable.x, touchable.y);

        float magnitude = MathUtils.clamp(
                end_loci.dst2(start_loci)/MAXIMUM_PULL_DISTANCE2,
                0.25f,
                1f);

        descriptor.setVelocity(end_loci.sub(start_loci).nor(), magnitude * -1);

        descriptor.setPosition(
                touchable.x - turret_style.turret_base.getRegionWidth()/2f,
                touchable.y - turret_style.turret_base.getRegionHeight()/2f);

        w.createProjectile(descriptor);
    }
    //-----Getters and Setters
    public float getPointX() {
        return point.x;
    }

    public float getPointY() {
        return point.y;
    }

    public void setPointPosition(float x, float y) {
        point.set(x, y);
        point_percent.set((x - touchable.x) / bounds.radius, (y - touchable.y) / bounds.radius);

        float length = point_percent.len();
        if (length > 1) point_percent.scl(1f / length);

        if (!bounds.contains(x, y)) {
            point.set(point_percent).nor().scl(bounds.radius).add(bounds.x, bounds.y);
        }
    }

    //------Inner/Anonymous Classes
    public static class TurretStyle {
        public TextureRegion turret_base;
        public TextureRegion turret_projectile;

        public TurretStyle(TextureRegion base, TextureRegion projectile) {
            turret_base = base;
            turret_projectile = projectile;
        }
    }
}
