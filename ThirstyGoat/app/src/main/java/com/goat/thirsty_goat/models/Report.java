package com.goat.thirsty_goat.models;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
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

    // todo: add name, date and time, report number
    public Report(String name, WaterType type, WaterCondition condition, Location location) {
        mName = name;
        //mDescription = desc;
        mLocation = location;
        mID = Next_ID++;
        mWaterType = type;
        mWaterCondition = condition;
        //TODO: add time
        mCalendar = Calendar.getInstance();
        Log.d("report", mCalendar.toString());
        mName = name;
    }

//    @ Override
//    public String toString() {
//        return mName + "\n" + mDescription;
//    }

    public String getName() {
        return mName;
    }

    public int getReportNumber() {
        return mID;
    }

    public WaterType getWaterType() {
        return mWaterType;
    }

    public String getWaterTypeString() {
        return  mWaterType.toString();
    }

    public WaterCondition getWaterCondition() {
        return mWaterCondition;
    }

    public String getWaterConditionString() {
        return mWaterCondition.toString();
    }


//    public String getDescription() {
//        return mDescription;
//    }

    public double getLatitude() {
        return mLocation.getLatitude();
    }

    public double getLongitude() {
        return mLocation.getLongitude();
    }

    public String getDateString() {
        return "" + mCalendar.get(Calendar.MONTH) + "/" + mCalendar.get(Calendar.DAY_OF_MONTH)
                + "/" + mCalendar.get(Calendar.YEAR);
    }

}
