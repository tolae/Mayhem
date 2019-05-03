package com.spitfire.game.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Holds information on the number of waves in the level. It details when the next wave starts, and
 * its formation.
 */
public class Level {

    //-----Fields

    private final int max_waves; //The total number of waves in the level
    private final List<Wave> waves; //An array containing all the waves
    private Iterator<Wave> current_wave; //The current wave the level is on

    //-----Constructors

    /**
     * Create's a new level with a set amount of waves.
     * @param wave_count The expected number of waves in the level
     */
    Level(int wave_count) {
        max_waves = wave_count;
        waves = new ArrayList<Wave>();
        current_wave = null;
    }
    //-----Methods

    /**
     * Gets the formation of the current wave.
     * @return A list of positions for each unit in the formation.
     */
    public Formation getFormation() {
        if (current_wave == null)
            current_wave = waves.iterator();
         return current_wave.hasNext() ?
                 current_wave.next().getNextFormation() : null;
    }

    /**
     * Reset's the level (and all inner waves) back to their initial position.
     */
    public void resetLevel() {
        for (Wave w: waves) { w.reset(); }
        current_wave = waves.iterator();
    }

    /**
     * Appends a wave to the end of the level.
     * @param w Wave to be added.
     * @return True if successful, false otherwise.
     */
    boolean addWave(Wave w) {
        if (waves.size() > max_waves)
            return false;
        return waves.add(w);
    }
    //-----Getters and Setters
}
