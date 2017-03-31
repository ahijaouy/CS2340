package com.goat.thirsty_goat.controllers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.ModelFacade;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;

/**
 * Created by Walker on 3/29/17.
 */

public class ViewHistoricalReportActivity  extends AppCompatActivity {

    private EditText mLatitudeField;
    private EditText mLongitudeField;
    private EditText mYearField;
    private RadioButton mVirusRadio;
    private RadioButton mContaminantRadio;
    private Button mMakeGraphButton;
    private GraphView mGraph;

    private ModelFacade mFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_historical_report);

        mFacade = ModelFacade.getInstance();

        mLatitudeField = (EditText) findViewById(R.id.latitude_number);
        mLongitudeField = (EditText) findViewById(R.id.longitude_number);
        mYearField = (EditText) findViewById(R.id.year_number);
        mVirusRadio = (RadioButton) findViewById(R.id.virus_radio_button);
        mContaminantRadio = (RadioButton) findViewById(R.id.contaminant_radio_button);
        mMakeGraphButton = (Button) findViewById(R.id.make_graph_button);
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

            //
            mGraph = mFacade.createGraph(latitude, longitude, year, isVirus, mGraph);
            //

//            mGraph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
//            mGraph.getGridLabelRenderer().setVerticalAxisTitle("PPM");
//
//            mGraph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
//                @Override
//                public String formatLabel(double value, boolean isValueX) {
//                    if (isValueX) {
//                        // show normal x values
////                    return super.formatLabel(value, isValueX);
//                        // this is just to make sure it truncates to correct value
//                        int val = (int) (value + 0.2);
//                        String string = "";
//                        switch (val) {
//                            case 1:
//                                string = "Jan";
//                                break;
//                            case 2:
//                                string = "Feb";
//                                break;
//                            case 3:
//                                string = "March";
//                                break;
//                            case 4:
//                                string = "April";
//                                break;
//                            case 5:
//                                string = "May";
//                                break;
//                            case 6:
//                                string = "June";
//                                break;
//                            case 7:
//                                string = "July";
//                                break;
//                            case 8:
//                                string = "Aug";
//                                break;
//                            case 9:
//                                string = "Sep";
//                                break;
//                            case 10:
//                                string = "Oct";
//                                break;
//                            case 11:
//                                string = "Nov";
//                                break;
//                            case 12:
//                                string = "Dec";
//                                break;
//                            default:
//                                string = "NAM";
//                                break;
//                        }
//                        return string;
//                    } else {
//                        // show currency for y values
//                        return super.formatLabel(value, isValueX);
//                    }
//                }
//            });
//
//            // set manual X bounds
//            mGraph.getViewport().setYAxisBoundsManual(true);
//            mGraph.getViewport().setMinY(-150);
//            mGraph.getViewport().setMaxY(150);
//
//            mGraph.getViewport().setXAxisBoundsManual(true);
//            mGraph.getViewport().setMinX(4);
//            mGraph.getViewport().setMaxX(80);
//
//            // enable scaling and scrolling
//            mGraph.getViewport().setScalable(true);
//            mGraph.getViewport().setScalableY(true);
//
//            mGraph.addSeries(mFacade.createGraph(latitude, longitude, year, isVirus));
//            Log.d("reportmanager", "graph should have displayed");
        }
    }
}
