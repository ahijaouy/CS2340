package com.goat.thirsty_goat.models;

/**
 * An enum that represents the water condition for a given water source.
 */

public enum WaterSourceCondition {
    WASTE("Waste"),
    TREATABLE_CLEAR("Treatable-Clear"),
    TREATABLE_MUDDY("Treatable-Muddy"),
    POTABLE("Potable");

    private final String waterSourceCondition;

    WaterSourceCondition(String waterSourceCondition) {
        this.waterSourceCondition = waterSourceCondition;
    }

    @Override
    public String toString() {
        return waterSourceCondition;
    }
}
