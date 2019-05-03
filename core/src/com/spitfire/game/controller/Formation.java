package com.spitfire.game.controller;

import com.spitfire.game.model.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Formation {

    /**
     * Describes the style in which the enemies will appear in a particular wave
     */
    public enum FormationStyle {
        NONE,
        LINE_1,
    }

    //-----Fields
    private FormationStyle formation_style; //How the units are organized
    private final int size; //How many units are in the formation
    private final String[] names; //Names of the enemies in said formation

    //-----Constructors
    Formation(int formation_count, String[] name_list, String style) throws IllegalFormationException {
        size = formation_count;
        formation_style = FormationStyle.valueOf(style.toUpperCase());
        names = createNameArray(name_list);
    }
    //-----Methods

    /**
     * Creates a new name array with the given list of names with the
     * appropriate size.
     * @param n The given list of names.
     * @return A list of names matching the style (None returns null)
     */
    private String[] createNameArray(String[] n) throws IllegalFormationException {
        String[] names = null;
        try {
            switch (formation_style) {
                case NONE:
                    break;
                case LINE_1:
                    names = new String[size];
                    Arrays.fill(names, n[0]);
                    break;
                default:
                    throw new IllegalFormationException(this);
            }
        } catch (IndexOutOfBoundsException e) {
            // This is okay
        }
        return names;
    }

    /**
     * Creates a formation based on the formations information.
     * @return a list of units in their relative positions
     */
    public List<Position> createFormation() {
        List<Position> positions = new ArrayList<Position>();
        if (formation_style == FormationStyle.NONE) return null;

        switch (formation_style) {
            // TODO: Create a factory for formation styles (new class)
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

    @Override
    public String toString() {
        return "Style: " + formation_style.toString() + "\n" +
        "Name List: " + Arrays.toString(names) + " (" + size + ")";
    }
    //-----Getters and Setters
    //-----Inner/Anonymous Classes
    public class IllegalFormationException extends Exception {
        Formation formation;
        IllegalFormationException(Formation bad_formation) {
            formation = bad_formation;
        }

        public String toString() {
            return "IllegalFormationException: Invalid formation given " +
                    " " + formation.toString();
        }
    }
}
