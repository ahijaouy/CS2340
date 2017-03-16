package com.goat.thirsty_goat.models;

import android.util.Log;

import org.joda.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Walker on 3/11/17.
 */

public class PurityReport {

    private static final String TAG = PurityReport.class.getSimpleName();

    private int mID;
    private String mName;
    private PurityCondition mCondition;
    private LocalDateTime mDateTime;
    private double mVirusPPM;
    private double mContaminantPPM;

    public PurityReport(PurityCondition condition, double virusPPM, double contaminantPPM, String name) {
        mID = Report.getAndIncrementID();
        mName = name;
        mCondition = condition;
        mDateTime = LocalDateTime.now();
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
    public PurityCondition getWaterCondition() {
        return mCondition;
    }

    /**
     * Gets the string representation of the water condition of this report.
     * @return the string representation of the warer condition of this report.
     */
    public String getWaterConditionString() {
        return mCondition.toString();
    }

    /**
     * Gets the string representation of the date of this report.
     * @return the string representation of the date of this report.
     */
    public String getDateString() {
        return mDateTime.toString();
    }

    public JSONObject toJson() {
        JSONObject json = null;
        try {
            json = new JSONObject()
                    .put("overall_condition", mCondition.name())
                    .put("virus_ppm", mVirusPPM)
                    .put("contaminant_ppm", mContaminantPPM)
                    .put("date_modified", mDateTime.toString())
                    .put("user_modified", mName);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return json;
    }
}
