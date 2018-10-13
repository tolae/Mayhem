package com.spitfire.game.model;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class Explosion {

    //-----Fields
    Circle explosion;

    //-----Constructor
    public Explosion(float x, float y, float radius) {
        this.explosion = new Circle(x, y, radius);
    }

    public Explosion(Vector2 position, float radius) {
        this.explosion = new Circle(position, radius);
    }

    public Explosion(Circle circle) {
        this.explosion = new Circle(circle);
    }

    //-----Methods
    public boolean contains(Vector2 position) {
        return explosion.contains(position);
    }

    public Vector2 explode(Vector2 position) {
        Vector2 directional = new Vector2(explosion.x, explosion.y);

        directional = directional.sub(position).nor().scl(-10000f); //TODO add magnitude

        return directional;
    }
}
