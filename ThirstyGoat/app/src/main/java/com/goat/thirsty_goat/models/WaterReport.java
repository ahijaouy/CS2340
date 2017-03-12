package com.goat.thirsty_goat.models;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a single report submitted by a user, and it holds all data relevant to
 * a report's operation and usefulness.
 *
 * Created by Walker on 2/26/17.
 */

public class WaterReport {
    private static int Next_ID = 0;

    protected Location mLocation;
    private List<WaterSourceReport> mWaterSourceReports;

    public WaterReport(Location location) {
        mWaterSourceReports = new LinkedList<>();
        mLocation = location;
    }

    public static int getAndIncrementID() {
        return Next_ID++;
    }

    public void addSourceReport(WaterType type, WaterSourceCondition condition, String name) {
        ((LinkedList<WaterSourceReport>) mWaterSourceReports).addFirst(new WaterSourceReport(type, condition, name));
    }

    public WaterSourceReport getCurrentWaterSourceReport() {
        return ((LinkedList<WaterSourceReport>) mWaterSourceReports).getFirst();
    }

    public String getCurrentWaterSourceReportDateString() {
        return getCurrentWaterSourceReport().getDateString();
    }

    public int getCurrentWaterSourceReportNumber() {
        return getCurrentWaterSourceReport().getReportNumber();
    }

    public String getCurrentWaterSourceReportTypeString() {
        return getCurrentWaterSourceReport().getWaterTypeString();
    }

    public String getCurrentWaterSourceReportConditionString() {
        return getCurrentWaterSourceReport().getWaterConditionString();
    }

    public String getCurrentWaterSourceReportName() {
        return getCurrentWaterSourceReport().getName();
    }

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

    public Location getLocation() {
        return mLocation;
    }
}
