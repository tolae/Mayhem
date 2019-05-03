package com.spitfire.game.model;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.spitfire.game.controller.EnemyLoader;
import com.spitfire.game.controller.EnumManager;
import com.spitfire.game.misc.BodyEditorLoader;

/**
 * Holds all the information for an enemy. This is unique to the instance of the object and will
 * be deleted alongside the enemy it defines.
 */
public class EnemyDef {

    //-----Fields
    /*Enemy Constants*/
    //Identifies what this object is
    private static final short CATE_BITS = EnumManager.EntityType.getVal(EnumManager.EntityType.ENEMY);
    //Identifies what this object collides with
    private static final short MASK_BITS = EnumManager.EntityType.getVal(EnumManager.EntityType.ALL);
    //Box2D object friction
    private static final float FRICTION = 0;
    final String name; //The name of the enemy
    final int max_velocity; //The top velocity a enemy to get to (naturally)
    final int health; //The total amount of damage the enemy can take before death
    final boolean isBoss; //Determines if this enemy is a boss or not
    final BodyDef body_def; //The internal information for a Box2D body
    final FixtureDef fixture_def; //The internal information for a Box2D fixture

    final BodyEditorLoader loader; //The loader for this projectile

    int width, height; //Width and height of the enemy from within the texture

    //-----Constructors

    /**
     * Base constructor for an enemy definition.
     * @param enemyJSON: A wrapper containing parsed enemy JSON data
     * @param body_info: A loader used to create the physics body
     */
    public EnemyDef(EnemyLoader.EnemyJSON enemyJSON,
                    BodyEditorLoader body_info) {
        name = enemyJSON.name;
        max_velocity = enemyJSON.max_velocity;
        health = enemyJSON.health;
        isBoss = enemyJSON.isBoss;
        body_def = createBodyDef();
        fixture_def = createFixtureDef(enemyJSON);
        loader = body_info;
    }

    /**
     * Copy constructor.
     * @param ed enemy_def: The enemy definition
     */
    public EnemyDef(EnemyDef ed) {
        name = ed.name;
        max_velocity = ed.max_velocity;
        health = ed.health;
        isBoss = ed.isBoss;
        body_def = ed.body_def;
        fixture_def = ed.fixture_def;
        loader = ed.loader;

        width = ed.width;
        height = ed.height;
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
     * Creates a fixture definition for the enemy.
     * @param enemyJSON A wrapper containing parsed enemy JSON data
     * @return A new FixtureDef
     */
    private FixtureDef createFixtureDef(EnemyLoader.EnemyJSON enemyJSON) {
        FixtureDef fd  = new FixtureDef();
        fd.friction = FRICTION;
        fd.density = enemyJSON.density;
        fd.restitution = enemyJSON.restitution;
        fd.filter.categoryBits = CATE_BITS;
        fd.filter.maskBits = MASK_BITS;
        return fd;
    }
    //-----Getters and Setters

    @Override
    public String toString() {
        String s = name;
        s += "\n\tMax Velocity: " + max_velocity;
        s += "\n\tHealth: " + health;
        s += "\n\tisBoss: " + isBoss;
        s += "\n\tBodyDef: " + body_def.toString();
        s += "\n\tFixtureDef: " + fixture_def.toString();
        s += "\n\tLoader: " + loader.toString();
        s += "\n\tWidth: " + width;
        s += "\n\tHeight: " + height;
        return s;
    }
}
