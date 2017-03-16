package com.goat.thirsty_goat.models;

/**
 * An enum that represents the water type for a given water source.
 */

public enum WaterType {
    BOTTLED("Bottled"),
    WELL("Well"),
    STREAM("Stream"),
    LAKE("Lake"),
    SPRING("Spring"),
    OTHER("Other");

    private final String waterType;

    WaterType(String waterType) {
        this.waterType = waterType;
    }

    @Override
    public String toString() {
        return waterType;
    }
}
