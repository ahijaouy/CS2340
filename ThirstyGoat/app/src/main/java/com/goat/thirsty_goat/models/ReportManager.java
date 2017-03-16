package com.goat.thirsty_goat.models;


import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.controllers.App;

import org.joda.time.LocalDateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A class that manages all reports and functionality by keeping a list of all reports
 * and providing ways to access and add these reports.
 *
 * Created by Walker on 2/26/17.
 */
public class ReportManager {

    private static final String TAG = ReportManager.class.getSimpleName();

    private static ReportManager INSTANCE = new ReportManager();
    private Map<Location, Report> mReportsMap;
    private RequestQueue mRequestQueue;

    // URLs for http requests
    private static final String SOURCE_REPORTS_URL = App.getResString(R.string.base_url_source_reports);
    private static final String PURITY_REPORTS_URL = App.getResString(R.string.base_url_purity_reports);



    /**
     * Creates a ReportManager instance.
     */
    public ReportManager() {
        mReportsMap = new HashMap<>();
        mRequestQueue = Volley.newRequestQueue(App.getContext());
//        makeDummyReports();
    }

    public static ReportManager getInstance() {
        return INSTANCE;
    }

    /**
     * Generates dummy reports for populating the map with preexisting reports.
     */
    private void makeDummyReports() {
        setSourceReport(SourceType.BOTTLED, SourceCondition.POTABLE, new Location(33.749, -84.388), "Bob");
        setSourceReport(SourceType.LAKE, SourceCondition.WASTE, new Location(50.8, -70.5), "Sally");
    }

    public void setSourceReport(SourceType type, SourceCondition cond, Location location, String name) {
        Report report = getReport(location);
        if (report.hasSourceReport()) {
            // TODO: delete old sourceReport in DB
        }
        SourceReport sourceReport = new SourceReport(type, cond, name);
        report.setSourceReport(sourceReport);
        sendSourceReport(sourceReport, location);
    }

    private void setOldSourceReport(Location location, SourceReport sourceReport) {
        Report report = getReport(location);
        report.setSourceReport(sourceReport);
    }

    private Report getReport(Location location) {
        if (mReportsMap.get(location) == null) {
            mReportsMap.put(location, new Report(location));
        }
        return mReportsMap.get(location);
    }


    // Getters and setters
    /**
     * Gets the list of stored reports.
     * @return the stored list of reports
     */
    public Map<Location, Report> getReports() {
        return mReportsMap;
    }


    private void clearReports() {
        mReportsMap.clear();
    }

    /**
     * Fetch all source reports from the database.
     */
    public void fetchSourceReports() {
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(SOURCE_REPORTS_URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                clearReports();
                try {
                    addOldSourceReports(response);
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
        mRequestQueue.add(jsonArrayRequest);
    }

    public void fetchPurityReports() {
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(PURITY_REPORTS_URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            addOldPurityReports(response);
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
        mRequestQueue.add(jsonArrayRequest);
    }

    /**
     * Send report to Database through a http POST request.
     * @param sourceReport Report instance to be sent
     */
    public void sendSourceReport(final SourceReport sourceReport, Location location) {
        final JSONObject reportJson = sourceReport.toJson();
        try {
           reportJson.put("location", location);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage(), e);
        }

        JsonObjectRequest jsonRequest = new JsonObjectRequest(SOURCE_REPORTS_URL, reportJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Sent: " + reportJson.toString());
                        Log.d(TAG, "Posted: " + response.toString());
                        try {
                            sourceReport.setID(response.getInt("insertId"));
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
        mRequestQueue.add(jsonRequest);
    }

    /**
     * Add all source reports given by JSON array param.
     * @param jsonArray JSON array to be parsed and added
     * @throws JSONException
     */
    private void addOldSourceReports(JSONArray jsonArray) throws JSONException {

        // JSON object names
        final String SOURCE_ID = "source_report_id";
        final String LAT = "latitude";
        final String LNG = "longitude";
        final String WATER_TYPE = "water_type";
        final String WATER_COND = "water_condition";
        final String DATE = "date_modified";
        final String USER = "user_modified";

        Random rand = new Random();

        JSONObject reportJson = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                reportJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }

            SourceType sourceType = SourceType.stringOf(reportJson.getString(WATER_TYPE));
            SourceCondition condition = SourceCondition.stringOf(reportJson.getString(WATER_COND));
//            double lat = reportJson.getDouble(LAT);
//            double lon = reportJson.getDouble(LNG);
            double lat = (rand.nextDouble() - 0.5) * 30 + 33;
            double lon = (rand.nextDouble() - 0.5) * 30 - 85;
            Location location = new Location(lat, lon);
            String name = reportJson.getString(USER);
            int id = reportJson.getInt(SOURCE_ID);
            LocalDateTime dateTime = LocalDateTime.parse(reportJson.getString(DATE));
            setOldSourceReport(location, new SourceReport(sourceType, condition, name, id, dateTime));
            Log.d(TAG, "Adding source report from DB");
        }
    }

    private void addOldPurityReports(JSONArray jsonArray) throws JSONException {

        // JSON object names
        final String PURITY_ID = "source_id";
        final String CONDITION = "overall_condition";
        final String VIRUS = "virus_ppm";
        final String CONTAMINANT = "contaminant_ppm";
        final String DATE = "date_modified";
        final String USER = "user_modified";

        JSONObject reportJson = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                reportJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage(), e);
            }

            // TODO Implement json parsing adding purity report to report map
        }
    }

    private void deleteSourceReport(int id) {
        String url = PURITY_REPORTS_URL + "/" + id;
    }

    private void deleteReport(Object o) {
        String url;
        int id;
        if (o instanceof SourceReport) {
            id = ((SourceReport) o).getReportNumber();
            url = SOURCE_REPORTS_URL + "/" + id;
        } else if (o instanceof PurityReport) {
            id = ((PurityReport) o).getReportNumber();
            url = PURITY_REPORTS_URL + "/" + id;
        } else {
            Log.d(TAG, "Object passed to deleteReport not a source nor purity Report");
            url = null;
        }

//        switch (o.getClass().getName()) {
//            case SourceReport.class.getName():
//                id = ((SourceReport) o).getReportNumber();
//                url = SOURCE_REPORTS_URL + "/" + id;
//                break;
//            case PurityReport.class:
//                id = ((PurityReport) o).getReportNumber();
//                url = PURITY_REPORTS_URL + "/" + id;
//                break;
//            default:
//                Log.d(TAG, "Object passed to deleteReport not a source nor purity Report");
//                url = null;
//                break;
//        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.getMessage(), error);
                    }
        });

        mRequestQueue.add(request);
    }
}
