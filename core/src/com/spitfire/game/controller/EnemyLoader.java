package com.spitfire.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.spitfire.game.misc.BodyEditorLoader;
import com.spitfire.game.model.EnemyDef;

import java.io.BufferedReader;

public class EnemyLoader extends AsynchronousAssetLoader<EnemyDef, EnemyLoader.EnemyParameters> {

    //-----Fields
    /*Actual EnemyDef to save*/
    private EnemyDef enemy_def; //TODO Add body stuff here :D
    //-----Constructors
    public EnemyLoader(FileHandleResolver resolver) {
        super(resolver);
    }
    //-----Methods
    @Override
    public void loadAsync(AssetManager manager, String file_name,
                          FileHandle file, EnemyParameters param) {
        enemy_def =  null;
        enemy_def = createEnemyDef(file);
    }

    @Override
    public EnemyDef loadSync(AssetManager manager, String file_name,
        FileHandle file, EnemyParameters param) {
        EnemyDef enemy_def = new EnemyDef(this.enemy_def);
        this.enemy_def = null;
        return enemy_def;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String file_name, FileHandle file,
                                                  EnemyParameters param) {
        return null;
    }

    private EnemyDef createEnemyDef(FileHandle file) {
        EnemyDef ed = null;
        try {
            FileHandle other = Gdx.files.internal(file.path());
            JsonReader reader =
                    new JsonReader(new BufferedReader(other.reader()));
            Gson g = new Gson();
            EnemyJSON enemyJson = g.fromJson(reader, EnemyJSON.class);

            BodyEditorLoader body_info =
                    new BodyEditorLoader(Gdx.files.internal(file.pathWithoutExtension().concat(".json")));
            ed = new EnemyDef(enemyJson, body_info);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ed;
    }
    //-----Getters and Setters
    //-----Inner/Anonymous Classes
    static class EnemyParameters extends AssetLoaderParameters<EnemyDef> {}

    /**
     * Json wrapper
     */
    public static class EnemyJSON {
        public String name;
        public int max_velocity;
        public int health;
        public boolean isBoss;
        public float density;
        public float restitution;
    }
}
