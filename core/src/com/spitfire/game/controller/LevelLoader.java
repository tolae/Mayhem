package com.spitfire.game.controller;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.utils.Array;
import com.spitfire.game.controller.EnumManager.FormationStyle;

import com.badlogic.gdx.files.FileHandle;

import java.io.BufferedReader;

public class LevelLoader extends AsynchronousAssetLoader<Level, LevelLoader.LevelParameters> {

    //-----Fields
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
    private static final String START_WAVE_FORMATION = "start";
    private static final String END_WAVE_FORMATION = "end";
    private static final String COMMENT = "#";
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
        Level level = new Level();
        try {
            BufferedReader reader = file.reader((int)file.length());

            String line;
            int line_count = 0;
            int wave_count = 0;
            int formation_count = 0;
            int string_count = 0;
            while((line = reader.readLine()) != null) {
                if (line.startsWith(COMMENT)) continue;

                switch (line_count) {
                    case LEVEL_NAME: //Level name
                        break;
                    case WAVE_COUNT: //Wave count
                        level.init(Integer.parseInt(line));
                        break;
                    case START_WAVE: //Start of wave
                        //Checks if the final wave has been read
                        if (checkWaveCount(level, wave_count)) {
                            return level;
                        }
                        break;
                    case FORMATION_COUNT: //Formation count
                        level.setWave(
                                new Wave(Integer.parseInt(line)),
                                wave_count);
                        break;
                    case START_FORMATION: //Start of formation
                        //Check if there is space for formations
                        if (!checkFormationCount(
                                level.getWave(wave_count),
                                formation_count) &&
                        line.equalsIgnoreCase(START_WAVE_FORMATION)) {
                            break;
                        }
                        //Intentional fall through
                    case END_WAVE: //Wave end
                        //Wait till actual end
                        if (!line.equalsIgnoreCase(END_WAVE_FORMATION)) {
                            line_count = END_WAVE - 1;
                            break;
                        }
                        line_count = START_WAVE - 1;
                        wave_count++;
                        formation_count = 0;
                        string_count = 0;
                        break;
                    case FORMATION_SIZE: //Formation size
                        level.getWave(wave_count).setFormation(
                                new Formation(Integer.parseInt(line)),
                                formation_count);
                        break;
                    case FORMATION_STYLE: //Formation style
                        level.getWave(wave_count)
                                .getFormation(formation_count)
                                .setFormationStyle(
                                        FormationStyle.valueOf(
                                                line.toUpperCase()));
                        break;
                    case FORMATION_NAME: //Formation names
                        //Check if should continue reading
                        if (!line.equalsIgnoreCase(END_WAVE_FORMATION) &&
                        !checkNameCount(
                                level.getWave(wave_count)
                                        .getFormation(formation_count),
                                string_count)) {
                            level.getWave(wave_count).getFormation(formation_count)
                                    .setName(line, string_count);
                            string_count++;
                            line_count = FORMATION_NAME - 1;
                            break;
                        }
                        //Intentional fall through
                    case END_FORMATION: //Formation end
                        //Wait till actual end
                        if (!line.equalsIgnoreCase(END_WAVE_FORMATION)) {
                            line_count = END_FORMATION - 1;
                            break;
                        }
                        //Reset formation
                        line_count = START_FORMATION - 1;
                        formation_count++;
                        string_count = 0;
                        break;
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

    private boolean checkWaveCount(Level level, int current_count) {
        return level.getMaxWaves() < current_count;
    }

    private boolean checkFormationCount(Wave wave, int current_count) {
        return wave.getMaxFormationCount() < current_count;
    }

    private boolean checkNameCount(Formation formation, int current_count) {
        return formation.getSize() < current_count;
    }

    //-----Getters and Setters
    //-----Inner/Anonymous Classes
    /*Currently no uses*/
    static class LevelParameters extends AssetLoaderParameters<Level> {}
}
