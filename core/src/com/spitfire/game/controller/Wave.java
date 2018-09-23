package com.spitfire.game.controller;

import java.util.Arrays;
import java.util.Iterator;

public class Wave {

    //-----Fields
    protected Formation[] formations; //The formation descriptions in the wave
    protected Iterator<Formation> current_formation; //A pointer to the current formation
    //-----Constructors
    public Wave(int formation_count) {
        formations = new Formation[formation_count];
        current_formation = Arrays.asList(formations).iterator();
    }
    //-----Methods
    //-----Getters and Setters
    public Formation getFormation(int index) {
        return formations[index];
    }

    public void setFormation(Formation formations, int index) {
        this.formations[index] = formations;
    }

    public Formation getNextFormation() {
        return current_formation.next();
    }
}
