package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class GeoLocationTest {
    protected Double longitude= 112.354;
    protected Double latitude = 12.354;

    @Test
    public void testLocation(){

        List<Double> Location = Arrays.asList(longitude, latitude);
        GeoLocation location = new GeoLocation(longitude,latitude);
        assertEquals(Location,location.getLocation());
    }
    @Test
    public void testCoordinate(){
        //String Location = "www.putianyiyuan.com";

        GeoLocation location = new GeoLocation(longitude,latitude);
        assertTrue(location.getLongtitude().equals(112.3));
        assertTrue(location.getLatitude().equals(12.3));
    }
}
