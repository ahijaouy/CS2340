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
import java.util.List;
import java.util.Map;

/**
 * A class that manages all reports and functionality by keeping a list of all reports
 * and providing ways to access and add these reports.
 *
 * Created by Walker on 2/26/17.
 */
public class ReportManager {

    private static final String TAG = ReportManager.class.getSimpleName();

    private static final ReportManager INSTANCE = new ReportManager();
    private final Map<Location, Report> mReportsMap;
    private final RequestQueue mRequestQueue;

    // URLs for http requests
    private static final String SOURCE_REPORTS_URL = App.getResString(R.string.base_url_source_reports);
    private static final String PURITY_REPORTS_URL = App.getResString(R.string.base_url_purity_reports);



    /**
     * Creates a ReportManager instance.
     */
    private ReportManager() {
        mReportsMap = new HashMap<>();
        mRequestQueue = Volley.newRequestQueue(App.getContext());
//        makeDummyReports();
//        makeDummyReports2();
    }

    public static ReportManager getInstance() {
        return INSTANCE;
    }


    /**
     * Adds a new source report to the map
     * @param type the type of the water source
     * @param cond the condition of the water source
     * @param loc the location of the water
     * @param name the name of the reporter
     */
    public void setSourceReport(SourceType type, SourceCondition cond, Location loc, String name) {
        Log.d(TAG, "Setting new Report");
        Report report = getReport(loc);
        if (report.hasSourceReport()) {
            // TODO: delete old sourceReport in DB
            Log.d(TAG, "delete old source report in db");
        }
        SourceReport sourceReport = new SourceReport(type, cond, name);
        report.setSourceReport(sourceReport);
        Log.d(TAG, "Sending report");

        try {
            sendReport(sourceReport, loc);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage(), e);
        }
        Log.d(TAG, "Added new report");
    }

    public void addPurityReport(PurityCondition cond, double virus, double contaminant, Location loc, String name) {
        Report report = getReport(loc);
        PurityReport purityReport = new PurityReport(cond, virus, contaminant, name);
        report.addPurityReport(purityReport);
        try {
            sendReport(purityReport, loc);
        } catch (JSONException e) {
            Log.d(TAG, e.getMessage(), e);
        }
        Log.d(TAG, "Adding purity report");
    }

    private void setOldSourceReport(Location location, SourceReport sourceReport) {
//        Log.d(TAG, "DOING NOTHING");
        Report report = getReport(location);
        report.setSourceReport(sourceReport);
    }

    private Report getReport(Location location) {
        if (mReportsMap.get(location) == null) {
            mReportsMap.put(location, new Report(location));
        }
        return mReportsMap.get(location);
    }

    private Report getReport(int sourceId) {
        for (Location location : mReportsMap.keySet()) {
            int id = mReportsMap.get(location).getCurrentSourceReportNumber();
            if (id == sourceId) {
                return mReportsMap.get(location);
            }
        }
        return null;
    }

    public List<PurityReport> getPurityReportsForLocation(Location lcn) {
//        return mReportsMap.get(lcn).getPurityReportsForLocation();
        if (mReportsMap.get(lcn) == null) {
            return null;
        } else {
            return mReportsMap.get(lcn).getPurityReportsForLocation();
        }
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
        Log.d(TAG, "Fetching purity reports");
        JsonArrayRequest jsonArrayRequest =
                new JsonArrayRequest(PURITY_REPORTS_URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.d(TAG, "adding purity reports from response");
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
     * @param report Report instance to be sent
     */
    private void sendReport(final Reportable report, Location location) throws JSONException {
        final JSONObject reportJson = report.toJson();
        String url;
        if (report instanceof SourceReport) {
            url = SOURCE_REPORTS_URL;
            reportJson.put("latitude", location.getLatitude());
            reportJson.put("longitude", location.getLongitude());
        } else {
            url = PURITY_REPORTS_URL;
            int sourceId = mReportsMap.get(location).getCurrentSourceReportNumber();
            reportJson.put("source_id", sourceId);
        }
        JsonObjectRequest jsonRequest = new JsonObjectRequest(url, reportJson,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "Sent: " + reportJson.toString());
                        Log.d(TAG, "Posted" +
                                "// TODO make delete requested: " + response.toString());
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
        mRequestQueue.add(jsonRequest);
    }

    /**
     * Add all source reports given by JSON array param.
     * @param jsonArray JSON array to be parsed and added
     * @throws JSONException json exception
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

        JSONObject reportJson = null;
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                reportJson = jsonArray.getJSONObject(i);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }

            assert reportJson != null;
            SourceType sourceType = SourceType.stringOf(reportJson.getString(WATER_TYPE));
            SourceCondition condition = SourceCondition.stringOf(reportJson.getString(WATER_COND));
            double lat = reportJson.getDouble(LAT);
            double lon = reportJson.getDouble(LNG);
            Location location = new Location(lat, lon);
            String name = reportJson.getString(USER);
            int id = reportJson.getInt(SOURCE_ID);
            LocalDateTime dateTime = LocalDateTime.parse(reportJson.getString(DATE).substring(0, 23));
            setOldSourceReport(location, new SourceReport(sourceType, condition, name, id, dateTime));
            Log.d(TAG, "Adding source report from DB");
        }
    }

    private void addOldPurityReports(JSONArray jsonArray) throws JSONException {

        // JSON object names
        final String PURITY_ID = "purity_report_id";
        final String SOURCE_ID = "source_id";
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
            assert reportJson != null;
            int purityId = reportJson.getInt(PURITY_ID);
            int sourceId = reportJson.getInt(SOURCE_ID);
            PurityCondition condition = PurityCondition.stringOf(CONDITION);
            double virus = reportJson.getDouble(VIRUS);
            double contaminant = reportJson.getDouble(CONTAMINANT);
            LocalDateTime dateTime = LocalDateTime.parse(reportJson.getString(DATE).substring(0, 23));
            String name = reportJson.getString(USER);
            addOldPurityReport(sourceId, new PurityReport(condition, virus, contaminant, name, purityId, dateTime));
            Log.d(TAG, "Adding purity report at source " + Integer.toString(sourceId));
        }
    }

    private void addOldPurityReport(int sourceId, PurityReport purityReport) {
        Report report = getReport(sourceId);
        if (report != null) {
            report.addPurityReport(purityReport);
        }
    }

    private void deleteSourceReport(int id) {
        String url = PURITY_REPORTS_URL + "/" + id;
        Log.d(TAG, "delete source report at " + url);
        // TODO make delete request
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
