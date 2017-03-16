package com.goat.thirsty_goat.models;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.controllers.App;

/**
 * An enum that represents the water condition for a given water source.
 */

public enum SourceCondition {
    WASTE(App.getResString(R.string.source_condition_waste)),
    TREATABLE_CLEAR(App.getResString(R.string.source_condition_treatable_clear)),
    TREATABLE_MUDDY(App.getResString(R.string.source_condition_treatable_muddy)),
    POTABLE(App.getResString(R.string.source_condition_potable));

    private final String value;

    SourceCondition(String value) {
        this.value = value;
    }

    public static SourceCondition stringOf(String waterConditionString) {
        try {
            return valueOf(waterConditionString);
        } catch (IllegalArgumentException e) {
            return SourceCondition.values()[0];
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
