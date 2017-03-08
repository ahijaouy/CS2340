package com.goat.thirsty_goat.models;

import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.result.Credentials;
import com.google.android.gms.maps.model.Marker;

import java.util.List;
import java.util.Map;

/**
 * This class acts as a facade between the models and controllers/views, and it provides methods
 * for accessing different model managers and instance data.
 *
 * Created by Walker on 2/26/17.
 */

public class ModelFacade {
    private static ModelFacade INSTANCE = new ModelFacade();
    public static ModelFacade getInstance() {
        return INSTANCE;
    }

    //private User mCurrentUser;

    private ReportManager mReportManager;
    //private UserManager mUserManager;
    private User mUser;

    private ModelFacade() {
        mReportManager = new ReportManager();
        //mCurrentUser = User.getInstance();
        //mUserManager = new UserManager();
        mUser = User.getInstance();
    }

//    public void addReport(String title, String desc, Location loc) {
//        mReportManager.addReport(new Report(title, desc, loc));
//    }

    /**
     * Tells the ReportManager class to add a report with the given parameters to its list of reports.
     * @param type type of water
     * @param condition condition of the water
     * @param loc location of the water
     */
    public void addReport(WaterType type, WaterCondition condition, Location loc, String name) {
        mReportManager.addReport(new Report(type, condition, loc, name));
    }

//    public void addReportAndMarker(String title, String desc, Location loc, Marker marker) {
//        mReportManager.addReportAndMarker(new Report(title, desc, loc), marker);
//    }

    /**
     * Gets the list of reports from the ReportManager class.
     * @return list of stored reports
     */
    public List<Report> getReports() { return mReportManager.getReportList(); }

    /**
     * Gets the most recently submitted report from the ReportManager class.
     * @return most recently submitted report
     */
    public Report getLastReport() {
        return mReportManager.getLastReport();
    }

//    public Map<Marker, Report> getMarkers() {
//        return mReportManager.getMarkers();
//    }

    /**
     * Gets the current user's name from the User class.
     * @return current user's name
     */
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
