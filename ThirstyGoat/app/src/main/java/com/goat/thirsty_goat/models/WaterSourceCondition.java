package com.goat.thirsty_goat.models;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.controllers.App;

/**
 * An enum that represents the water condition for a given water source.
 */

public enum WaterSourceCondition {
    WASTE(App.getResString(R.string.water_condition_waste)),
    TREATABLE_CLEAR(App.getResString(R.string.water_condition_treatable_clear)),
    TREATABLE_MUDDY(App.getResString(R.string.water_condition_treatable_muddy)),
    POTABLE(App.getResString(R.string.water_condition_potable));

    private final String value;

    WaterSourceCondition(String value) {
        this.value = value;
    }

    public static WaterSourceCondition stringOf(String waterConditionString) {
        try {
            return valueOf(waterConditionString);
        } catch (IllegalArgumentException e) {
            return WaterSourceCondition.values()[0];
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
