package com.goat.thirsty_goat.models;

/**
 * Created by Walker on 3/11/17.
 */

public abstract class UserRole {
    protected static ReportManager mReportManager = ReportManager.getInstance();



    public abstract void addWaterSourceReport(WaterType type, WaterCondition condition, Location loc, String name);
}
