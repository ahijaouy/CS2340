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

public class SourceReport implements Reportable {

    private static final String TAG = SourceReport.class.getSimpleName();

    private int mID;
    private String mName;
    private SourceType mType;
    private SourceCondition mCondition;
    private LocalDateTime mDateTime;

    /**
     * Creates new Report object.
     *
     * @param type type of water
     * @param cond condition of water
     * @param name name of user creating report
     */
    public SourceReport(SourceType type, SourceCondition cond, String name) {
        mType = type;
        mCondition = cond;
        mDateTime = LocalDateTime.now();
        mName = name;
    }

    /**
     * Creates new Report object with ID param.
     *
     * @param type type of water
     * @param cond condition fo water
     * @param name name of user creating report
     * @param id id of the user
     * @param dateTime date/time of report when created
     */
    public SourceReport(SourceType type, SourceCondition cond, String name, int id, LocalDateTime dateTime) {
        this(type, cond, name);
        mID = id;
        mDateTime = dateTime;
    }

    // ##########################################################
    // Methods dealing with getters/setters for SourceReport
    // ##########################################################


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
    public SourceType getSourceType() {
        return mType;
    }

    /**
     * Gets the string representation of the water type of this report.
     * @return the string representation of the water type of this report.
     */
    public String getSourceTypeString() {
        return mType.toString();
    }

    /**
     * Gets the water condition of this report.
     * @return the water condition of this report
     */
    public SourceCondition getCondition() {
        return mCondition;
    }

    /**
     * Gets the string representation of the water condition of this report.
     *
     * @return the string representation of the water condition of this report.
     */
    public String getConditionString() {
        return mCondition.toString();
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
        try {
            json = new JSONObject()
                    .put("water_type", mType.name())
                    .put("water_condition", mCondition.name())
                    .put("date_modified", mDateTime.toString()) //TODO check if + 'Z' is needed
                    .put("user_modified", mName);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return json;
    }

    public void setID(int id) {
        mID = id;
    }
}
