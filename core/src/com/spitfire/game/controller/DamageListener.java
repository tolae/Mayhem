package com.spitfire.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.spitfire.game.model.Enemy;
import com.spitfire.game.model.Entity;
import com.spitfire.game.model.Projectile;

public class DamageListener implements ContactListener {

    private final MyGame game; //Instance of this game

    public DamageListener(MyGame game) {
        this.game = game;
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {
        /*Get who called the contact*/
        EnumManager.EntityType cate_bits_A =
                EnumManager.EntityType.getType(contact.getFixtureA().getFilterData().categoryBits);
        Object obj_A = contact.getFixtureA().getBody().getUserData();
        EnumManager.EntityType cate_bits_B =
                EnumManager.EntityType.getType(contact.getFixtureB().getFilterData().categoryBits);
        Object obj_B = contact.getFixtureB().getBody().getUserData();
        /*Contact Logic*/
        //Projectile hit something (doesn't matter what it hit
        if (cate_bits_A == EnumManager.EntityType.PROJECTILE) {
            ((Projectile) obj_A).bounce();
        } if (cate_bits_B == EnumManager.EntityType.PROJECTILE) {
            ((Projectile) obj_B).bounce();
        }
        //Something hit the end bounds. Delete them.
        if (cate_bits_A == EnumManager.EntityType.BOUND) {
            if (((Boolean) obj_A))
                game.lose();
            ((Entity) obj_B).setShouldExplode(false);
            ((Entity) obj_B).onDeath();
            return;
        } if (cate_bits_B == EnumManager.EntityType.BOUND) {
            if (((Boolean) obj_B))
                game.lose();
            ((Entity) obj_A).setShouldExplode(false);
            ((Entity) obj_A).onDeath();
        }
        //Enemy collided with Projectile
        if (cate_bits_A == EnumManager.EntityType.PROJECTILE && cate_bits_B == EnumManager.EntityType.ENEMY) {
            ((Enemy) obj_B).hit((Projectile) obj_A);
        } else if (cate_bits_B == EnumManager.EntityType.PROJECTILE && cate_bits_A == EnumManager.EntityType.ENEMY) {
            ((Enemy) obj_A).hit((Projectile) obj_B);
        }
        //Enemy collided with wall
        if (cate_bits_A == EnumManager.EntityType.WALL && cate_bits_B == EnumManager.EntityType.ENEMY) {
            ((Enemy) obj_B).hit();
        } else if (cate_bits_B == EnumManager.EntityType.WALL && cate_bits_A == EnumManager.EntityType.ENEMY) {
            ((Enemy) obj_A).hit();
        }
        //Enemy collided with enemy
        if (cate_bits_A == cate_bits_B && cate_bits_A == EnumManager.EntityType.ENEMY) {
            ((Enemy) obj_A).hit((Enemy) obj_B);
            ((Enemy) obj_B).hit((Enemy) obj_A);
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
