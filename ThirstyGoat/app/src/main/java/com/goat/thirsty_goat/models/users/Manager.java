package com.goat.thirsty_goat.models.users;

import android.util.Log;

import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.PurityCondition;
import com.goat.thirsty_goat.models.SourceCondition;
import com.goat.thirsty_goat.models.SourceType;

/**
 * This class represents a manager, which can do everything a worker can do, plus view historical
 * reports and trends of water purity; they can also delete individual reports.
 */
public class Manager extends UserRole {
    private static final String TAG = Manager.class.getSimpleName();

    @Override
    public void addSourceReport(SourceType type, SourceCondition condition, Location loc, String name) {
//        mReportManager.setSourceReport(type, condition, loc, name);
        Log.d(TAG, "Manager addReport");
    }

    @Override
    public void addPurityReport(PurityCondition condition, String name, double virusPPM, double contaminantPPM, Location loc) {

    }
}
