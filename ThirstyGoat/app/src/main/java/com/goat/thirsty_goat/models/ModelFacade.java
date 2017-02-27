package com.goat.thirsty_goat.models;

import java.util.List;

/**
 * Created by Walker on 2/26/17.
 */

public class ModelFacade {
    private static ModelFacade INSTANCE = new ModelFacade();
    public static ModelFacade getInstance() {
        return INSTANCE;
    }

    private ReportManager mReportManager;
    //private UserManager mUserManager;

    private ModelFacade() {
        mReportManager = new ReportManager();
        //mUserManager = new UserManager();
    }

    public void addReport(String title, String desc, Location loc) {
        mReportManager.addReport(new Report(title, desc, loc));
    }

    public List<Report> getReports() { return mReportManager.getReportList(); }

    public Report getLastReport() {
        return mReportManager.getLastReport();
    }
}
