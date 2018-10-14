package com.spitfire.game.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.spitfire.game.misc.BodyEditorLoader;
import com.spitfire.game.model.ProjectileDef;
import com.spitfire.game.controller.EnumManager.EntityType;

import java.io.BufferedReader;

public class ProjectileLoader extends
		AsynchronousAssetLoader<ProjectileDef, ProjectileLoader.ProjectileParameters> {

	//-----Fields
	private static final String PROJECTILE_ASSET_PATH = 
        "android/assets/data/projectiles/";
	/*File parsing line constants*/
	private static final int PROJECTILE_NAME = 0;
	private static final int PROJECTILE_VELOCITY = 1;
	private static final int PROJECTILE_DAMAGE = 2;
	private static final int PROJECTILE_BOUNCES = 3;
	private static final int PROJECTILE_DENSITY = 4;
	private static final int PROJECTILE_RESTITUTION = 5;
	/*Projectile Constants*/
    //Identifies what this object is
    private static final short CATE_BITS = EntityType.getVal(EntityType.PROJECTILE);
    //Identifies what this object collides with
    private static final short MASK_BITS = 
        (short)(EntityType.getVal(EntityType.ENEMY) |
				EntityType.getVal(EntityType.WALL) |
				EntityType.getVal(EntityType.BOUND));
    //Box2D object friction
	private static final float FRICTION = 0;
	/*Actual ProjectileDef to save*/
	ProjectileDef projectile_def;
	//-----Constructors
	public ProjectileLoader(FileHandleResolver resolver) {
		super(resolver);
	}
	//-----Methods
	@Override
    public void loadAsync(AssetManager manager, String file_name, 
        FileHandle file, ProjectileParameters param) {
        projectile_def =  null;
        projectile_def = createProjectileDef(file);
    }

    @Override
    public ProjectileDef loadSync(AssetManager manager, String file_name,
								   FileHandle file, ProjectileParameters param) {
		ProjectileDef projectile_def = new ProjectileDef(this.projectile_def);
        this.projectile_def = null;
        return projectile_def;
    }

	@Override
    public Array<AssetDescriptor> getDependencies(String file_name,
												  FileHandle file, ProjectileParameters param) {
        return null;
    }

    private ProjectileDef createProjectileDef(FileHandle file) {
    	ProjectileDef pd = null;
    	try {
    		BufferedReader reader = file.reader((int)file.length());

    		String line;
    		int line_count = 0;

    		String n = "";
    		int v = 0;
    		int b = 0;
    		int d = 0;
    		BodyDef bd = new BodyDef();
    		FixtureDef fd = new FixtureDef();
    		while ((line = reader.readLine()) != null) {
    			if (line.startsWith("#")) continue;

    			switch (line_count) {
    				case PROJECTILE_NAME:
    					n = line;
    					break;
    				case PROJECTILE_VELOCITY:
    					v = Integer.parseInt(line);
    					break;
    				case PROJECTILE_BOUNCES:
    					b = Integer.parseInt(line);
    					break;
    				case PROJECTILE_DENSITY:
    					fd.density = Float.parseFloat(line);
    					break;
    				case PROJECTILE_RESTITUTION:
    					fd.restitution = Float.parseFloat(line);
    					break;
                    case PROJECTILE_DAMAGE:
                        d = Integer.parseInt(line);
                        break;
    				default:
    					break;
    			}

    			line_count++;
    		}

    		BodyEditorLoader bel = new BodyEditorLoader(Gdx.files.internal(file.pathWithoutExtension().concat(".json")));
    		bd.type = BodyDef.BodyType.DynamicBody;
    		fd.friction = FRICTION;
    		fd.filter.categoryBits = CATE_BITS;
    		fd.filter.maskBits = MASK_BITS;
    		pd = new ProjectileDef(n, v, b, d, bd, fd, bel);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	return pd;
    }
    //-----Getters and Setters
    //-----Inner/Anonymous Classes
    static public class ProjectileParameters extends
			AssetLoaderParameters<ProjectileDef> {}
}