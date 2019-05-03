package com.spitfire.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Array;
import com.google.gson.Gson;

import com.badlogic.gdx.files.FileHandle;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.util.*;

public class LevelLoader extends AsynchronousAssetLoader<Level, LevelLoader.LevelParameters> {

    //-----Fields
    /*Actual Level to save*/
    private Level level;

    //-----Constructors
    public LevelLoader(FileHandleResolver resolver) {
        super(resolver);
    }
    //-----Methods
    @Override
    public void loadAsync(AssetManager manager, String file_name,
                          FileHandle file, LevelParameters param) {
        level =  null;
        level = createLevel(file);
    }

    @Override
    public Level loadSync(AssetManager manager, String file_name,
        FileHandle file, LevelParameters param) {
        Level level = this.level;
        this.level = null;
        return level;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String file_name, FileHandle file,
                                                  LevelParameters param) {
        return null;
    }
    /**
     * Creates a level based off the information inside the level file name.
     * @param file: The file handle to parse.
     * @return Returns a newly created level based on the file given
     */
    private Level createLevel(FileHandle file) {
        Level level = null;
        try {
            FileHandle other = Gdx.files.internal(file.path());
            JsonReader reader =
                    new JsonReader(new BufferedReader(other.reader()));
            Gson g = new Gson();
            LevelJSON levelJson = g.fromJson(reader, LevelJSON.class);
            level = new Level(levelJson.waves.length);
            for (JsonElement je: levelJson.waves) {
                Wave wave = new Wave(je.getAsJsonArray().size());
                for (JsonElement jo: je.getAsJsonArray()) {
                    JsonObject joel = jo.getAsJsonObject().get("formation").getAsJsonObject();
                    //noinspection unchecked :  Guaranteed to be Strings
                    Formation formation = new Formation(
                            joel.get("number").getAsInt(),
                            ((ArrayList<String>) g.fromJson(joel.get("ids"),
                                ArrayList.class)).toArray(
                                new String[joel.get("number").getAsInt()]),
                            joel.get("style").getAsString());

                    wave.addFormation(formation);
                }
                if (!level.addWave(wave)) break; //Protects from typos in json
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return level;
    }

    //-----Getters and Setters
    //-----Inner/Anonymous Classes
    /*Currently no uses*/
    static class LevelParameters extends AssetLoaderParameters<Level> {}

    static class LevelJSON {
        JsonElement[] waves;
    }
}
