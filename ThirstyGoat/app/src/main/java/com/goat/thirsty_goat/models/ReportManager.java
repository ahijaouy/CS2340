package com.goat.thirsty_goat.models;


import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A class that manages all reports and functionality by keeping a list of all reports
 * and providing ways to access and add these reports.
 *
 * Created by Walker on 2/26/17.
 */
public class ReportManager {
    private static ReportManager INSTANCE = new ReportManager();

    private List<WaterReport> mWaterReports;
    private Map<Location, WaterReport> mWaterReportsMap;
//    private Map<Marker, WaterReport> mMarkers = new HashMap<>();

    public ReportManager() {
        mWaterReports = new ArrayList<>();
        mWaterReportsMap = new HashMap<>();
        makeDummyReports();
    }

    public static ReportManager getInstance() {
        return INSTANCE;
    }

    /**
     * Generates dummy reports for populating the map with preexisting reports.
     */
    private void makeDummyReports() {
        addWaterSourceReport(WaterType.BOTTLED, WaterCondition.POTABLE, new Location(33.749, -84.388), "Bob");
        addWaterSourceReport(WaterType.LAKE, WaterCondition.WASTE, new Location(50.8, -70.5), "Sally");
    }

    public void addWaterSourceReport(WaterType type, WaterCondition condition, Location location, String name) {
        if (mWaterReportsMap.get(location) == null) {
            mWaterReportsMap.put(location, new WaterReport(location));
        }
        WaterReport waterReport = mWaterReportsMap.get(location);
        waterReport.addSourceReport(type, condition, name);

    }




//    /**
//     * Adds a given waterReport to the list of reports.
//     * @param waterReport the waterReport to add
//     */
//    public void addReport(WaterReport waterReport) {
//        mWaterReports.add(waterReport);
//        Log.d("waterReport", "Added a water waterReport!");
//    }



    // getters and setters
    /**
     * Gets the list of stored reports.
     * @return the stored list of reports
     */
    public Map<Location, WaterReport> getReports() {
        return mWaterReportsMap;
    }

    /**
     * Gets the last submitted report as a String from the list of reports.
     * @return the last submitted report as a String
     */
    public String getLastReportString() {
        return mWaterReports.get(mWaterReports.size() - 1).toString();
    }

    /**
     * Gets the last submitted report as a String from the list of reports.
     * @return the last submitted report as a String
     */
    public WaterReport getLastReport() {
        return mWaterReports.get(mWaterReports.size() - 1);
    }
}
