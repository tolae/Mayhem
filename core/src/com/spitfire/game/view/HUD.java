package com.spitfire.game.view;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.spitfire.game.controller.MyGame;

public class HUD extends Stage {

    private final Table table = new Table();
    private final MyGame game;

    public HUD(final MyGame game) {
        super(new ScreenViewport());

        this.game = game;

        table.setFillParent(true);
        table.setDebug(true);

        this.addActor(table);
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
