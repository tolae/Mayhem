package com.spitfire.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.spitfire.game.controller.MyGame;
import com.spitfire.game.controller.Turret;
import com.spitfire.game.model.Entity;
import com.spitfire.game.model.ProjectileDef;

public class GameScreen implements Screen{

    //-----Fields
    private final MyGame game;

    private TextureRegion base;
    private TextureRegion background;
    private Turret turret;

    //-----Constructors
    public GameScreen(final MyGame g) {
        game = g;

        base = g.resource_manager.getTextureRegion("base");
        background = g.resource_manager.getTextureRegion("back");


        turret = new Turret(
                game.camera.viewportWidth / 20f,
                game.camera.viewportHeight / 2f,
                game.resource_manager.getAsset("projectile_xxxx", ProjectileDef.class));
    }

    //-----Methods
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();

		game.batch.setProjectionMatrix(game.camera.combined);

		game.batch.begin();
		game.batch.draw(background, 0, 0);
        game.batch.draw(base, turret.touchable.x, turret.touchable.y);

        for (Entity world_entities: game.world.getActiveComponents()) {
            Vector2 entity_cord = world_entities.getBody().getWorldCenter();
            game.batch.draw(
                    game.resource_manager.getAsset(
                            world_entities.getName(),
                            TextureAtlas.class).findRegion("0"),
                    entity_cord.x,
                    entity_cord.y);
        }

        game.batch.end();

        game.world.step();
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
