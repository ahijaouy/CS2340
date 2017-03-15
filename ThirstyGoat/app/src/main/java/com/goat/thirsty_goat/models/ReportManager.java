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

    private static ReportManager INSTANCE = new ReportManager();
    private Map<Location, WaterReport> mWaterReportsMap;
    private static final String BASE_URL = App.getContext().getString(R.string.base_url);

    /**
     * Creates a ReportManager instance.
     */
    public ReportManager() {
        mWaterReportsMap = new HashMap<>();
//        makeDummyReports();
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
