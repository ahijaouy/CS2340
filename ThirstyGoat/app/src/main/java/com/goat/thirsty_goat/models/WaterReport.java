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
//    private List<WaterSourceReport> mWaterSourceReports;
    private WaterSourceReport mSourceReport;
    private List<WaterPurityReport> mPurityReport;

    public WaterReport(Location location) {
//        mWaterSourceReports = new LinkedList<>();
        mPurityReport = new LinkedList<>();
        mLocation = location;
    }

    public static int getAndIncrementID() {
        return Next_ID++;
    }

    public void setSourceReport(WaterType type, WaterSourceCondition condition, String name) {
        mSourceReport = new WaterSourceReport(type, condition, name);
    }

    public void setSourceReport(WaterSourceReport report) {
        mSourceReport = report;
    }

    public WaterSourceReport getCurrentWaterSourceReport() {
//        return ((LinkedList<WaterSourceReport>) mWaterSourceReports).getFirst();
        return mSourceReport;
    }

    public String getCurrentWaterSourceReportDateString() {
        return getCurrentWaterSourceReport().getDateTimeString();
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

    // #####################################################
    // Methods dealing with the associated WaterPurityReport
    // #####################################################

    /**
     * Tries to add a water purity report to this water source report. Since a source report can only
     * have one purity report, if a purity report already exists, the old report is replaced with this
     * new one.
     * @param condition the water purity condition
     * @param virusPPM the amount of virus in the water in parts per million
     * @param contaminantPPM the amount of contaminants in the water in parts per million
     * @param name the name of the person who submitted this purity report
     */
    public void addWaterPurityReport(WaterPurityCondition condition, double virusPPM, double contaminantPPM, String name) {
        ((LinkedList<WaterPurityReport>) mPurityReport).addFirst(new WaterPurityReport(condition, virusPPM, contaminantPPM, name));
    }

    /**
     * Gets the purity report associated with this water source report.
     * @return the purity report associated with this source report, or null if none exists
     */
    public WaterPurityReport getWaterPurityReport() {
        return ((LinkedList<WaterPurityReport>) mPurityReport).getFirst();
    }

    /**
     * Gets the reporter's name for the purity report associated with this water source report.
     * @return the reporter's name for the purity report associated with this source report, or null if none exists
     */
    public String getWaterPurityReportName() {
//        if (getWaterPurityReport() == null) {
//            return null;
//        } else {
//            return getWaterPurityReport().getName();
//        }
        return getWaterPurityReport().getName();
    }

    /**
     * Gets the report number of the purity report associated with this water source report.
     * @return the report number of the purity report associated with this source report, or -1 if none exists
     */
    public int getWaterPurityReportNumber() {
        if (getWaterPurityReport() == null) {
            return -1;
        } else {
            return getWaterPurityReport().getReportNumber();
        }
    }

    /**
     * Gets the purity condition from the purity report associated with this water source report.
     * @return the purity condition from the purity report associated with this source report, or null if none exists
     */
    public String getWaterPurityReportConditionString() {
//        if (getWaterPurityReport() == null) {
//            return null;
//        } else {
//            return getWaterPurityReport().getWaterConditionString();
//        }
        return getWaterPurityReport().getWaterConditionString();
    }

    /**
     * Gets the date from the purity report associated with this water source report.
     * @return the date from the purity report associated with this source report, or null if none exists
     */
    public String getWaterPurityReportDateString() {
//        if (getWaterPurityReport() == null) {
//            return null;
//        } else {
//            return getWaterPurityReport().getDateString();
//        }
        return getWaterPurityReport().getDateString();
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
