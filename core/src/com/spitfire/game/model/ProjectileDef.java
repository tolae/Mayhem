package com.spitfire.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.spitfire.game.controller.EnumManager;
import com.spitfire.game.controller.ProjectileLoader;
import com.spitfire.game.misc.BodyEditorLoader;

/**
 * Holds all the information for a projectile. This is unique to the instance of the object and will
 * be deleted alongside the projectile it defines.
 */
public class ProjectileDef {

    //-----Fields
    /*Projectile Constants*/
    //Identifies what this object is
    private static final short CATE_BITS = EnumManager.EntityType.getVal(EnumManager.EntityType.PROJECTILE);
    //Identifies what this object collides with
    private static final short MASK_BITS =
            (short)(EnumManager.EntityType.getVal(EnumManager.EntityType.ENEMY) |
                    EnumManager.EntityType.getVal(EnumManager.EntityType.WALL) |
                    EnumManager.EntityType.getVal(EnumManager.EntityType.BOUND));
    //Box2D object friction
    private static final float FRICTION = 0;

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
     * @param projectileJson A wrapper containing parsed projectile JSON data
     * @param body_info A loader used to create the physics body
     */
    public ProjectileDef(ProjectileLoader.ProjectileJSON projectileJson,
                         BodyEditorLoader body_info) {
        name = projectileJson.name;
        max_velocity = projectileJson.velocity;
        max_bounces = projectileJson.bounces;
        damage = projectileJson.damage;
        body_def = createBodyDef();
        fixture_def = createFixtureDef(projectileJson);
        loader = body_info;
    }

    /**
     * Copy constructor.
     * @param pd projectile_def The projectile definition
     */
    public ProjectileDef(ProjectileDef pd) {
        name = pd.name;
        max_velocity = pd.max_velocity;
        max_bounces = pd.max_bounces;
        damage = pd.damage;
        body_def = pd.body_def;
        fixture_def = pd.fixture_def;
        loader = pd.loader;

        this.width = pd.width;
        this.height = pd.height;
    }
    //-----Methods
    /**
     * Creates a body definition for the enemy.
     * @return A new BodyDef
     */
    private BodyDef createBodyDef() {
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        return bd;
    }

    /**
     * Creates a fixture definition for the projectile.
     * @param projectileJson A wrapper containing parsed projectile JSON data
     * @return A new FixtureDef
     */
    private FixtureDef createFixtureDef(ProjectileLoader.ProjectileJSON projectileJson) {
        FixtureDef fd  = new FixtureDef();
        fd.friction = FRICTION;
        fd.density = projectileJson.density;
        fd.restitution = projectileJson.restitution;
        fd.filter.categoryBits = CATE_BITS;
        fd.filter.maskBits = MASK_BITS;
        return fd;
    }
    //-----Getters and Setters
    public void setPosition(float x_pos, float y_pos) {
        this.body_def.position.set(x_pos, y_pos);
    }

    public void setVelocity(Vector2 velocity, float scale) {
        this.body_def.linearVelocity.set(velocity.scl(scale * max_velocity));
    }
}
