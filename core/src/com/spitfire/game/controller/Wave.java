package com.spitfire.game.controller;

import java.util.Arrays;
import java.util.Iterator;

public class Wave {

    //-----Fields
    private int max_formation_count; //The total number of expected formations
    private Formation[] formations; //The formation descriptions in the wave
    private Iterator<Formation> current_formation; //A pointer to the current formation
    //-----Constructors
    public Wave(int formation_count) {
        max_formation_count = formation_count;
        formations = new Formation[formation_count];
        current_formation = Arrays.asList(formations).iterator();
    }
    //-----Methods
    //-----Getters and Setters
    int getMaxFormationCount() { return max_formation_count; }

    Formation getFormation(int index) {
        return formations[index];
    }

    void setFormation(Formation formations, int index) {
        this.formations[index] = formations;
    }

    Formation getNextFormation() {
        return current_formation.next();
    }
}
