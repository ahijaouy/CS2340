package com.goat.thirsty_goat.models;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * This class represents a single report submitted by a user, and it holds all data relevant to
 * a report's operation and usefulness.
 *
 * Created by Walker on 2/26/17.
 */
public class Report {
    private static int Next_ID = 0;
    private int mID;
    private String mName;
    private String mDescription;
    private Location mLocation;
    private WaterType mWaterType;
    private WaterCondition mWaterCondition;
    private Calendar mCalendar;

//    public Report(String name, String desc, Location location) {
//        mName = name;
//        mDescription = desc;
//        mLocation = location;
//        mID = Next_ID++;
//    }

    public Report(WaterType type, WaterCondition condition, Location location, String name) {
        mName = "Johnny Appleseed";
        //mDescription = desc;
        mLocation = location;
        mID = Next_ID++;
        mWaterType = type;
        mWaterCondition = condition;
        mCalendar = Calendar.getInstance();
        Log.d("report", mCalendar.toString());
        mName = name;
    }

//    @ Override
//    public String toString() {
//        return mName + "\n" + mDescription;
//    }


    /**
     * Gets the name of the person who submitted this report.
     * @return the report submitter's name
     */
    public String getName() {
        return mName;
    }

    /**
     * Gets the unique ID number of this report.
     * @return this report's unique ID number
     */
    public int getReportNumber() {
        return mID;
    }

    /**
     * Gets the water type of this report.
     * @return the water type of this report
     */
    public WaterType getWaterType() {
        return mWaterType;
    }

    /**
     * Gets the string representation of the water type of this report.
     * @return the string representation of the water type of this report.
     */
    public String getWaterTypeString() {
        return  mWaterType.toString();
    }

    /**
     * Gets the water condition of this report.
     * @return the water condition of this report
     */
    public WaterCondition getWaterCondition() {
        return mWaterCondition;
    }

    /**
     * Gets the string representation of the water condition of this report.
     * @return the string representation of the warer condition of this report.
     */
    public String getWaterConditionString() {
        return mWaterCondition.toString();
    }


//    public String getDescription() {
//        return mDescription;
//    }

    /**
     * Gets the latitude of this report.
     * @return the latitude of this report
     */
    public double getLatitude() {
        return mLocation.getLatitude();
    }

    /**
     * Gets the longitude of this report.
     * @return the longitude of this report.
     */
    public double getLongitude() {
        return mLocation.getLongitude();
    }

    /**
     * Gets the string representation of the date of this report.
     * @return the string representation of the date of this report.
     */
    public String getDateString() {
        return "" + mCalendar.get(Calendar.MONTH) + "/" + mCalendar.get(Calendar.DAY_OF_MONTH)
                + "/" + mCalendar.get(Calendar.YEAR);
    }

}
