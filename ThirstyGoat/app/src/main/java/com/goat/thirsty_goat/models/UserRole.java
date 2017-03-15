package com.goat.thirsty_goat.models;

/**
 * Created by Walker on 3/11/17.
 */

public abstract class UserRole {
    protected static ReportManager mReportManager = ReportManager.getInstance();

    /*
      NOTE: any method you want a child class to have, add it to this class as an abstract method.
      Then, you'll have to implement that method in every child class of this (ie BasicUser, Worker,
      Manager, and Admin). Even if one of them can't do that method, implement it in a way that makes
      sense, and then eventually we'll edit the UI so that they can never even reach that method to
      begin with. See what I did with the setSourceReport method in all the classes (ie how I
      left a blank/wasted implementation in Admin).
    */

    public abstract void addWaterSourceReport(WaterType type, WaterSourceCondition condition, Location loc, String name);
}
