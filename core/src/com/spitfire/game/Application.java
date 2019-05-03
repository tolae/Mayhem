package com.spitfire.game;

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.physics.box2d.Box2D;
import com.spitfire.game.controller.MyGame;

/**
 * Main game class. This class contains the main game loop and is the center of the model.
 */
public class Application extends ApplicationAdapter {

    private MyGame game; //My game :D

	@Override
	public void create () {
		//Initializations
		Box2D.init();
		//My game :D
	    game = new MyGame();
	    game.create();
	}

	@Override
	public void render () {
	    game.render();
	}

	@Override
	public void dispose () {
	    game.dispose();
	}
}
