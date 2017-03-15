package com.goat.thirsty_goat.models;

import com.google.android.gms.maps.model.LatLng;

/**
 * This class represents a location and holds a latitude and longitude.
 */
public class Location {
    private double mLatitude;
    private double mLongitute;
    private LatLng mLatLng;

    public Location(double lat, double lon) {
        mLatLng = new LatLng(lat, lon);
        mLatitude = lat;
        mLongitute = lon;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Location location = (Location) o;

        if (Double.compare(location.mLatitude, mLatitude) != 0) return false;
        return Double.compare(location.mLongitute, mLongitute) == 0;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(mLatitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(mLongitute);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
