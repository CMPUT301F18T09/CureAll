package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.example.cmput301f18t09.cureall.GeoLocation;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class GeoLocationTest {



    @Test
    public void testLocation(){
        Double longitude= 112.354;
        Double latitude = 12.354;
        Double LO = 112.354;
        Double LA = 12.354;

        GeoLocation location = new GeoLocation(longitude,latitude);

        assertEquals( location.getLongitude(),longitude, LO);

        assertEquals( location.getLatitude(),latitude, LA );

    }
    @Test
    public void testCoordinate(){

        Double longitude= 112.354;
        Double latitude = 12.354;

        List<Double> Location = Arrays.asList(longitude, latitude);

        GeoLocation location = new GeoLocation(longitude,latitude);
        assertTrue( location.getLocation().equals( Location ) );

        /*assertTrue(location.getLongitude().equals(Location));
        assertTrue(location.getLatitude().equals(Location));*/

    }
}
