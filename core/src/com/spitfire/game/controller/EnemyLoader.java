package com.spitfire.game.controller;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.spitfire.game.model.EnemyDef;
import com.spitfire.game.controller.EnumManager.EntityType;

import java.io.BufferedReader;

public class EnemyLoader extends AsynchronousAssetLoader<EnemyDef, EnemyLoader.EnemyParameters> {

    //-----Fields
    private static final String ENEMY_ASSET_PATH = "android/assets/data/enemies/";
    /*File parsing line constants*/
    private static final int ENEMY_NAME = 0;
    private static final int ENEMY_VELOCITY = 1;
    private static final int ENEMY_HEALTH = 2;
    private static final int ENEMY_BOSS = 3;
    private static final int ENEMY_DENSITY = 4;
    private static final int ENEMY_RESTITUTION = 5;
    /*Enemy Constants*/
    //Identifies what this object is
    private static final short CATE_BITS = EntityType.ENEMY.val;
    //Identifies what this object collides with
    private static final short MASK_BITS = EntityType.ALL.val;
    //Box2D object friction
    private static final float FRICTION = 0;
    /*Actual EnemyDef to save*/
    EnemyDef enemy_def;
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
            BufferedReader reader = file.reader((int)file.length());

            String line;
            int line_count = 0;

            String n = "";
            int v = 0;
            int h = 0;
            boolean b = false;
            BodyDef bd = new BodyDef();
            FixtureDef fd = new FixtureDef();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#")) continue;

                switch (line_count) {
                    case ENEMY_NAME:
                        n = line;
                        break;
                    case ENEMY_VELOCITY:
                        v = Integer.parseInt(line);
                        break;
                    case ENEMY_HEALTH:
                        h = Integer.parseInt(line);
                        break;
                    case ENEMY_BOSS:
                        b = Boolean.parseBoolean(line);
                        break;
                    case ENEMY_DENSITY:
                        fd.density = Float.parseFloat(line);
                        break;
                    case ENEMY_RESTITUTION:
                        fd.restitution = Float.parseFloat(line);
                        break;
                    default:
                        break;
                }

                line_count++;
            }

            bd.type = BodyDef.BodyType.DynamicBody;
            fd.friction = FRICTION;
            fd.filter.categoryBits = CATE_BITS;
            fd.filter.maskBits = MASK_BITS;
            ed = new EnemyDef(n, v, h, b, bd, fd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ed;
    }
    //-----Getters and Setters
    //-----Inner/Anonymous Classes
    static public class EnemyParameters extends AssetLoaderParameters<EnemyDef> {}
}
