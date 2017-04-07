package com.goat.thirsty_goat.models;

import android.util.Log;

import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.result.Credentials;
import com.jjoe64.graphview.GraphView;

import java.util.List;
import java.util.Map;

/**
 * This class acts as a facade between the models and controllers/views, and it provides methods
 * for accessing different model managers and instance data.
 *
 * Created by Walker on 2/26/17.
 */

public class ModelFacade {

    private static final String TAG = ModelFacade.class.getSimpleName();
    private static final ModelFacade INSTANCE = new ModelFacade();

    public static ModelFacade getInstance() {
        return INSTANCE;
    }


    private final ReportManager mReportManager;
    private final UserManager mUserManager;
    private final HistoricalReportManager mHistoricalReportManager;

    /**
     * Creates a facade.
     */
    private ModelFacade() {
        mReportManager = ReportManager.getInstance();
        mUserManager = UserManager.getInstance();
        mHistoricalReportManager = HistoricalReportManager.getInstance();
        Log.d(TAG, "model facade constructor");
    }


    /**
     * Tells the UserManager class to add a report with the given parameters.
     *
     * @param type      type of water
     * @param condition condition of the water
     * @param loc       location of the water
     */
    public void addSourceReport(SourceType type, SourceCondition condition, Location loc) {
        mUserManager.addSourceReport(type, condition, loc);
    }

    /**
     * Tells the User class to add a water purity report with the given parameters.
     *
     * @param condition      condition of the water
     * @param virusPPM       virus concentration
     * @param contaminantPPM contaminant concentration
     * @param loc            location of the water
     */
    public void addPurityReport(PurityCondition condition, double virusPPM, double contaminantPPM, Location loc) {
        mUserManager.addPurityReport(condition, virusPPM, contaminantPPM, loc);
    }

    /**
     * Gets the map of reports from the ReportManager class.
     *
     * @return list of stored reports
     */
    public Map<Location, Report> getReports() {
        return mReportManager.getReports();
    }

    /**
     * Gets the current user's name from the UserManager class.
     *
     * @return current user's name
     */
    public String getUserName() {
        return mUserManager.getUserName();
    }

    public String getAccountType() {
        return mUserManager.getAccountType().toString();
    }

    /**
     * Gets the current user's email.
     *
     * @return current user's email
     */
    public String getUserEmail() {
        return mUserManager.getEmail();
    }

    /**
     * Gets the position in the AccountType enum of the current user's account type.
     *
     * @return the position in the enum of the current user's account type
     */
    public int getUserAccountTypePosition() {
        return mUserManager.getAccountTypePosition();
    }

    /**
     * Used to update the user profile with new user information from Auth0.
     *
     * @param client the new client information that we want to use to update this user's profile
     */
    public void updateUserProfile(AuthenticationAPIClient client) {
        mUserManager.updateUserProfile(client);
    }

    /**
     * Gets the ID of the current user.
     *
     * @return the current user's ID.
     */
    public String getUserID() {
        return mUserManager.getCredentials().getIdToken();
    }

    /**
     * Sets the current user's credentials.
     *
     * @param credentials the credentials to give to the current user
     */
    public void setUserCredentials(Credentials credentials) {
        mUserManager.setCredentials(credentials);
    }

    public void fetchReports() {
        mReportManager.fetchSourceReports();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mReportManager.fetchPurityReports();
    }

    public List<PurityReport> getPurityReportsForLocation(double latitude, double longitude) {
        Log.d("graph", "lat: " + latitude + "  lng: " + longitude);
        return mReportManager.getPurityReportsForLocation(new Location(latitude, longitude));
    }

    public GraphView createGraph(double latitude, double longitude, int year, boolean isVirus, GraphView graph) {
        Log.d("graph", String.format("lat: %s   lng: %s   year: %d  isVirus: %b", latitude, longitude, year, isVirus));
        return mHistoricalReportManager.createGraph(latitude, longitude, year, isVirus, graph);
    }


}
