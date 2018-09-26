package com.spitfire.game.model;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;

/**
 * Holds all the information for an enemy. This is unique to the instance of the object and will
 * be deleted alongside the enemy it defines.
 */
public class EnemyDef {

    //-----Fields
    final protected String name; //The name of the enemy
    final private int max_velocity; //The top velocity a enemy to get to
    final private int health; //The total amount of damage the enemy can take before death
    final private boolean isBoss; //Determines if this enemy is a boss or not
    final BodyDef body_def; //The internal information for a Box2D body
    final FixtureDef fixture_def; //The internal information for a Box2D fixture

    int width, height; //Width and height of the enemy from within the texture

    //-----Constructors

    /**
     * Base constructor for an enemy definition.
     * @param n name: The name of the enemy
     * @param mv max_velocity: The top velocity a enemy to get to
     * @param h health: The total amount of damage the enemy can take before death
     * @param boss isBoss: Determines if this enemy is a boss or not
     * @param bd body_def: The internal information for a Box2D body
     * @param fd fixture_def: The internal information for a Box2D fixture
     */
    public EnemyDef(String n, int mv, int h, boolean boss, BodyDef bd, FixtureDef fd) {
        name = n;
        max_velocity = mv;
        health = h;
        isBoss = boss;
        body_def = bd;
        fixture_def = fd;
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
    }

    //-----Methods

    //-----Getters and Setters
}
