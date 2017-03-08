package com.goat.thirsty_goat.models;

/**
 * This class represents a location and holds a latitude and longitude.
 */
public class Location {
    private double mLatitude;
    private double mLongitute;

    public Location(double lat, double lon) {
        mLatitude = lat;
        mLongitute = lon;
    }

    /**
     * Gets the latitude of this location.
     * @return the latitude of this location
     */
    public double getLatitude() {
        return mLatitude;
    }

    /**
     * Gets the longitude of this location.
     * @return the longitude of this location
     */
    public double getLongitude() {
        return mLongitute;
    }


    @Override
    public String toString() {
        return "Lat: " + mLatitude + "; Lon: " + mLongitute;
    }
}
