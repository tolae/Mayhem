package com.spitfire.game.controller;

import com.spitfire.game.controller.EnumManager.FormationStyle;

public class Formation {

    //-----Fields
    protected EnumManager.FormationStyle formation_style; //How the units are organized
    protected int size; //How many units are in the formation
    protected String[] names; //Names of the enemies in said formation

    //-----Constructors
    public Formation(int formation_count) {
        names = new String[formation_count];
        size = formation_count;
        formation_style = FormationStyle.NONE;
    }
    //-----Methods
    //-----Getters and Setters

    protected EnumManager.FormationStyle getFormationStyle() {
        return formation_style;
    }

    protected void setFormationStyle(EnumManager.FormationStyle formation_style) {
        this.formation_style = formation_style;
    }

    protected int getSize() {
        return size;
    }

    protected void setSize(int size) {
        this.size = size;
    }

    protected String getName(int index) {
        return names[index];
    }

    protected void setName(String name, int index) {
        this.names[index] = name;
    }
}
