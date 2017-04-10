package com.goat.thirsty_goat.models;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by laddjones on 4/10/17.
 */
public class LocationTestEquals {
    @Test
    public void equalsTest() throws Exception {
        double testLat = 22;
        double testLong = 25;
        Location firstLoc = new Location(testLat, testLong);
        Location secondLoc = new Location(testLat, testLong);
        assertTrue(firstLoc.equals(secondLoc));
    }
    @Test
    public void notEqualsTest() throws Exception {
        double testLat = 22;
        double testLong = 25;
        Location firstLoc = new Location(testLat, testLong);
        double testLat2 = 24;
        double testLong2 = 27;
        Location secondLoc = new Location(testLat2, testLong2);
        assertFalse(firstLoc.equals(secondLoc));
    }
    @Test
    public void oneNUllEqualsTest() throws Exception {
        double testLat2 = 24;
        double testLong2 = 27;
        Location secondLoc = new Location(testLat2, testLong2);
        assertFalse(secondLoc.equals(null));
    }
}