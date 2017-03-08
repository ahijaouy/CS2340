package com.goat.thirsty_goat.models;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.controllers.App;

/**
 * An enum that represents the water type for a given water source.
 */

public enum WaterType {
    BOTTLED(App.getResString(R.string.water_type_bottled)),
    WELL(App.getResString(R.string.water_type_well)),
    STREAM(App.getResString(R.string.water_type_stream)),
    LAKE(App.getResString(R.string.water_type_lake)),
    SPRING(App.getResString(R.string.water_type_spring)),
    OTHER(App.getResString(R.string.water_type_other));

    private final String value;

    WaterType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
