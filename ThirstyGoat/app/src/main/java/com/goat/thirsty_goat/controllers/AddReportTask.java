package com.goat.thirsty_goat.controllers;

import android.os.AsyncTask;
import android.util.Log;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.Report;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by GabrielNAN on 3/8/17.
 */

public class AddReportTask extends AsyncTask<Report, Void, Report> {

    private static final String TAG = AddReportTask.class.getSimpleName();

    @Override
    protected Report doInBackground(Report... reports) {

        if (reports.length == 0) {
            Log.d(TAG, "No parameters passed to AddReportTask.execute()");
        }

        HttpURLConnection urlConnection = null;

        String reportJsonString;
        final String BASE_URL = App.getResString(R.string.base_url_source_reports);

        try {

            // Establish connection
            URL url = new URL(BASE_URL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            urlConnection.setDoOutput(true);
            urlConnection.setChunkedStreamingMode(0);

            OutputStream outputStream = urlConnection.getOutputStream();
        } catch (IOException e) {
            Log.e(TAG, "Error", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return null;
    }
}
