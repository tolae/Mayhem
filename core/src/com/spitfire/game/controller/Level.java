package com.spitfire.game.controller;

import java.util.Arrays;
import java.util.Iterator;

/**
 * Holds information on the number of waves in the level. It details when the next wave starts, and
 * its formation.
 */
public class Level {

    //-----Fields

    private int max_waves = 0; //The total number of waves in the level
    private Wave[] waves; //An array containing all the waves
    private Iterator<Wave> current_wave; //The current wave the level is on

    //-----Constructors
    //-----Methods
    public void init(int wave_count) {
        max_waves = wave_count;
        waves = new Wave[wave_count];
        current_wave = Arrays.asList(waves).iterator();
    }
    /**
     * Gets the formation of the current wave.
     * @return A list of positions for each unit in the formation.
     */
    public Formation getFormation() {
        if (current_wave.hasNext())
            return current_wave.next().getNextFormation();
        else
            return null;
    }
    //-----Getters and Setters
    public int getMaxWaves() {
        return max_waves;
    }

    public void setMaxWaves(int max_waves) {
        this.max_waves = max_waves;
    }

    public Wave getWave(int index) {
        return waves[index];
    }

    public void setWave(Wave w, int index) {
        this.waves[index] = w;
    }
}
