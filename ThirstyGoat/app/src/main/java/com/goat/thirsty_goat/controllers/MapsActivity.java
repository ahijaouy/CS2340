package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.Report;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

    private static final String TAG = MapsActivity.class.getSimpleName();

    public final static String LATITUDE_MESSAGE = "com.goat.thirsty_goat.LATITUDE";
    public final static String LONGITUDE_MESSAGE = "com.goat.thirsty_goat.LONGITUDE";

    private static final String KEY_CAMERA_POSITION = "camera_position";

    private GoogleMap mMap;
    private ModelFacade mFacade;
    private CameraPosition mCameraPosition;

    private LatLng mCurrLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve camera position from saved isntance state
        if (savedInstanceState != null) {
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }
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

                Log.d(TAG, "pre handle");
                // switches to Wa
                handleReport(latLng);
                Log.d(TAG, "post handle");

                displayMarkers();
                Log.d(TAG, "post display markers");
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
//        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//            mMap.setMyLocationEnabled(true);
//        } else {
//            // Show rationale and request permission.
//            Log.d(TAG, "my location not working");
//        }

        // Moving camera position back
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
            Log.d(TAG, "Moving camera to position");
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            Log.d(TAG, "Saving Camera Position");
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "MapActivity's onResume");
        super.onResume();
        if (mMap != null) {
            displayMarkers();
        }
    }

    private Map<Marker, Report> markerReportMap = new HashMap<>();

    private void displayMarkers() {
        Log.d(TAG, "displaying markers");
        Map<Location, Report> waterReportsMap = mFacade.getReports();
        Log.d(TAG, "number of reports:" + Integer.toString(waterReportsMap.size()));

        for (Location location : waterReportsMap.keySet()) {
            Report report = waterReportsMap.get(location);
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//            String titleString = report.getCurrentWaterSourceReportDateString();
//            titleString += "   Water Report #: " + report.getCurrentWaterSourceReportNumber();
//            String snippetString = "Lat: " + latLng.latitude + "  Long: " + latLng.longitude
//                    + "\nType: " +

            // this title and snippet should never be shown
            String title = "TITLEEEE";
            String snippet = "SNIPPETTTT";
            Marker markerAdded = mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .snippet(snippet));
            markerReportMap.put(markerAdded, report);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    private void handleReport(LatLng latLng) {
        Intent intent = new Intent(this, SourceReportActivity.class);
        intent.putExtra(LATITUDE_MESSAGE, latLng.latitude);
        intent.putExtra(LONGITUDE_MESSAGE, latLng.longitude);
        startActivity(intent);
    }


    private class CustomInfoWindowAdapter implements InfoWindowAdapter {

        private final View mContents;

        CustomInfoWindowAdapter() {
            //mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            Log.d(TAG, "CustomInfoWindowAdapter constructor p1");
            mContents = getLayoutInflater().inflate(R.layout.custom_info_window_contents, null);
            Log.d(TAG, "CustomInfoWindowAdapter constructor p2");
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // this defaults to calling getInfoContents below
            Log.d(TAG, "getInfoWindow");
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            Log.d(TAG, "getInfoContents");
            render(marker, mContents);
            return mContents;
        }

        private void render(Marker marker, View view) {
            Log.d(TAG, "render p1");

            Report thisReport = markerReportMap.get(marker);

            Log.d(TAG, "render p2");

            TextView markerDate = (TextView) view.findViewById(R.id.infoWindowDate);
            TextView markerReportNum = (TextView) view.findViewById(R.id.infoWindowReportNum);
            TextView markerLatitude = (TextView) view.findViewById(R.id.infoWindowLatitude);
            TextView markerLongitude = (TextView) view.findViewById(R.id.infoWindowLongitude);
            TextView markerType = (TextView) view.findViewById(R.id.infoWindowType);
            TextView markerCondition = (TextView) view.findViewById(R.id.infoWindowCondition);
            TextView markerReporter = (TextView) view.findViewById(R.id.infoWindowReporter);

            Log.d(TAG, "render p3");

            markerDate.setText(thisReport.getCurrentWaterSourceReportDateString());
            markerReportNum.setText(String.valueOf(thisReport.getCurrentWaterSourceReportNumber()));
            markerLatitude.setText(String.valueOf(thisReport.getLatitude()));
            markerLongitude.setText(String.valueOf(thisReport.getLongitude()));
            markerType.setText(thisReport.getCurrentWaterSourceReportTypeString());
            markerCondition.setText(thisReport.getCurrentWaterSourceReportConditionString());
            markerReporter.setText(thisReport.getCurrentWaterSourceReportName());

            Log.d(TAG, "render p4");
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
