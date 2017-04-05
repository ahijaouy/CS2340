package com.goat.thirsty_goat.models.users;

import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.PurityCondition;
import com.goat.thirsty_goat.models.ReportManager;
import com.goat.thirsty_goat.models.SourceCondition;
import com.goat.thirsty_goat.models.SourceType;

/**
 * Created by Walker on 3/11/17.
 */

public abstract class UserRole {
    static ReportManager mReportManager = ReportManager.getInstance();

    /*
      NOTE: any method you want a child class to have, add it to this class as an abstract method.
      Then, you'll have to implement that method in every child class of this (ie BasicUser, Worker,
       Manager, and Admin). Even if one of them can't do that method, implement it in a way that makes
       sense, and then eventually we'll edit the UI so that they can never even reach that method to
       begin with. See what I did with the addSourceReport method in all the classes (ie how I
       left a blank/wasted implementation in Admin).
    */

    public abstract void addSourceReport(SourceType type, SourceCondition condition, Location loc, String name);

    public abstract void addPurityReport(PurityCondition condition, String name, double virusPPM, double contaminantPPM, Location loc);
}
