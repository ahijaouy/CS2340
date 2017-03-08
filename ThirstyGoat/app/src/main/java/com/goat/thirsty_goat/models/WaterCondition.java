package com.goat.thirsty_goat.models;

import android.content.res.Resources;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.controllers.App;

/**
 * An enum that represents the water condition for a given water source.
 */

public enum WaterCondition {
    WASTE(App.getResString(R.string.water_condition_waste)),
    TREATABLE_CLEAR(App.getResString(R.string.water_condition_treatable_clear)),
    TREATABLE_MUDDY(App.getResString(R.string.water_condition_treatable_muddy)),
    POTABLE(App.getResString(R.string.water_condition_potable));

    private final String value;

    WaterCondition(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
