package com.goat.thirsty_goat.models;

import android.util.Log;

/**
 * Created by Walker on 3/11/17.
 */

public class BasicUser extends UserRole {
    private static final String TAG = BasicUser.class.getSimpleName();

    public void addWaterSourceReport(WaterType type, WaterSourceCondition condition, Location loc, String name) {
        mReportManager.addWaterSourceReport(type, condition, loc, name);
        Log.d(TAG, "BasicUser addReport");
    }
}
