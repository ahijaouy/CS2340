package com.goat.thirsty_goat.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.goat.thirsty_goat.R;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.Report;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
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
    private static final String TAG = MapsActivity.class.getSimpleName();

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
        List<Report> reportList = mFacade.getReports();
        for (Report r : reportList) {
            LatLng loc = new LatLng(r.getLatitude(), r.getLongitude());
            //mMap.addMarker(new MarkerOptions().position(loc).title(r.getName()).snippet(r.getDescription()));
            String titleString = r.getDateTimeString() + "   Report #: " + r.getReportNumber();
            String snippetString = "Lat: " + r.getLatitude() + "  Long: " + r.getLongitude()
                    + "\nType: " + r.getWaterTypeString()
                    + "\nCondition: " + r.getWaterConditionString()
                    + "\nReporter name: " + r.getName();

            Marker markerAdded = mMap.addMarker(new MarkerOptions()
                    .position(loc)
                    //.title(r.getWaterCondition().toString())
                    .title(titleString)
                    .snippet(snippetString));
            markerReportMap.put(markerAdded, r);

            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        }
    }

    private void handleReport(LatLng latLng) {
        Intent intent = new Intent(this, WaterReportActivity.class);
        intent.putExtra(LATITUDE_MESSAGE, latLng.latitude);
        intent.putExtra(LONGITUDE_MESSAGE, latLng.longitude);
        startActivity(intent);
    }



    class CustomInfoWindowAdapter implements InfoWindowAdapter {

        // These are both viewgroups containing an ImageView with id "badge" and two TextViews with id
        // "title" and "snippet".
        //private final View mWindow;

        private final View mContents;

        CustomInfoWindowAdapter() {
            //mWindow = getLayoutInflater().inflate(R.layout.custom_info_window, null);
            Log.d(TAG, "CustomInfoWindowAdapter constructor p1");
            mContents = getLayoutInflater().inflate(R.layout.custom_info_window_contents, null);
            Log.d(TAG, "CustomInfoWindowAdapter constructor p2");
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // default stuff from google maps example
//            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_window) {
//                // This means that getInfoContents will be called.
//                return null;
//            }
//            render(marker, mWindow);
//            return mWindow;

            // this defaults to calling getInfoContents below
            Log.d(TAG, "getInfoWindow");
            return null;
        }

        @Override
        public View getInfoContents(Marker marker) {
            // default stuff from google maps example
//            if (mOptions.getCheckedRadioButtonId() != R.id.custom_info_contents) {
//                // This means that the default info contents will be used.
//                return null;
//            }
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

            markerDate.setText(thisReport.getDateTimeString());
            markerReportNum.setText(String.valueOf(thisReport.getReportNumber()));
            markerLatitude.setText(String.valueOf(thisReport.getLatitude()));
            markerLongitude.setText(String.valueOf(thisReport.getLongitude()));
            markerType.setText(thisReport.getWaterTypeString());
            markerCondition.setText(thisReport.getWaterConditionString());
            markerReporter.setText(thisReport.getName());

            Log.d(TAG, "render p4");

            // this is all from the google maps demo
//            int badge;
//            // Use the equals() method on a Marker to check for equals.  Do not use ==.
//            if (marker.equals(mBrisbane)) {
//                badge = R.drawable.badge_qld;
//            } else if (marker.equals(mAdelaide)) {
//                badge = R.drawable.badge_sa;
//            } else if (marker.equals(mSydney)) {
//                badge = R.drawable.badge_nsw;
//            } else if (marker.equals(mMelbourne)) {
//                badge = R.drawable.badge_victoria;
//            } else if (marker.equals(mPerth)) {
//                badge = R.drawable.badge_wa;
//            } else {
//                // Passing 0 to setImageResource will clear the image view.
//                badge = 0;
//            }
//            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
//
//            String title = marker.getTitle();
//            TextView titleUi = ((TextView) view.findViewById(R.id.title));
//            if (title != null) {
//                // Spannable string allows us to edit the formatting of the text.
//                SpannableString titleText = new SpannableString(title);
//                titleText.setSpan(new ForegroundColorSpan(Color.RED), 0, titleText.length(), 0);
//                titleUi.setText(titleText);
//            } else {
//                titleUi.setText("");
//            }
//
//            String snippet = marker.getSnippet();
//            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
//            if (snippet != null && snippet.length() > 12) {
//                SpannableString snippetText = new SpannableString(snippet);
//                snippetText.setSpan(new ForegroundColorSpan(Color.MAGENTA), 0, 10, 0);
//                snippetText.setSpan(new ForegroundColorSpan(Color.BLUE), 12, snippet.length(), 0);
//                snippetUi.setText(snippetText);
//            } else {
//                snippetUi.setText("");
//            }
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
//        if (marker.equals(mPerth)) {
//            // This causes the marker at Perth to bounce into position when it is clicked.
//            final Handler handler = new Handler();
//            final long start = SystemClock.uptimeMillis();
//            final long duration = 1500;
//
//            final Interpolator interpolator = new BounceInterpolator();
//
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    long elapsed = SystemClock.uptimeMillis() - start;
//                    float t = Math.max(
//                            1 - interpolator.getInterpolation((float) elapsed / duration), 0);
//                    marker.setAnchor(0.5f, 1.0f + 2 * t);
//
//                    if (t > 0.0) {
//                        // Post again 16ms later.
//                        handler.postDelayed(this, 16);
//                    }
//                }
//            });
//        } else if (marker.equals(mAdelaide)) {
//            // This causes the marker at Adelaide to change color and alpha.
//            marker.setIcon(BitmapDescriptorFactory.defaultMarker(mRandom.nextFloat() * 360));
//            marker.setAlpha(mRandom.nextFloat());
//        }
//
//        // Markers have a z-index that is settable and gettable.
//        float zIndex = marker.getZIndex() + 1.0f;
//        marker.setZIndex(zIndex);
//        Toast.makeText(this, marker.getTitle() + " z-index set to " + zIndex,
//                Toast.LENGTH_SHORT).show();
//
//        // Markers can store and retrieve a data object via the getTag/setTag methods.
//        // Here we use it to retrieve the number of clicks stored for this marker.
//        Integer clickCount = (Integer) marker.getTag();
//        // Check if a click count was set.
//        if (clickCount != null) {
//            clickCount = clickCount + 1;
//            // Markers can store and retrieve a data object via the getTag/setTag methods.
//            // Here we use it to store the number of clicks for this marker.
//            marker.setTag(clickCount);
//            mTagText.setText(marker.getTitle() + " has been clicked " + clickCount + " times.");
//        } else {
//            mTagText.setText("");
//        }
//
//        mLastSelectedMarker = marker;
//        // We return false to indicate that we have not consumed the event and that we wish
//        // for the default behavior to occur (which is for the camera to move such that the
//        // marker is centered and for the marker's info window to open, if it has one).
//        return false;
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
