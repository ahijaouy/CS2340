package com.goat.thirsty_goat.models;


import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.controllers.App;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * A class that manages all reports and functionality by keeping a list of all reports
 * and providing ways to access and add these reports.
 *
 * Created by Walker on 2/26/17.
 */
public class ReportManager {
    private List<Report> mReports;
//    private Map<Marker, Report> mMarkers = new HashMap<>();
    private static final String TAG = ReportManager.class.getSimpleName();
    private static final String BASE_URL = App.getContext().getString(R.string.base_url);

    /**
     * Creates a ReportManager instance.
     */
    public ReportManager() {
        mReports = new ArrayList<>();
//        makeDummyReports();
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
        Log.d(TAG, "Sending report to DB");
        sendReport(report);
        Log.d(TAG, "Report Sent");
        mReports.add(report);
        Log.d(TAG, "Added a water report!");
    }

    /**
     * Adds all reports in the collection of reports param to the list fo reports.
     * @param collection collection of reports to be added
     */
    public void addAllReports(Collection<Report> collection) {
        mReports.addAll(collection);
    }

    /**
     * Clear all the reports.
     */
    public void clearReports() {
        mReports.clear();
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

    /**
     * Fetch all reports from the database.
     */
    public void fetchReports() {
        RequestQueue queue = Volley.newRequestQueue(App.getContext());

        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(BASE_URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
//                clearReports();
                try {
                    addAllReports(getReportsFromJSONArray(response));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.getMessage(), error);
            }
        });
        queue.add(jsonArrayRequest);
    }

    /**
     * Send report to Database through a http POST request.
     * @param report Report instance to be sent
     */
    public void sendReport(final Report report) {
        RequestQueue queue = Volley.newRequestQueue(App.getContext());
        final JSONObject reportJson = report.toJson();

        JsonObjectRequest jsonRequest = new JsonObjectRequest(BASE_URL, reportJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Sent: " + reportJson.toString());
                        Log.d(TAG, "Posted: " + response.toString());
                        try {
                            report.setID(response.getInt("insertId"));
                        } catch (JSONException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage(), error);
                    }
        });
        queue.add(jsonRequest);
    }

    /**
     * Convert JSON array into a list of Report instances.
     * @param jsonArray JSON array to be converted
     * @return List of Reports instances.
     * @throws JSONException
     */
    private List<Report> getReportsFromJSONArray(JSONArray jsonArray) throws JSONException {
        /**
         * "source_report_id" : 2,
         "location" : Michigan,
         "water_type" : Bottled,
         "water_condition" : Potable,
         "date_modified" : 2017-03-04T00:00:00.000Z,
         "user_modified" : Username
         */
        // JSON object names
        final String ID = "source_report_id";
        final String LOCATION = "location";
        final String LAT = "latitude";
        final String LON = "longitude";
        final String WATER_TYPE = "water_type";
        final String WATER_COND = "water_condition";
        final String DATE = "date_modified";
        final String USER = "user_modified";

        // Random testing
        WaterType[] waterTypeValues = WaterType.values();
        WaterCondition[] waterCondValues = WaterCondition.values();
        Random rand = new Random();

        List<Report> reports = new ArrayList<>();
        JSONObject reportJson = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                reportJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
//            WaterType waterType = WaterType.valueOf(reportJson.getString(WATER_TYPE));
//            WaterCondition waterCondition = WaterCondition.valueOf(reportJson.getString(WATER_COND));
            WaterType waterType = waterTypeValues[rand.nextInt(waterTypeValues.length)];
            WaterCondition waterCondition = waterCondValues[rand.nextInt(waterCondValues.length)];
//            double lat = reportJson.getDouble(LAT);
//            double lon = reportJson.getDouble(LON);
            double lat = (rand.nextDouble() - 0.5) * 15 + 33;
            double lon = (rand.nextDouble() - 0.5) * 15 - 85;
            Location location = new Location(lat, lon);
            String name = reportJson.getString(USER);
            int id = reportJson.getInt(ID);
            // TODO: Calendar(int year, int month, int day) add calendar as param to report
            reports.add(new Report(waterType, waterCondition, location, name, id));
        }
        return reports;
    }
}
