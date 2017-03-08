package com.goat.thirsty_goat.models;

import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.result.Credentials;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Map;

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
    private User mUser;

    private ModelFacade() {
        mReportManager = new ReportManager();
        //mUserManager = new UserManager();
        mUser = User.getInstance();
    }

//    public void addReport(String title, String desc, Location loc) {
//        mReportManager.addReport(new Report(title, desc, loc));
//    }

    public void addReport(String name, WaterType type, WaterCondition condition, Location loc) {
        mReportManager.addReport(new Report(name, type, condition, loc));
    }

//    public void addReportAndMarker(String title, String desc, Location loc, Marker marker) {
//        mReportManager.addReportAndMarker(new Report(title, desc, loc), marker);
//    }

    public List<Report> getReports() { return mReportManager.getReportList(); }

    public Report getLastReport() {
        return mReportManager.getLastReport();
    }

    public Map<Marker, Report> getMarkers() {
        return mReportManager.getMarkers();
    }

    public String getUserName() {
        return mUser.getUserName();
    }

    public String getUserEmail() {
        return mUser.getEmail();
    }

    public int getUserAccountTypePosition() {
        return mUser.getAccountTypePosition();
    }

    public void updateUserProfile(AuthenticationAPIClient client) {
        mUser.updateUserProfile(client);
    }

    public String getUserID() {
        return mUser.getCredentials().getIdToken();
    }
    public void setUserCredentials(Credentials credentials) {
        mUser.setCredentials(credentials);
    }


}
