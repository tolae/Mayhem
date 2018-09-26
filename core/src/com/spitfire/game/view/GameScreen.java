package com.spitfire.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.spitfire.game.controller.Level;
import com.spitfire.game.controller.MyGame;
import com.spitfire.game.controller.Turret;
import com.spitfire.game.model.Entity;
import com.spitfire.game.model.ProjectileDef;

public class GameScreen implements Screen, InputProcessor {

    //-----Fields
    private final MyGame game;

    private TextureRegion background;
    private Turret turret;

    private Vector3 tp;
    //-----Constructors
    GameScreen(final MyGame g) {
        game = g;

        background = g.resource_manager.getTextureRegion("back");

        turret = new Turret(
                game.camera.viewportWidth/20f,
                game.camera.viewportHeight/2f,
                game.resource_manager.getAsset(
                        "projectile_xxxx", ProjectileDef.class),
                new Turret.TurretStyle(
                        game.resource_manager.getTextureRegion("base"),
                        game.resource_manager.getAsset(
                            "projectile_xxxx",
                            TextureAtlas.class).findRegion("0")));

        tp = new Vector3();

        game.setWorldLevel(
                game.resource_manager.getAsset("level_xxxx", Level.class));

        Gdx.input.setInputProcessor(this);
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

        game.stage.act();

		game.batch.begin();
		game.batch.draw(background, 0, 0);

		game.batch.draw(
		        turret.turret_style.turret_base,
                turret.touchable.x - turret.turret_style.turret_base.getRegionWidth()/2f,
                turret.touchable.y - turret.turret_style.turret_base.getRegionHeight()/2f);
		if (turret.touched)
		    game.batch.draw(
		            turret.turret_style.turret_projectile,
                    turret.getPointX() - turret.turret_style.turret_projectile.getRegionWidth()/2f,
                    turret.getPointY() - turret.turret_style.turret_projectile.getRegionHeight()/2f);

        for (Entity world_entities: game.world.getActiveComponents()) {
            Vector2 entity_cord = world_entities.getBody().getWorldCenter();
            TextureRegion entity_texture = game.resource_manager.getAsset(
                    world_entities.getName(),
                    TextureAtlas.class).findRegion("0");
            game.batch.draw(
                    entity_texture,
                    entity_cord.x,
                    entity_cord.y);
        }

        game.batch.end();

        game.stage.draw();

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
    /*Input Processor Methods*/

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
        if (turret.touched) return false;

        game.camera.unproject(tp.set(screenX, screenY, 0));

        if (turret.touchable.contains(tp.x, tp.y )) {
            turret.touched = true;
            turret.setPointPosition(tp.x, tp.y);
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!turret.touched) return false;

        game.camera.unproject(tp.set(screenX, screenY, 0));

        turret.setPointPosition(tp.x, tp.y);
        turret.fire(tp, game.world);
        turret.touched = false;

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!turret.touched) return false;

        game.camera.unproject(tp.set(screenX, screenY, 0));

        turret.setPointPosition(tp.x, tp.y);

        return true;
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
