package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import com.example.cmput301f18t09.cureall.GeoLocation;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class GeoLocationTest {

    Double longitude= 112.354;
    Double latitude = 12.354;
    List<Double> Location = Arrays.asList(longitude, latitude);

    @Test
    public void testLocation(){
        Double longitude= 112.354;
        Double latitude = 12.354;

        GeoLocation location = new GeoLocation(longitude,latitude);
        assertEquals((Object) location.getLongitude(),longitude);
        assertEquals((Object) location.getLatitude(),latitude );
    }
    @Test
    public void testCoordinate(){
        String Location = "www.putianyiyuan.com";
        Double longitude= 112.354;
        Double latitude = 12.354;

        GeoLocation location = new GeoLocation(longitude,latitude);
        assertTrue( location.getLocation().equals( Location ) );

        /*assertTrue(location.getLongitude().equals(Location));
        assertTrue(location.getLatitude().equals(Location));*/

    }
}
