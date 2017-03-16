package com.goat.thirsty_goat.models;

import java.util.Calendar;

/**
 * Created by Walker on 3/11/17.
 */

public class WaterPurityReport {
    private int mID;
    private String mName;
    private WaterPurityCondition mWaterCondition;
    private Calendar mCalendar;
    private double mVirusPPM;
    private double mContaminantPPM;

    public WaterPurityReport(WaterPurityCondition condition, double virusPPM, double contaminantPPM, String name) {
        mCalendar = Calendar.getInstance();
        mID = WaterReport.getAndIncrementID();
        mName = name;
        mWaterCondition = condition;
        mVirusPPM = virusPPM;
        mContaminantPPM = contaminantPPM;
    }

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
     * Gets the water condition of this report.
     * @return the water condition of this report
     */
    public WaterPurityCondition getWaterCondition() {
        return mWaterCondition;
    }

    /**
     * Gets the string representation of the water condition of this report.
     * @return the string representation of the warer condition of this report.
     */
    public String getWaterConditionString() {
        return mWaterCondition.toString();
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
