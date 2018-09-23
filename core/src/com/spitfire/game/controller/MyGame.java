package com.spitfire.game.controller;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.spitfire.game.model.MyWorld;
import com.spitfire.game.model.ProjectileDef;
import com.spitfire.game.view.GameScreen;
import com.spitfire.game.view.LoadingScreen;
import com.spitfire.game.view.ResourceManager;

/**
 * This is the main container for all controller related processes are handled. This one directly
 * communicates between both the view(Resource Controller) and the model(World). The main loop
 * is contained in this class (render method).
 */
public class MyGame extends Game implements InputProcessor {

    //-----Fields
    /*Camera Statics*/
    //private static final int CAMERA_WIDTH = 1920; Possibly use these for longer maps
    //private static final int CAMERA_HEIGHT = 1080;
    public MyWorld world; //The main container for all model related processes

    public ResourceManager resource_manager; //The main container for all view related processes
    public Camera camera; //The camera in which the player can see
    public SpriteBatch batch; //The main drawing mechanism
    public Vector3 tp; //Touch point of the user

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
        tp = new Vector3();

        this.setScreen(new LoadingScreen(this));
        Gdx.input.setInputProcessor(this);
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

    /*Input Processing Methods*/

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (button != Input.Buttons.LEFT || pointer > 0) return false;
        camera.unproject(tp.set(screenX, screenY, 0));

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    //-----Getters and Setters
}
