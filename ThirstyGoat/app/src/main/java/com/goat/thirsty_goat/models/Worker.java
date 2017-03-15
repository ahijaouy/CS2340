package com.goat.thirsty_goat.models;

import android.util.Log;

/**
 * Created by Walker on 3/11/17.
 */

public class Worker extends UserRole {
    private static final String TAG = Worker.class.getSimpleName();

    public void addWaterSourceReport(WaterType type, WaterSourceCondition condition, Location loc, String name) {
        mReportManager.setSourceReport(type, condition, loc, name);
        Log.d(TAG, "Worker addReport");
    }
}
