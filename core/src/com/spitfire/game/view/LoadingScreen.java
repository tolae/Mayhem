package com.spitfire.game.view;

import com.badlogic.gdx.Screen;
import com.spitfire.game.controller.MyGame;

public class LoadingScreen implements Screen {

    //-----Fields
    private final MyGame game;

    //-----Constructors
    public LoadingScreen(final MyGame g) {
        game = g;
    }

    //-----Methods
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (game.resource_manager.manager.update()) {
            game.setScreen(new GameScreen(game));
        }

        System.out.println("Progress: " + game.resource_manager.manager.getProgress());
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
