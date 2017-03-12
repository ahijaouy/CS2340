package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.WaterReport;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;


import java.util.HashMap;
import java.util.Map;

/**
 * This activity handles the Google Maps view and is responsible for everything related
 * to the map and all interactions with it.
 */
public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnInfoWindowLongClickListener,
        GoogleMap.OnInfoWindowCloseListener {

    public final static String LATITUDE_MESSAGE = "com.goat.thirsty_goat.LATITUDE";
    public final static String LONGITUDE_MESSAGE = "com.goat.thirsty_goat.LONGITUDE";

    private GoogleMap mMap;
    private ModelFacade mFacade;

    private LatLng mCurrLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mFacade = ModelFacade.getInstance();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Setting a click event handler for the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                mCurrLatLong = latLng;

                Log.d("WaterReport", "pre handle");
                // switches to Wa
                handleReport(latLng);
                Log.d("WaterReport", "post handle");

                displayMarkers();
                Log.d("WaterReport", "post display markers");
            }
        });

        displayMarkers();

        // Setting an info window adapter allows us to change the both the contents and look of the
        // info window.
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter());

        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerDragListener(this);
        mMap.setOnInfoWindowCloseListener(this);
        mMap.setOnInfoWindowLongClickListener(this);
    }

    @Override
    public void onResume() {
        Log.d("WaterReport", "MapActivity's onResume");
        super.onResume();
        if (mMap != null) {
            displayMarkers();
        }
    }

    private Map<Marker, WaterReport> markerReportMap = new HashMap<>();

    private void displayMarkers() {
        Log.d("WaterReport", "displaying markers");
        Map<Location, WaterReport> waterReportsMap = mFacade.getReports();

        for (Location location : waterReportsMap.keySet()) {
            WaterReport waterReport = waterReportsMap.get(location);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            String titleString = waterReport.getCurrentWaterSourceReportDateString();
//            titleString += "   Water Report #: " + waterReport.getCurrentWaterSourceReportNumber();
//            String snippetString = "Lat: " + latLng.latitude + "  Long: " + latLng.longitude
//                    + "\nType: " +

            // this title and snippet should never be shown
            String title = "TITLEEEE";
            String snippet = "SNIPPETTTT";
            Marker markerAdded = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .snippet(snippet));
            markerReportMap.put(markerAdded, waterReport);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    private void handleReport(LatLng latLng) {
        Intent intent = new Intent(this, WaterSourceReportActivity.class);
        intent.putExtra(LATITUDE_MESSAGE, latLng.latitude);
        intent.putExtra(LONGITUDE_MESSAGE, latLng.longitude);
        startActivity(intent);
    }


    private class CustomInfoWindowAdapter implements InfoWindowAdapter {

        private final View mContents;

        CustomInfoWindowAdapter() {
            //mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            Log.d("report", "CustomInfoWindowAdapter constructor p1");
            mContents = getLayoutInflater().inflate(R.layout.custom_info_window_contents, null);
            Log.d("report", "CustomInfoWindowAdapter constructor p2");
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // this defaults to calling getInfoContents below
            Log.d("report", "getInfoWindow");
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            Log.d("report", "getInfoContents");
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            Log.d("report", "render p1");

            WaterReport thisWaterReport = markerReportMap.get(marker);

            Log.d("report", "render p2");

            TextView markerDate = (TextView) view.findViewById(R.id.infoWindowDate);
            TextView markerReportNum = (TextView) view.findViewById(R.id.infoWindowReportNum);
            TextView markerLatitude = (TextView) view.findViewById(R.id.infoWindowLatitude);
            TextView markerLongitude = (TextView) view.findViewById(R.id.infoWindowLongitude);
            TextView markerType = (TextView) view.findViewById(R.id.infoWindowType);
            TextView markerCondition = (TextView) view.findViewById(R.id.infoWindowCondition);
            TextView markerReporter = (TextView) view.findViewById(R.id.infoWindowReporter);

            Log.d("report", "render p3");

            markerDate.setText(thisWaterReport.getCurrentWaterSourceReportDateString());
            markerReportNum.setText(String.valueOf(thisWaterReport.getCurrentWaterSourceReportNumber()));
            markerLatitude.setText(String.valueOf(thisWaterReport.getLatitude()));
            markerLongitude.setText(String.valueOf(thisWaterReport.getLongitude()));
            markerType.setText(thisWaterReport.getCurrentWaterSourceReportTypeString());
            markerCondition.setText(thisWaterReport.getCurrentWaterSourceReportConditionString());
            markerReporter.setText(thisWaterReport.getCurrentWaterSourceReportName());

            Log.d("report", "render p4");
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        return false;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
//        Toast.makeText(this, "Click Info Window", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowClose(Marker marker) {
        //Toast.makeText(this, "Close Info Window", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onInfoWindowLongClick(Marker marker) {
//        Toast.makeText(this, "Info Window long click", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMarkerDragStart(Marker marker) {
//        mTopText.setText("onMarkerDragStart");
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
//        mTopText.setText("onMarkerDragEnd");
    }

    @Override
    public void onMarkerDrag(Marker marker) {
//        mTopText.setText("onMarkerDrag.  Current Position: " + marker.getPosition());
    }
}
