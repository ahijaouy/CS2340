package com.goat.thirsty_goat.controllers;

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
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.Report;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the activity that represents and displays the list of water reports that
 * have been submitted. Uses a RecyclerView.
 *
 * Created by Walker on 3/6/17.
 */
public class SourceReportListActivity extends AppCompatActivity {

    private static final String TAG = SourceReportListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceData) {
        super.onCreate(savedInstanceData);
        setContentView(R.layout.activity_water_report_list);
        setContentView(R.layout.water_report_list);

        //Step 1.  Setup the recycler view by getting it from our layout in the main window
//        View recyclerView = findViewById(R.id.water_report_list);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.water_report_list);
//        assert recyclerView != null;
        if (recyclerView == null) {
            Log.d(TAG, "recycler view is null");
        } else {
            Log.d(TAG, "recycler view is not null");
        }

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
        ModelFacade model = ModelFacade.getInstance();
        Log.d("report", "setting up recycler view");
        //Log.d("report", model.getReports().get(0).getName());

        WaterReportViewAdapter mAdapter = new WaterReportViewAdapter(new ArrayList<Report>(model.getReports().values()));
        Log.d("report", "adapter: " + mAdapter);
        recyclerView.setAdapter(mAdapter);
    }


    /**
     * This inner class is our custom adapter.  It takes our basic model information and
     * converts it to the correct layout for this view.
     *
     * In this case, we are just mapping the toString of the Course object to a text field.
     */
    public class WaterReportViewAdapter
            extends RecyclerView.Adapter<WaterReportViewAdapter.ViewHolder> {

        /**
         * Collection of the items to be shown in this list.
         */
        private List<Report> mReports;

        /**
         * set the items to be used by the adapter
         * @param items the list of items to be displayed in the recycler view
         */
        public WaterReportViewAdapter(List<Report> items) {
            mReports = items;
            Log.d(TAG, "Made it to constructor: " + mReports.get(0).getCurrentWaterSourceReportName());
            if (items == null) {
                Log.d(TAG, "called constructor with null items");
            }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.d(TAG, "is this ever called? 1");
            /*

              This sets up the view for each individual item in the recycler display
              To edit the actual layout, we would look at: res/layout/course_list_content.xml
              If you look at the example file, you will see it currently just 2 TextView elements
             */
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.water_report_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
//        public void onBindViewHolder(final ViewHolder holder, int position) {
        public void onBindViewHolder(ViewHolder holder, int position) {
            Log.d(TAG, "is this ever called? 2");

//            final ModelFacade model = ModelFacade.getInstance();
            ModelFacade model = ModelFacade.getInstance();

            /*
            This is where we have to bind each data element in the list (given by position parameter)
            to an element in the view (which is one of our two TextView widgets
             */
            //start by getting the element at the correct position
            holder.mReport = mReports.get(position);
            /*
              Now we bind the data to the widgets.  In this case, pretty simple, put the id in one
              textview and the string rep of a course in the other.
             */
            holder.mNumber.setText("" + mReports.get(position).getCurrentWaterSourceReportNumber());
            holder.mDateAndTime.setText(mReports.get(position).getCurrentWaterSourceReportDateString());
            holder.mReporterName.setText(mReports.get(position).getCurrentWaterSourceReportName());
            holder.mLatitude.setText("" + mReports.get(position).getLatitude());
            holder.mLongitude.setText("" + mReports.get(position).getLongitude());
            holder.mWaterType.setText(mReports.get(position).getCurrentWaterSourceReportTypeString());
            holder.mWaterCondition.setText(mReports.get(position).getCurrentWaterSourceReportConditionString());



            /*
             * set up a listener to handle if the user clicks on this list item, what should happen?
             */
            /*
              NOTE: I didn't implement this yet, since it's not required for M6 and the recycler view
              shows all the info for each report. But, if you want to be able to take an action upon
              clicking the view, this might be a good place to start
            */
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //on a phone, we need to change windows to the detail view
//                    Context context = v.getContext();
//                    //create our new intent with the new screen (activity)
//                    Intent intent = new Intent(context, CourseDetailActivity.class);
//                    /*
//                        pass along the id of the course so we can retrieve the correct data in
//                        the next window
//                     */
//                    intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, holder.mCourse.getId());
//
//                    model.setCurrentCourse(holder.mCourse);
//
//                    //now just display the new window
//                    context.startActivity(intent);
//                }
//            });
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "is this ever called? 3");
            return mReports.size();
        }

        /**
         * This inner class represents a ViewHolder which provides us a way to cache information
         * about the binding between the model element (in this case a Report) and the widgets in
         * the list view (in this case all the data to display for a report)
         */
        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mNumber;
            public final TextView mDateAndTime;
            public final TextView mReporterName;
            public final TextView mLatitude;
            public final TextView mLongitude;
            public final TextView mWaterType;
            public final TextView mWaterCondition;
            public Report mReport;

            public ViewHolder(View view) {
                super(view);
                Log.d(TAG, "is this ever called? 5");
                mView = view;
                mNumber = (TextView) view.findViewById(R.id.reportNumber);
                mDateAndTime = (TextView) view.findViewById(R.id.reportDateAndTime);
                mReporterName = (TextView) view.findViewById(R.id.reportNameData);
                mLatitude = (TextView) view.findViewById(R.id.reportLatitudeData);
                mLongitude = (TextView) view.findViewById(R.id.reportLongitudeData);
                mWaterType = (TextView) view.findViewById(R.id.reportTypeData);
                mWaterCondition = (TextView) view.findViewById(R.id.reportConditionData);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mNumber.getText() + "'";
            }
        }
    }
}
