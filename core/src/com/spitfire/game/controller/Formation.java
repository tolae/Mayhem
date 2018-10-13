package com.spitfire.game.controller;

import com.spitfire.game.controller.EnumManager.FormationStyle;
import com.spitfire.game.model.Position;

import java.util.ArrayList;
import java.util.List;

public class Formation {

    //-----Fields
    private FormationStyle formation_style; //How the units are organized
    private final int size; //How many units are in the formation
    private final String[] names; //Names of the enemies in said formation

    //-----Constructors
    public Formation(int formation_count) {
        names = new String[formation_count];
        size = formation_count;
        formation_style = FormationStyle.NONE;
    }
    //-----Methods

    /**
     * Creates a formation based on the formations information.
     * @return a list of units in their relative positions
     */
    public List<Position> createFormation() {
        List<Position> positions = new ArrayList<Position>();
        if (formation_style == FormationStyle.NONE) return positions;

        switch (formation_style) {
            case LINE_1:
                int current_size = 0;
                for (int y_counter = -2; y_counter < size-2;) {
                    //Ensure the current name doesn't go over the name length
                    current_size %= size;
                    //Ensure there is a name to add to the position
                    if (names[current_size] != null) {
                        positions.add(new Position(
                                names[current_size],
                                0,
                                y_counter));
                        y_counter++;
                        current_size++;
                    } else {
                        current_size = 0; //Out of names, go back to the beginning
                    }
                }
                break;
        }

        return positions;
    }
    //-----Getters and Setters
    void setFormationStyle(EnumManager.FormationStyle formation_style) {
        this.formation_style = formation_style;
    }

    protected int getSize() {
        return size;
    }

    void setName(String name, int index) {
        this.names[index] = name;
    }
}
