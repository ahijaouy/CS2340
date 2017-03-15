package com.goat.thirsty_goat.models;

import android.util.Log;

/**
 * This class represents a manager, which can do everything a worker can do, plus view historical
 * reports and trends of water purity; they can also delete individual reports.
 */
public class Manager extends UserRole {
    private static final String TAG = Manager.class.getSimpleName();

    public void addWaterSourceReport(WaterType type, WaterSourceCondition condition, Location loc, String name) {
        mReportManager.setSourceReport(type, condition, loc, name);
        Log.d(TAG, "Manager addReport");
    }
}
