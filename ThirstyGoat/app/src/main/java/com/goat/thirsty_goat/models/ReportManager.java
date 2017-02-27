package com.goat.thirsty_goat.models;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Walker on 2/26/17.
 */

public class ReportManager {
    private List<Report> mReports;

    public ReportManager() {
        mReports = new ArrayList<>();
        makeDummyReports();
    }

    private void makeDummyReports() {
        addReport(new Report("Water fountain", "College of Computing", new Location(33.749, -84.388)));
        addReport(new Report("Vending machine", "Klaus", new Location(33.8, -84.5)));
    }

    public List<Report> getReportList() {
        return mReports;
    }

    public void addReport(Report report) {
        mReports.add(report);
    }

    public String getLastReportString() {
        return mReports.get(mReports.size() - 1).toString();
    }

    public Report getLastReport() {
        return mReports.get(mReports.size() - 1);
    }
}
