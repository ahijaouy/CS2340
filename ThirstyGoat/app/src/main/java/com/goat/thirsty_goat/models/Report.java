package com.goat.thirsty_goat.models;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a single report submitted by a user, and it holds all data relevant to
 * a report's operation and usefulness.
 *
 * Created by Walker on 2/26/17.
 */

public class Report {
    private static int Next_ID = 0;

    protected Location mLocation;
//    private List<SourceReport> mWaterSourceReports;
    private SourceReport mSourceReport;
    private List<PurityReport> mPurityReport;

    public Report(Location location) {
//        mWaterSourceReports = new LinkedList<>();
        mPurityReport = new LinkedList<>();
        mLocation = location;
    }

    public static int getAndIncrementID() {
        return Next_ID++;
    }

    public boolean hasSourceReport() {
        return mSourceReport != null;
    }

    public void setSourceReport(SourceType type, SourceCondition condition, String name) {
        mSourceReport = new SourceReport(type, condition, name);
    }

    public void setSourceReport(SourceReport report) {
        mSourceReport = report;
    }

    public SourceReport getSourceReport() {
//        return ((LinkedList<SourceReport>) mWaterSourceReports).getFirst();
        return mSourceReport;
    }

    public String getCurrentSourceReportDateString() {
        return getSourceReport().getDateTimeString();
    }

    public int getCurrentSourceReportNumber() {
        return getSourceReport().getReportNumber();
    }

    public String getCurrentSourceReportTypeString() {
        return getSourceReport().getSourceTypeString();
    }

    public String getCurrentSourceReportConditionString() {
        return getSourceReport().getConditionString();
    }

    public String getCurrentSourceReportName() {
        return getSourceReport().getName();
    }

    // #####################################################
    // Methods dealing with the associated PurityReport
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
    public void addPurityReport(PurityCondition condition, double virusPPM, double contaminantPPM, String name) {
        ((LinkedList<PurityReport>) mPurityReport).addFirst(new PurityReport(condition, virusPPM, contaminantPPM, name));
    }

    public void addPurityReport(PurityReport report) {
        ((LinkedList<PurityReport>) mPurityReport).addFirst(report);
    }

    /**
     * Gets the purity report associated with this water source report.
     * @return the purity report associated with this source report, or null if none exists
     */
    public PurityReport getPurityReport() {
        return ((LinkedList<PurityReport>) mPurityReport).getFirst();
    }

    /**
     * Gets the reporter's name for the purity report associated with this water source report.
     * @return the reporter's name for the purity report associated with this source report, or null if none exists
     */
    public String getPurityReportName() {
//        if (getPurityReport() == null) {
//            return null;
//        } else {
//            return getPurityReport().getName();
//        }
        return getPurityReport().getName();
    }

    /**
     * Gets the report number of the purity report associated with this water source report.
     * @return the report number of the purity report associated with this source report, or -1 if none exists
     */
    public int getPurityReportNumber() {
        if (getPurityReport() == null) {
            return -1;
        } else {
            return getPurityReport().getReportNumber();
        }
    }

    /**
     * Gets the purity condition from the purity report associated with this water source report.
     * @return the purity condition from the purity report associated with this source report, or null if none exists
     */
    public String getPurityReportConditionString() {
//        if (getPurityReport() == null) {
//            return null;
//        } else {
//            return getPurityReport().getConditionString();
//        }
        return getPurityReport().getConditionString();
    }

    /**
     * Gets the date from the purity report associated with this water source report.
     * @return the date from the purity report associated with this source report, or null if none exists
     */
    public String getPurityReportDateString() {
//        if (getPurityReport() == null) {
//            return null;
//        } else {
//            return getPurityReport().getDateString();
//        }
        return getPurityReport().getDateString();
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
