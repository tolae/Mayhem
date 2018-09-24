package com.spitfire.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.spitfire.game.model.MyWorld;
import com.spitfire.game.view.LoadingScreen;
import com.spitfire.game.view.ResourceManager;

/**
 * This is the main container for all controller related processes are handled. This one directly
 * communicates between both the view(Resource Controller) and the model(World). The main loop
 * is contained in this class (render method).
 */
public class MyGame extends Game {

    //-----Fields
    /*Camera Statics*/
    //private static final int CAMERA_WIDTH = 1920; Possibly use these for longer maps
    //private static final int CAMERA_HEIGHT = 1080;
    public MyWorld world; //The main container for all model related processes

    public ResourceManager resource_manager; //The main container for all view related processes
    public Camera camera; //The camera in which the player can see
    public SpriteBatch batch; //The main drawing mechanism

    public Stage stage; //Stage for actors and the HUD
    //-----Constructors
    //-----Methods
    @Override
    public void create() {
        world = new MyWorld(this);
        resource_manager = new ResourceManager();

        camera = new OrthographicCamera(
        	Gdx.graphics.getWidth(), 
        	Gdx.graphics.getHeight());

        camera.position.set(
        	camera.viewportWidth / 2f, 
        	camera.viewportHeight / 2f, 0);

        camera.update();

        world.init();

        batch = new SpriteBatch();
        stage = new Stage(new ScreenViewport(), batch);

        this.setScreen(new LoadingScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }
    //-----Getters and Setters
}
