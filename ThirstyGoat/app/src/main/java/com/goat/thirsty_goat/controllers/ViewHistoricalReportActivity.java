package com.goat.thirsty_goat.controllers;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.ModelFacade;
import com.jjoe64.graphview.GraphView;

import java.util.NoSuchElementException;

/**
 * Created by Walker on 3/29/17.
 *
 * Activity to display historical reports.
 */

public class ViewHistoricalReportActivity  extends AppCompatActivity {

    private EditText mLatitudeField;
    private EditText mLongitudeField;
    private EditText mYearField;
    private RadioButton mVirusRadio;
    private GraphView mGraph;

    private ModelFacade mFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_historical_report);

        mFacade = ModelFacade.getInstance();

        mLatitudeField = (EditText) findViewById(R.id.latitude_number);
        mLongitudeField = (EditText) findViewById(R.id.longitude_number);
        mYearField = (EditText) findViewById(R.id.year);
        mVirusRadio = (RadioButton) findViewById(R.id.virus_radio_button);
        mGraph = (GraphView) findViewById(R.id.graph);
    }

    public void onMakeGraphButtonClicked(View view) {
        boolean isVirus = mVirusRadio.isChecked();
        String latitudeStr = mLatitudeField.getText().toString();
        String longitudeStr = mLongitudeField.getText().toString();
        String yearStr = mYearField.getText().toString();

        if (!longitudeStr.equals("") && !latitudeStr.equals("") && !yearStr.equals("")) {
            double latitude = Double.parseDouble(mLatitudeField.getText().toString());
            double longitude = Double.parseDouble(mLongitudeField.getText().toString());
            int year = Integer.parseInt(mYearField.getText().toString());

            try {
                mGraph = mFacade.createGraph(latitude, longitude, year, isVirus, mGraph);
            } catch (NoSuchElementException exc) {
                Context context = getApplicationContext();
                CharSequence text = exc.getMessage();
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(context, text, duration);
                toast.setGravity(Gravity.CENTER, 0, 500);
                toast.show();
            }

        }
    }
}
