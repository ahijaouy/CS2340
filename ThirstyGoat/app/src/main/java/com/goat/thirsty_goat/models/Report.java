package com.goat.thirsty_goat.models;

/**
 * Created by Walker on 2/26/17.
 */

public class Report {
    private static int Next_ID = 0;
    private int mID;
    private String mName;
    private String mDescription;
    private Location mLocation;

    public Report(String name, String desc, Location location) {
        mName = name;
        mDescription = desc;
        mLocation = location;
        mID = Next_ID++;
    }

    @ Override
    public String toString() {
        return mName + "\n" + mDescription;
    }

    public String getName() {
        return mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public double getLatitute() {
        return mLocation.getLatitude();
    }

    public double getLongitude() {
        return mLocation.getLongitude();
    }
}
