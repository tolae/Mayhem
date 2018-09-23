package com.spitfire.game.controller;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Array;
import com.spitfire.game.controller.EnumManager.FormationStyle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;

public class LevelLoader extends AsynchronousAssetLoader<Level, LevelLoader.LevelParameters> {

    //-----Fields
    private static final String LEVEL_ASSET_PATH = "android/assets/data/level/";
    /*File parsing line constants*/
    private static final int LEVEL_NAME = 0;
    private static final int WAVE_COUNT = 1;
    private static final int START_WAVE = 2;
    private static final int FORMATION_COUNT = 3;
    private static final int START_FORMATION = 4;
    private static final int FORMATION_SIZE = 5;
    private static final int FORMATION_STYLE = 6;
    private static final int FORMATION_NAME = 7;
    private static final int END_FORMATION = 8;
    private static final int END_WAVE = 9;
    /*Actual Level to save*/
    Level level;

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
     * @param f_n file_name: The name of the file to parse.
     * @return
     */
    private Level createLevel(FileHandle file) {
        Level level = new Level();
        try {
            BufferedReader reader = file.reader((int)file.length());

            String line = "";
            int line_count = 0;
            int wave_count = 0;
            int formation_count = 0;
            int string_count = 0;
            while((line = reader.readLine()) != null) {
                if (line.startsWith("#")) continue;
                if (wave_count >= level.getMaxWaves()) break;

                switch (line_count) {
                    case LEVEL_NAME: //Level name
                        break;
                    case WAVE_COUNT: //Wave count
                        level.init(Integer.parseInt(line));
                        break;
                    case START_WAVE: //Start of wave
                        break;
                    case FORMATION_COUNT: //Formation count
                        level.setWave(new Wave(Integer.parseInt(line)), wave_count);
                        break;
                    case START_FORMATION: //Start of formation
                        break;
                    case FORMATION_SIZE: //Formation size
                        level.getWave(wave_count).setFormation(
                                new Formation(Integer.parseInt(line)), formation_count);
                        break;
                    case FORMATION_STYLE: //Formation style
                        level.getWave(wave_count).getFormation(formation_count)
                                .setFormationStyle(FormationStyle.valueOf(line.toUpperCase()));
                        break;
                    case FORMATION_NAME: //Formation names
                        level.getWave(wave_count).getFormation(formation_count)
                                .setName(line, string_count);
                        string_count++;
                        break;
                    case END_FORMATION: //Formation end or continuing names
                        if (line.equalsIgnoreCase("end")) {
                            line_count = 4;
                            formation_count++;
                            string_count = 0;
                        } else {
                            level.getWave(wave_count).getFormation(formation_count)
                                    .setName(line, string_count);
                            line_count-=2;
                        }
                        break;
                    case END_WAVE: //Wave end
                        if (line.equalsIgnoreCase("end")) {
                            line_count = 2;
                            wave_count++;
                            formation_count = 0;
                            string_count = 0;
                        }
                    default: //If any changes occur to the file or empty last lines
                        break;
                }

                line_count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return level;
    }

    //-----Getters and Setters
    //-----Inner/Anonymous Classes
    /*Currently no uses*/
    static public class LevelParameters extends AssetLoaderParameters<Level> {}
}
