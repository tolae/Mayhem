package com.spitfire.game.view;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.spitfire.game.controller.EnemyLoader;
import com.spitfire.game.controller.Level;
import com.spitfire.game.controller.LevelLoader;
import com.spitfire.game.controller.ProjectileLoader;
import com.spitfire.game.model.EnemyDef;
import com.spitfire.game.model.ProjectileDef;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Contains all the visual resources for the game and a way to find them when needed. Required key
 * is the name of the texture. These names will be declared during level generation.
 */
public class ResourceManager {

    //-----Fields
    /*Files of Importance*/
    private static final String ATLAS_FILE = "atlas";
    private static final String PROJECTILE_FILE = "projectile";
    private static final String ENEMY_FILE = "enemy";
    private static final String LEVEL_FILE = "level";

    /*Directory Locations*/
    private String MAIN_DIR;
    private String DATA_DIR;
    private String IMAGE_DIR;
    private String PROJECTILE_DIR; //Projectile resources
    private String ENEMY_DIR; //Enemy resources
    private String LEVEL_DIR; //Level resources
    private String TURRET_DIR; //Turret resources
    private String HUD_DIR; //HUD resources //TODO: TO BE IMPLEMENTED

    AssetManager manager;

    //-----Constructors

    /**
     * Default constructor. Gathers all the textures required throughout the game.
     */
    public ResourceManager() {
        manager = new AssetManager();
        manager.setLoader(EnemyDef.class, new EnemyLoader(new FileHandleResolver() {
            @Override
            public FileHandle resolve(String fileName) {
                return new FileHandle(fileName);
            }
        }));

        manager.setLoader(ProjectileDef.class, new ProjectileLoader(new FileHandleResolver() {
            @Override
            public FileHandle resolve(String fileName) {
                return new FileHandle(fileName);
            }
        }));

        manager.setLoader(Level.class, new LevelLoader(new FileHandleResolver() {
            @Override
            public FileHandle resolve(String fileName) {
                return new FileHandle(fileName);
            }
        }));

        setPaths();
    }

    //-----Methods
    public void init() { loadAllResources(); }

    private void  setPaths() {
        // Changes depending on the system
        if (Gdx.app.getType() == Application.ApplicationType.Desktop ||
            Gdx.app.getType() == Application.ApplicationType.HeadlessDesktop) {
            MAIN_DIR = Gdx.files.getLocalStoragePath().replace("\\", "/");
        } else {
            MAIN_DIR = "";
        }

        DATA_DIR = "data/";
        IMAGE_DIR ="images/";
        PROJECTILE_DIR = MAIN_DIR+"projectiles/";
        ENEMY_DIR = MAIN_DIR+"enemies/";
        LEVEL_DIR = MAIN_DIR+"level/";
        TURRET_DIR = MAIN_DIR+"turrets/";
        HUD_DIR = MAIN_DIR+"hud/";
    }

    private void loadAllResources() {
    	loadResourcesInDirectory(MAIN_DIR);
    }

    private void loadResourcesInDirectory(String path) {
        FileHandle[] file_list = Gdx.files.internal(path).list();
    	for(FileHandle file : file_list) {
    	    String filename = file.path().startsWith("/") ? file.path().substring(1) :
                file.path();
            Gdx.app.error("Mayhem",
                    "Filename: " + filename + "\nExtension: " + file.extension());

    		if (file.isDirectory() || file.extension().equals(""))
    			loadResourcesInDirectory(filename);

    		if (file.extension().equals(ATLAS_FILE))
    			manager.load(filename, TextureAtlas.class);

    		if (file.extension().equals(PROJECTILE_FILE))
    			manager.load(filename, ProjectileDef.class);

    		if (file.extension().equals(ENEMY_FILE))
                manager.load(filename, EnemyDef.class);

    		if (file.extension().equals(LEVEL_FILE))
    			manager.load(filename, Level.class);
    	}
    }

    /**
     * Retrieves the texture associated with a given name
     * @param n name: Name of the texture to be found
     * @return Returns a texture region associated with the given name.
     */
    public final TextureRegion getTextureRegion(String n) {
        Array<String> assets = manager.getAssetNames();
        TextureRegion toReturn = manager.get(MAIN_DIR+"temp.atlas", TextureAtlas.class).findRegion(n);
        if (toReturn == null) {
            toReturn = manager.get(MAIN_DIR+"back.atlas", TextureAtlas.class).findRegion(n);
        }
        return new TextureRegion(toReturn);
    }

    public final <T> T getAsset(String name, Class<T> type) {
        if (name.startsWith(PROJECTILE_FILE)) {
            if (type.isAssignableFrom(TextureAtlas.class))
                return manager.get(
                    PROJECTILE_DIR+name+"/"+IMAGE_DIR+name+"."+ATLAS_FILE, type);
            if (type.isAssignableFrom(ProjectileDef.class))
                return manager.get(
                    PROJECTILE_DIR+name+"/"+DATA_DIR+name+"."+PROJECTILE_FILE, type);
        }

        if (name.startsWith(ENEMY_FILE)) {
            if (type.isAssignableFrom(TextureAtlas.class))
                return manager.get(
                    ENEMY_DIR+name+"/"+IMAGE_DIR+name+"."+ATLAS_FILE, type);
            if (type.isAssignableFrom(EnemyDef.class))
                return manager.get(
                    ENEMY_DIR+name+"/"+DATA_DIR+name+"."+ENEMY_FILE, type);
        }

        if (name.startsWith(LEVEL_FILE)) {
            if (type.isAssignableFrom(TextureAtlas.class))
                return manager.get(
                    LEVEL_DIR+name+"/"+IMAGE_DIR+name+"."+ATLAS_FILE, type);
            if (type.isAssignableFrom(Level.class))
                return manager.get(
                    LEVEL_DIR+name+"/"+DATA_DIR+name+"."+LEVEL_FILE, type);
        }

        /*TODO: Implement lookups for turret, hud, etc, directories*/
        return null;
    }

    public void dispose() {
        manager.dispose();
    }

    //-----Getters and Setters
}
