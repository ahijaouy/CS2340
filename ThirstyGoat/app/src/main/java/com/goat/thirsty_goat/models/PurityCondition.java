package com.goat.thirsty_goat.models;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.controllers.App;

/**
 * Created by Walker on 3/11/17.
 */

public enum PurityCondition {
    SAFE(App.getResString(R.string.purity_condition_safe)),
    TREATABLE(App.getResString(R.string.purity_condition_treatable)),
    UNSAFE(App.getResString(R.string.purity_condition_unsafe));

    private final String purityCondition;

    PurityCondition(String purityCondition) {
        this.purityCondition = purityCondition;
    }

    public static PurityCondition stringOf(String conditionString) {
        try {
            return valueOf(conditionString);
        } catch (IllegalArgumentException e) {
            return PurityCondition.values()[0];
        }
    }

    @Override
    public String toString() {
        return purityCondition;
    }
}
