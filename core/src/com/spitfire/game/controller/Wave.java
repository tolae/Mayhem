package com.spitfire.game.controller;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Wave {

    //-----Fields
    private int max_formation_count; //The total number of expected formations
    private List<Formation> formations; //The formation descriptions in the wave
    private Iterator<Formation> current_formation; //A pointer to the current formation
    //-----Constructors
    Wave(int formation_count) {
        max_formation_count = formation_count;
        formations = new ArrayList<Formation>();
        current_formation = null;
    }
    //-----Methods
    void reset() {
        current_formation = formations.iterator();
    }

    //-----Getters and Setters
    void addFormation(Formation formations) {
        this.formations.add(formations);
    }

    Formation getNextFormation() {
        if (current_formation == null)
            current_formation = formations.iterator();
        return current_formation.hasNext() ? current_formation.next() : null;
    }
}
