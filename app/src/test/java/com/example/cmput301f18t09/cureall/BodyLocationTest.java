package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;


import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import com.example.cmput301f18t09.cureall.BodyLocation;

import junit.framework.TestCase;

public  class BodyLocationTest {


    @Test

    public void testAddBodyLocationPhoto() {
        String bodyLocationName = "arm";
        String photoLocation = "www.putianyiyuan.com";
        String photoType = "1";
        double photoSize = 354654651316516546516.1641654654654654;
        double photoWidth = 123456789101112131415.1114664646465465465465;
        double photoLength = 65465423132165465498465.13216546546498498465415132;

        ArrayList<AllKindsOfPhotos> testPhoto = new ArrayList<>();
        testPhoto.add(new AllKindsOfPhotos(photoLocation,photoType,photoSize,photoWidth,photoLength));

        ArrayList<String> bodyLocationPhotoArrayList = new ArrayList<String>(  );
        bodyLocationPhotoArrayList.add(photoLocation);
        BodyLocation body = new BodyLocation(bodyLocationName,  bodyLocationPhotoArrayList);

        assertEquals(body.getBodyLocationPhotoArrayList().get(0),testPhoto.get(0).getPhotoLocation());


    }

    @Test
    public void testChoosePaperDollPhoto() {
    }

    @Test
    public void testGetLocationFromPaperDollPhoto() {
    }

    @Test
    public void testShowPhotos() {

    }



}
