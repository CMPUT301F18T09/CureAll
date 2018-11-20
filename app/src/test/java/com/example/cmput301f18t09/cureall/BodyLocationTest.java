package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public  class BodyLocationTest {


    @Test

    public void testAddBodyLocationPhoto() {
        String bodyLocationName = "arm";
        String photoLocation = "photoSize";
        double photoSize = 354654651316516546516.1641654654654654;
        double photoWidth = 123456789101112131415.1114664646465465465465;
        double photoLength = 65465423132165465498465.13216546546498498465415132;

        ArrayList<AllKindsOfPhotos> testPhoto = new ArrayList<>();
        testPhoto.add(new AllKindsOfPhotos(photoLocation,"1",photoSize,photoWidth,photoLength));

        ArrayList<String> bodyLocationPhotoArrayList = new ArrayList<>(  );

        BodyLocation body = new BodyLocation(bodyLocationName,  bodyLocationPhotoArrayList);

        assertEquals(body.getBodyLocationName(),bodyLocationName);
        assertEquals(body.getBodyLocationPhotoArrayList(),testPhoto);

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
