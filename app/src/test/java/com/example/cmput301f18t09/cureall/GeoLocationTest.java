/**..*/
package com.example.cmput301f18t09.cureall;

import com.example.cmput301f18t09.cureall.model.GeoLocation;

import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class GeoLocationTest {

    @Test
    public void testLocation(){
        Double longitude= 112.354;
        Double latitude = 12.354;
        Double LO = 112.354;
        Double LA = 12.354;
        /**
         * Init for function testLocation
         * longitude    FOR ADDING
         * latitude     FOR ADDING
         * LO           FOR TESTING
         * LA           FOR TESTING
         */
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
        /**
         * Init for function testLocation
         * longitude    FOR ADDING
         * latitude     FOR ADDING
         * location     The testing location to be added
         */
        assertTrue( location.getLocation().equals( Location ) );

    }
}
