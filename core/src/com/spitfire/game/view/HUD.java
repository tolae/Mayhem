package com.spitfire.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.spitfire.game.controller.MyGame;

public class HUD extends Stage {

    private static final float PADDING = 116f;

    private final Table table = new Table();
    private final MyGame game;

    public HUD(final MyGame game) {
        super(new ScreenViewport());

        this.game = game;

        table.setFillParent(true);
        table.setDebug(true);

        Skin table_skin = new Skin();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = game.resource_manager.getAsset("hud_font_regular", BitmapFont.class);
        style.fontColor = Color.BLACK;
        table_skin.add("hud_font", style);

        table.setSkin(table_skin);

        HorizontalGroup test1 = new HorizontalGroup();
        HorizontalGroup test2 = new HorizontalGroup();
        VerticalGroup test3 = new VerticalGroup();

        table.add(test1).padBottom(PADDING);
        table.row();
        table.add(test2).padTop(PADDING);

        test3.addActor(new Label("Settings", table.getSkin().get("hud_font", Label.LabelStyle.class)));
        test3.addActor(new Label("Music", table.getSkin().get("hud_font", Label.LabelStyle.class)));
        test1.addActor(new Label("Lives", table.getSkin().get("hud_font", Label.LabelStyle.class)));
        test1.addActor(new Label("Mayhem", table.getSkin().get("hud_font", Label.LabelStyle.class)));
        test1.addActor(test3);
        test1.space(150f);
        test2.addActor(new Label("2", table.getSkin().get("hud_font", Label.LabelStyle.class)));

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
