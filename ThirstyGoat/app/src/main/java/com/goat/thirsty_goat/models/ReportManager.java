package com.goat.thirsty_goat.models;


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

    private Map<Location, WaterReport> mWaterReportsMap;

    public ReportManager() {
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
        addWaterSourceReport(WaterType.BOTTLED, WaterSourceCondition.POTABLE, new Location(33.749, -84.388), "Bob");
        addWaterSourceReport(WaterType.LAKE, WaterSourceCondition.WASTE, new Location(50.8, -70.5), "Sally");
    }

    public void addWaterSourceReport(WaterType type, WaterSourceCondition condition, Location location, String name) {
        if (mWaterReportsMap.get(location) == null) {
            mWaterReportsMap.put(location, new WaterReport(location));
        }
        WaterReport waterReport = mWaterReportsMap.get(location);
        waterReport.addSourceReport(type, condition, name);
    }

    // getters and setters
    /**
     * Gets the list of stored reports.
     * @return the stored list of reports
     */
    public Map<Location, WaterReport> getReports() {
        return mWaterReportsMap;
    }
}
