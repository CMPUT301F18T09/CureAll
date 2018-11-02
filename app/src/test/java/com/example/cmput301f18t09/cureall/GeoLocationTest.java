package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class GeoLocationTest {
    @Test
    public void testLocation(){
        String Location = "www.putianyiyuan.com";

        GeoLocation location = new GeoLocation(Location,112.3,12.3);
        assertEquals(Location,location.getLocation());
    }
    @Test
    public void testCoordinate(){
        String Location = "www.putianyiyuan.com";
        GeoLocation location = new GeoLocation(Location,112.3,12.3);
        assertTrue(location.getLongtitude().equals(112.3));
        assertTrue(location.getLatitude().equals(12.3));
    }
}
