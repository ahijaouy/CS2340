package com.goat.thirsty_goat.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * This class represents a location and holds a latitude and longitude.
 */
public class Location {
//    private double mLatitude;
//    private double mLongitute;
    private LatLng mLatLng;

    public Location(double lat, double lon) {
        mLatLng = new LatLng(lat, lon);
//        mLatitude = lat;
//        mLongitute = lon;
    }

    public Location(LatLng latLng) {
        mLatLng = latLng;
    }

    /**
     * Gets the latitude of this location.
     * @return the latitude of this location
     */
    public double getLatitude() {
        return mLatLng.latitude;
//        return mLatitude;
    }

    /**
     * Gets the longitude of this location.
     * @return the longitude of this location
     */
    public double getLongitude() {
        return mLatLng.longitude;
//        return mLongitute;
    }


    @Override
    public String toString() {
        return getLatitude() + "," + getLongitude();
//        return mLatitude + "," + mLongitute;
    }
}
