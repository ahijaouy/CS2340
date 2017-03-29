package com.goat.thirsty_goat.models.users;

import android.util.Log;

import com.goat.thirsty_goat.models.*;

/**
 * Created by Walker on 3/11/17.
 */

public class Worker extends UserRole {
    private static final String TAG = Worker.class.getSimpleName();

    public void addSourceReport(SourceType type, SourceCondition condition, Location loc, String name) {
        mReportManager.setSourceReport(type, condition, loc, name);
        Log.d(TAG, "Worker addReport");
    }

    @Override
    public void addPurityReport(PurityCondition condition, String name, double virusPPM, double contaminantPPM, Location loc) {
        mReportManager.getReports().get(loc).addPurityReport(condition, virusPPM, contaminantPPM, name);
        Log.d(TAG, "Worker addPurityReport");
    }
}
