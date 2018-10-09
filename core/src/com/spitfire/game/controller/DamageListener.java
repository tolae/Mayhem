package com.spitfire.game.controller;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.spitfire.game.model.Entity;

public class DamageListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        String name1 = ((Entity) contact.getFixtureA().getBody().getUserData()).getName();
        String name2 = ((Entity) contact.getFixtureB().getBody().getUserData()).getName();

        return;
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
