package com.spitfire.game.view;

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

import java.util.HashMap;
import java.util.List;

/**
 * Contains all the visual resources for the game and a way to find them when needed. Required key
 * is the name of the texture. These names will be declared during level generation.
 */
public class ResourceManager {

    //-----Fields
    /*Directory Locations*/
    private static final String MAIN_DIR = Gdx.files.getLocalStoragePath().replace("\\", "/");
    private static final String DATA_DIR = "data/";
    private static final String IMAGE_DIR ="images/";
    private static final String PROJECTILE_DIR = MAIN_DIR+"projectiles/"; //Projectile resources
    private static final String ENEMY_DIR = MAIN_DIR+"enemies/"; //Enemy resources
    private static final String LEVEL_DIR = MAIN_DIR+"level/"; //Level resources
    private static final String TURRET_DIR = MAIN_DIR+"turrets/"; //Turret resources
    private static final String HUD_DIR = MAIN_DIR+"hud/"; //HUD resources //TODO: TO BE IMPLEMENTED
    /*Files of Importance*/
    private static final String ATLAS_FILE = "atlas";
    private static final String PROJECTILE_FILE = "projectile";
    private static final String ENEMY_FILE = "enemy";
    private static final String LEVEL_FILE = "level";

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

        loadAllResources();
    }

    //-----Methods
    private void loadAllResources() {
    	loadResourcesInDirectory(MAIN_DIR);
    }

    private void loadResourcesInDirectory(String path) {
        FileHandle[] file_list = Gdx.files.internal(path).list();
    	for(FileHandle file : file_list) {
    	    String s = file.path();
    		if (file.isDirectory())
    			loadResourcesInDirectory(file.path());

    		if (file.extension().equals(ATLAS_FILE))
    			manager.load(file.path(), TextureAtlas.class);

    		if (file.extension().equals(PROJECTILE_FILE))
    			manager.load(file.path(), ProjectileDef.class);

    		if (file.extension().equals(ENEMY_FILE))
    			manager.load(file.path(), EnemyDef.class);
    		
    		if (file.extension().equals(LEVEL_FILE))
    			manager.load(file.path(), Level.class);
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
        return toReturn;
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
