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
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.spitfire.game.misc.BodyEditorLoader;
import com.spitfire.game.model.ProjectileDef;
import com.spitfire.game.controller.EnumManager.EntityType;

import java.io.BufferedReader;

public class ProjectileLoader extends
		AsynchronousAssetLoader<ProjectileDef, ProjectileLoader.ProjectileParameters> {

	//-----Fields
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
			FileHandle other = Gdx.files.internal(file.path());
			JsonReader reader =
					new JsonReader(new BufferedReader(other.reader()));
			Gson g = new Gson();
			ProjectileJSON projectileJson = g.fromJson(reader,
					ProjectileJSON.class);

			BodyEditorLoader body_info =
					new BodyEditorLoader(Gdx.files.internal(file.pathWithoutExtension().concat(".json")));
    		pd = new ProjectileDef(projectileJson, body_info);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}

    	return pd;
    }
    //-----Getters and Setters
    //-----Inner/Anonymous Classes
    static class ProjectileParameters extends
			AssetLoaderParameters<ProjectileDef> {}

	public static class ProjectileJSON {
		public String name;
		public int velocity;
		public int damage;
		public int bounces;
		public int density;
		public int restitution;
	}
}