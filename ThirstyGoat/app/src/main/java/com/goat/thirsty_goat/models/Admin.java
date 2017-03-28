package com.goat.thirsty_goat.models;

import android.util.Log;

/**
 * Created by Walker on 3/11/17.
 */

public class Admin extends UserRole {
    private static final String TAG = Admin.class.getSimpleName();

    public void addWaterSourceReport(WaterType type, WaterSourceCondition condition, Location loc, String name) {
        /*
          NOTE: an admin shouldn't be able to add a water source report.
          Even though they can't add them, they still need this method for the polymorphic aspect to work.

          The most basic (and worst) solution is to just leave this method blank, so even if they try to submit
          a report, it won't do anything.

          A better approach would be to modify the UI so that they can never see or interact with anything
          that would allow this method to be reached.
        */
    }

    @Override
    public void addWaterPurityReport(WaterPurityCondition condition, String name, double virusPPM, double contaminantPPM, Location loc) {

    }
}
