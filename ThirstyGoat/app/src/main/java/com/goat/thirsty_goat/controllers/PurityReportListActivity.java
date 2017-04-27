package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.PurityReport;
import com.goat.thirsty_goat.models.Report;

import java.util.ArrayList;
import java.util.List;


/**
 * This is the activity that represents and displays the list of water reports that
 * have been submitted. Uses a RecyclerView.
 *
 * Created by Walker on 3/6/17.
 */
public class PurityReportListActivity extends AppCompatActivity  {

    private Bundle extras;
    private static String TAG = PurityReportListActivity.class.getSimpleName();
    private ModelFacade mFacade;
    private Location mSourceLocation;

    @Override
    protected void onCreate(final Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        mFacade = ModelFacade.getInstance();

        setContentView(R.layout.activity_water_report_list);
        setContentView(R.layout.purity_report_list);

        //Step 1.  Setup the recycler view by getting it from our layout in the main window
//        View recyclerView = findViewById(R.id.water_report_list);
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.purity_report_list);

        if (recyclerView == null) {
            Log.d(TAG, "recycler view is null");
        } else {
            Log.d(TAG, "recycler view is not null");
        }

        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Step 2.  Hook up the adapter to the view
//        setupRecyclerView((RecyclerView) recyclerView);
        setupRecyclerView(recyclerView);
    }

    /**
     * Set up an adapter and hook it to the provided view
     * @param recyclerView  the view that needs this adapter
     */
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        Intent intent = getIntent();
        extras = intent.getExtras();

        Log.d(TAG, "setting up recycler view");
        //Log.d(TAG, model.getReports().get(0).getName());

        ArrayList<PurityReport> list = new ArrayList<>();
        mSourceLocation = new Location((double) extras.get("lat"), (double) extras.get("long"));
        Report report = mFacade.getReports().get(mSourceLocation);
        list.addAll(report.getPurityReportList());

        PurityReportViewAdapter mAdapter = new PurityReportViewAdapter(list);
        Log.d(TAG, "adapter: " + mAdapter);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     * In this case, we are just mapping the toString of the Course object to a text field.
     */
    public class PurityReportViewAdapter
            extends RecyclerView.Adapter<PurityReportViewAdapter.ViewHolder> {


        /**
         * Collection of the items to be shown in this list.
         */
        private final List<PurityReport> mPurityReports;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public PurityReportViewAdapter(List<PurityReport> items) {
            mPurityReports = items;
            if (items == null) {
                Log.d(TAG, "called constructor with null items");
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            Log.d(TAG, "is this ever called? 1");
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.purity_report_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
        public void onBindViewHolder(ViewHolder holder, final int position) {
//            Log.d(TAG, "is this ever called? 2");

//            final ModelFacade model = ModelFacade.getInstance();

            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            PurityReport purityReport = mPurityReports.get(position);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.mNumber.setText(String.valueOf(purityReport.getReportNumber()));
            holder.mDateAndTime.setText(purityReport.getReadableDateTimeString());
            holder.mReporterName.setText(purityReport.getName());
            holder.mLatitude.setText(mSourceLocation.getLatitudeString());
            holder.mLongitude.setText(mSourceLocation.getLongitudeString());
            holder.mCondition.setText(purityReport.getConditionString());
            holder.mContaminantPPM.setText(String.valueOf(purityReport.getContaminantPPM()));
            holder.mVirusPPM.setText(String.valueOf(purityReport.getVirusPPM()));



            // Can be implemented to show individual report
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //on a phone, we need to change windows to the detail view
//                    Context context = v.getContext();
//                    //create our new intent with the new screen (activity)
//                    if (model.getAccountType().equals("Administrator") ||
//                            model.getAccountType().equals("Manager")) {
//
//                    } else if (model.getAccountType().equals("Worker")) {
//                        Intent intent = new Intent(context, PurityReportActivity.class);
//                    /*
//                        pass along the id of the course so we can retrieve the correct data in
//                        the next window
//                     */
//                        intent.putExtra("lat", mWaterReports.get(position).getLatitude());
//                        intent.putExtra("long", mWaterReports.get(position).getLongitude());
//
//                        //model.setCurrentCourse(holder.mCourse);
//
//                        //now just display the new window
//                        context.startActivity(intent);
//                    }
//                }
//            });
        }

        @Override
        public int getItemCount() {
//            Log.d(TAG, "is this ever called? 3");
            return mPurityReports.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a WaterReport) and the widgets in
         * the list view (in this case all the data to display for a report)
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final TextView mNumber;
            public final TextView mDateAndTime;
            public final TextView mReporterName;
            public final TextView mLatitude;
            public final TextView mLongitude;
            public final TextView mCondition;
            public final TextView mVirusPPM;
            public final TextView mContaminantPPM;

            public ViewHolder(View view) {
                super(view);
//              Log.d(TAG, "is this ever called? 5");
                mNumber = (TextView) view.findViewById(R.id.reportNumber);
                mDateAndTime = (TextView) view.findViewById(R.id.reportDateAndTime);
                mReporterName = (TextView) view.findViewById(R.id.reportNameData);
                mLatitude = (TextView) view.findViewById(R.id.reportLatitudeData);
                mLongitude = (TextView) view.findViewById(R.id.reportLongitudeData);
                mCondition = (TextView) view.findViewById(R.id.reportConditionData);
                mVirusPPM = (TextView) view.findViewById(R.id.reportVirusPPMData);
                mContaminantPPM = (TextView) view.findViewById(R.id.reportContaminantPPMData);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mNumber.getText() + "'";
            }
        }
    }

}