package com.spitfire.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.spitfire.game.controller.MyGame;

public class LoadingScreen implements Screen {

    //-----Fields
    private final MyGame game;

    //-----Constructors
    public LoadingScreen(final MyGame g) {
        game = g;
        game.resource_manager.init();
    }

    //-----Methods
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (game.resource_manager.manager.update()) {
            Gdx.app.error("Mayhem", "Done!");
            game.setScreen(new GameScreen(game));
        }

        Gdx.app.error("Mayhem", "Progress: " + game.resource_manager.manager.getProgress());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }


    //-----Getters and Setters
}
