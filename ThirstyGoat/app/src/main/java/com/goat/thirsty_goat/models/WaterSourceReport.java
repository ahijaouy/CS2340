package com.goat.thirsty_goat.models;

import android.util.Log;

import org.joda.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class represents a single report submitted by a user, and it holds all data relevant to
 * a report's operation and usefulness.
 *
 * Created by Walker on 2/26/17.
 */

public class WaterSourceReport {
    private int mID;
    private String mName;
    private WaterType mWaterType;
    private WaterSourceCondition mWaterCondition;
    private Calendar mCalendar;
    private LocalDateTime mDateTime;

    private static final String TAG = WaterSourceReport.class.getSimpleName();


    /**
     * Creates new Report object.
     *
     * @param type type of water
     * @param cond condition of water
     * @param loc  location of source
     * @param name name of user creating report
     */
    public WaterSourceReport(WaterType type, WaterCondition cond, Location loc, String name) {
        mLocation = loc;
        mWaterType = type;
        mWaterCondition = cond;
        mDateTime = LocalDateTime.now();
        Log.d(TAG, "DateTime: " + getDateTimeString());
        mName = name;
    }

    /**
     * Creates new Report object with Calendar param.
     *
     * @param type type of water
     * @param cond condition fo water
     * @param loc  location of source
     * @param name name of user creating report
     * @param id id of the user
     */
    public WwaterSourceReport(WaterType type, WaterCondition cond, Location loc, String name, int id) {
        this(type, cond, loc, name);
        mID = id;
    }

    // ##########################################################
    // Methods dealing with getters/setters for WaterSourceReport
    // ##########################################################

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
        return mWaterType.toString();
    }

    /**
     * Gets the water condition of this report.
     * @return the water condition of this report
     */
    public WaterSourceCondition getWaterCondition() {
        return mWaterCondition;
    }

    /**
     * Gets the string representation of the water condition of this report.
     *
     * @return the string representation of the water condition of this report.
     */
    public String getWaterConditionString() {
        return mWaterCondition.toString();
    }

    /**
     * Gets the string representation of the date of this report in ISO 8601 format.
     *
     * @return string representation of the date of this report
     */
    public String getDateTimeString() {
        return mDateTime.toString();
    }

    /**
     * Converts the Report instance to a JSON object.
     * @return JSON object
     */
    public JSONObject toJson() {
        JSONObject json = null;
        Log.d(TAG, mLocation.toString());
        try {
            json = new JSONObject()
                    .put("location", mLocation)
                    .put("water_type", mWaterType.toString())
                    .put("water_condition", mWaterCondition.toString())
                    .put("date_modified", mDateTime.toString())
                    .put("user_modified", mName);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return json;
    }
    public void setID(int ID) {
        mID = ID;
    }
}