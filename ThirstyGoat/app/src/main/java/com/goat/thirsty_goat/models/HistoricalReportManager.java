package com.goat.thirsty_goat.models;

import android.util.Log;

import com.goat.thirsty_goat.models.ModelFacade;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Walker on 3/29/17.
 */

public class HistoricalReportManager {
    private static HistoricalReportManager INSTANCE = new HistoricalReportManager();

    private ModelFacade mFacade;

    public HistoricalReportManager() {
        mFacade = ModelFacade.getInstance();
    }

    public static HistoricalReportManager getInstance() {
        return INSTANCE;
    }

    public GraphView createGraph(double latitude, double longitude, int year, boolean isVirus, GraphView graph) {
        //make sure it gets the latest instance
        mFacade = ModelFacade.getInstance();

        Log.d("reportmanager", "" + (mFacade == null));
        List<PurityReport> allPurityReports = mFacade.getPurityReportsForLocation(latitude, longitude);

        Log.d("reportmanager", "# all purity reports = " + allPurityReports.size());

        graph.removeAllSeries();

        List<PurityReport> filteredPurityReports = new LinkedList<>();
        for (PurityReport report : allPurityReports) {
            if (report.getYear() == year) {
                filteredPurityReports.add(report);
            }
        }
        Collections.sort(filteredPurityReports, new Comparator<PurityReport>() {
            @Override
            public int compare(PurityReport pr1, PurityReport pr2) {
//                return pr1.getMonth() - pr2.getMonth();
                if (pr1.getMonth() < pr2.getMonth()) {
                    return -1;
                } else if (pr1.getMonth() > pr2.getMonth()) {
                    return 1;
                } else {
                    return pr1.getDay() - pr2.getDay();
                }
            }
        });

        Log.d("reportmanager", "# purity reports in given year = " + filteredPurityReports.size());

        DataPoint[] dataPoints = new DataPoint[filteredPurityReports.size()];
        int i = 0;
        for (PurityReport report : filteredPurityReports) {
            if (isVirus) {
                // the addition of month and day/31.0 is to spread out the days in each month semi-evenly
                Log.d("reportmanager", String.format("day: %d   day/31: %f   month: %d", report.getDay(), report.getDay()/31.0, report.getMonth()));
                dataPoints[i++] = new DataPoint(report.getMonth() + (report.getDay() / 31.0), report.getVirusPPM());
            } else {
                dataPoints[i++] = new DataPoint(report.getMonth() + (report.getDay() / 31.0), report.getContaminantPPM());
            }
//            Log.d("reportmanager", "datapoint added = " + dataPoints[i-1]);
        }

        Log.d("reportmanager", "number of datapoints = " + dataPoints.length);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
        series.setDrawDataPoints(true);
        graph.addSeries(series);





        graph.getGridLabelRenderer().setHorizontalAxisTitle("Month");
        graph.getGridLabelRenderer().setVerticalAxisTitle("PPM");

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
//                    return super.formatLabel(value, isValueX);
                    // this is just to make sure it truncates to correct value
                    int val = (int) (value + 0.2);
                    String string = "";
                    switch (val) {
                        case 0:
                            break;
                        case 1:
                            string = "Jan";
                            break;
                        case 2:
                            string = "Feb";
                            break;
                        case 3:
                            string = "March";
                            break;
                        case 4:
                            string = "April";
                            break;
                        case 5:
                            string = "May";
                            break;
                        case 6:
                            string = "June";
                            break;
                        case 7:
                            string = "July";
                            break;
                        case 8:
                            string = "Aug";
                            break;
                        case 9:
                            string = "Sep";
                            break;
                        case 10:
                            string = "Oct";
                            break;
                        case 11:
                            string = "Nov";
                            break;
                        case 12:
                            string = "Dec";
                            break;
                        default:
                            string = "";
                            break;
                    }
                    return string;
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX);
                }
            }
        });
//
        // set manual X bounds
//        graph.getViewport().setYAxisBoundsManual(true);
//        graph.getViewport().setMinY(0);
//        graph.getViewport().setMaxY(150);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(14);

        // enable scaling and scrolling
//        graph.getViewport().setScrollable(true);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        return graph;
    }
}
