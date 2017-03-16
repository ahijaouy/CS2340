package com.goat.thirsty_goat.models.users;

import android.util.Log;

import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.SourceCondition;
import com.goat.thirsty_goat.models.SourceType;

/**
 * Created by Walker on 3/11/17.
 */

public class Worker extends UserRole {
    private static final String TAG = Worker.class.getSimpleName();

    public void addWaterSourceReport(SourceType type, SourceCondition condition, Location loc, String name) {
        mReportManager.setSourceReport(type, condition, loc, name);
        Log.d(TAG, "Worker addReport");
    }
}
