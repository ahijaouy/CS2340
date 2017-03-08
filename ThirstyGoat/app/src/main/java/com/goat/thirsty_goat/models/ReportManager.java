package com.goat.thirsty_goat.models;


import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A class that manages all reports and functionality by keeping a list of all reports
 * and providing ways to access and add these reports.
 *
 * Created by Walker on 2/26/17.
 */
public class ReportManager {
    private List<Report> mReports;
//    private Map<Marker, Report> mMarkers = new HashMap<>();

    /**
     * Creates a ReportManager instance.
     */
    public ReportManager() {
        mReports = new ArrayList<>();
        makeDummyReports();
    }

    /**
     * Generates dummy reports for populating the map with preexisting reports.
     */
    private void makeDummyReports() {
        addReport(new Report(WaterType.BOTTLED, WaterCondition.POTABLE, new Location(33.749, -84.388), "Bob"));
        addReport(new Report(WaterType.LAKE, WaterCondition.WASTE, new Location(33.8, -84.5), "Sally"));
    }

    /**
     * Gets the list of stored reports.
     * @return the stored list of reports
     */
    public List<Report> getReportList() {
        return mReports;
    }

    /**
     * Adds a given report to the list of reports.
     * @param report the report to add
     */
    public void addReport(Report report) {
        mReports.add(report);
        Log.d("Report", "Added a water report!");
    }

    /**
     * Adds all reports in the collection param to the list fo reports.
     * @param collection collection of reports to be added
     */
    public void addAllReports(Collection<Report> collection) {
        mReports.addAll(collection);
    }

//    public void addReportAndMarker(Report report, Marker marker) {
//        addReport(report);
//        mMarkers.put(marker, report);
//    }

//    public Map<Marker, Report> getMarkers() {
//        return mMarkers;
//    }

    /**
     * Gets the last submitted report as a String from the list of reports.
     * @return the last submitted report as a String
     */
    public String getLastReportString() {
        return mReports.get(mReports.size() - 1).toString();
    }

    /**
     * Gets the last submitted report as a String from the list of reports.
     * @return the last submitted report as a String
     */
    public Report getLastReport() {
        return mReports.get(mReports.size() - 1);
    }
}
