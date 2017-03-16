package com.goat.thirsty_goat.models;

/**
 * Created by Walker on 3/11/17.
 */

public enum WaterPurityCondition {
    SAFE("Safe"),
    TREATABLE("Treatable"),
    UNSAFE("Unsafe");

    private final String waterPurityCondition;

    WaterPurityCondition(String waterPurityCondition) {
        this.waterPurityCondition = waterPurityCondition;
    }

    @Override
    public String toString() {
        return waterPurityCondition;
    }
}
