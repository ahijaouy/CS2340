package com.goat.thirsty_goat.models.users;

import android.util.Log;

import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.PurityCondition;
import com.goat.thirsty_goat.models.SourceCondition;
import com.goat.thirsty_goat.models.SourceType;

/**
 * Created by Walker on 3/11/17.
 */

public class BasicUser extends UserRole {
    private static final String TAG = BasicUser.class.getSimpleName();

    @Override
    public void addSourceReport(SourceType type, SourceCondition condition, Location loc, String name) {
        mReportManager.setSourceReport(type, condition, loc, name);
        Log.d(TAG, "BasicUser addReport");
    }

    @Override
    public void addPurityReport(PurityCondition condition, String name, double virusPPM, double contaminantPPM, Location loc) {
    }
}
