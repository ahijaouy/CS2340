package com.goat.thirsty_goat.models;

import android.util.Log;

import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.result.Credentials;

import java.util.Map;

/**
 * This class acts as a facade between the models and controllers/views, and it provides methods
 * for accessing different model managers and instance data.
 *
 * Created by Walker on 2/26/17.
 */

public class ModelFacade {

    private static final String TAG = ModelFacade.class.getSimpleName();
    private static ModelFacade INSTANCE = new ModelFacade();
    public static ModelFacade getInstance() {
        return INSTANCE;
    }


    private ReportManager mReportManager;
    private User mUser;

    /**
     * Creates a facade.
     */
    private ModelFacade() {
        mReportManager = new ReportManager();
        mUser = User.getInstance();
        Log.d(TAG, "model facade constructor");
    }


    /**
     * Tells the User class to add a report with the given parameters.
     * @param type type of water
     * @param condition condition of the water
     * @param loc location of the water
     */
    public void addWaterSourceReport(WaterType type, WaterSourceCondition condition, Location loc) {
        mUser.addWaterSourceReport(type, condition, loc);
    }

    /**
     * Uses the singleton ReportManager to add all reports in the collection parameter.
     * @param collection collection of reports to be added
     */
    public void addAllReports(Collection<Report> collection) {
//        mReportManager.addAllReports(collection);
    }

//    public void addReportAndMarker(String title, String desc, Location loc, Marker marker) {
//        mReportManager.addReportAndMarker(new Report(title, desc, loc), marker);
//    }

    /**
     * Gets the map of reports from the ReportManager class.
     * @return list of stored reports
     */
    public Map<Location, WaterReport> getReports() { return mReportManager.getReports(); }

    /**
     * Gets the current user's name from the User class.
     * @return current user's name
     */
    public String getUserName() {
        return mUser.getUserName();
    }

    /**
     * Gets the current user's email.
     * @return current user's email
     */
    public String getUserEmail() {
        return mUser.getEmail();
    }

    /**
     * Gets the position in the AccountType enum of the current user's account type.
     * @return the position in the enum of the current user's account type
     */
    public int getUserAccountTypePosition() {
        return mUser.getAccountTypePosition();
    }

    /**
     * Used to update the user profile with new user informatino from Auth0.
     * @param client the new client information that we want to use to update this user's profile
     */
    public void updateUserProfile(AuthenticationAPIClient client) {
        mUser.updateUserProfile(client);
    }

    /**
     * Gets the ID of the current user.
     * @return the current user's ID.
     */
    public String getUserID() {
        return mUser.getCredentials().getIdToken();
    }

    /**
     * Sets the current user's credentials.
     * @param credentials the credentials to give to the current user
     */
    public void setUserCredentials(Credentials credentials) {
        mUser.setCredentials(credentials);
    }

    public void fetchReports() {
        mReportManager.fetchReports();
    }


}
