package com.spitfire.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.spitfire.game.controller.MyGame;
import com.spitfire.game.model.MyWorld;
import com.spitfire.game.view.ResourceManager;

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
