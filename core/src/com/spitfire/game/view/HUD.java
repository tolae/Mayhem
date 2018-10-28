package com.spitfire.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.spitfire.game.controller.MyGame;

/**
 * Holds much of the GUI. Uses Scene2d.ui for a majority of its handling.
 */
public class HUD extends Stage {

    private static final float PADDING = 116f; /* Padding between top and bottom banner */

    private final Table table = new Table(); /* Main Container that will hold everything */
    private final MyGame game; /* A copy of the game */

    /* Top banner and its subsections */
    private final HorizontalGroup top_banner = new HorizontalGroup();
    private final VerticalGroup life_section = new VerticalGroup(); /* How many lives the player has left */
    private final VerticalGroup title_section = new VerticalGroup(); /* Title of the game */
    private final VerticalGroup settings_section = new VerticalGroup(); /* Settings tab and music tab */

    /* Bot banner and its subsections */
    private final HorizontalGroup bot_banner = new HorizontalGroup();
    private final VerticalGroup turret_section = new VerticalGroup(); /* Turret information (life + energy) */
    private final VerticalGroup projectile_section = new VerticalGroup(); /* Projectile stats */
    private final VerticalGroup level_section = new VerticalGroup(); /* Level and wave information */


    public HUD(final MyGame game) {
        /* Create a new viewport for the HUD that is the screen and call the super constructor for it */
        super(new ScreenViewport());
        /* Save a copy of the game */
        this.game = game;
        /* Set table params */
        table.setBounds(0, 0, game.camera.viewportWidth, game.camera.viewportHeight);
        table.setFillParent(true);
        table.setDebug(true);
        /* Create a skin used by the table and all it's children */
        Skin table_skin = new Skin();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = game.resource_manager.getAsset("hud_font_regular", BitmapFont.class);
        style.fontColor = Color.BLACK;
        table_skin.add("default", style);
        table.setSkin(table_skin);
        /* Add internals for each section */
        life_section.addActor(new Label("Lives", table.getSkin()));
        title_section.addActor(new Label("Mayhem", table.getSkin()));
        settings_section.addActor(new Label("Settings", table.getSkin()));
        settings_section.addActor(new Label("Music", table.getSkin()));
        /* Add sections to banner */
        top_banner.addActor(life_section);
        top_banner.addActor(title_section);
        top_banner.addActor(settings_section);
        /* Add internals for each section */
        turret_section.addActor(new Label("Turret", table.getSkin()));
        turret_section.addActor(new Label("Life", table.getSkin()));
        projectile_section.addActor(new Label("Projectile Info", table.getSkin()));
        level_section.addActor(new Label("Enemies", table.getSkin()));
        /* Add sections to banner */
        bot_banner.addActor(turret_section);
        bot_banner.addActor(projectile_section);
        bot_banner.addActor(level_section);
        /* Add the organized banners to a table */
        table.add(top_banner).padBottom(PADDING);
        table.row(); /* Creates the spacing between the top and bottom */
        table.add(bot_banner).padTop(PADDING);
        /* Add the table to the main HUD */
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
