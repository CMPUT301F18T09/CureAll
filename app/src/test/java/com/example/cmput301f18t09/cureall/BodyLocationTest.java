package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;

public  class BodyLocationTest {


    @Test

    public void testAddBodyLocationPhoto() {
        String bodyLicationName = "arm";
        String photoLocation = "photoSize";
        double photoSize = 354654651316516546516.1641654654654654;
        double photoWidth = 123456789101112131415.1114664646465465465465;
        double photoLenth = 65465423132165465498465.13216546546498498465415132;

        ArrayList<AllKindsOfPhotos> testphoto = new ArrayList<>();
        testphoto.add(new AllKindsOfPhotos(photoLocation,"1",photoSize,photoWidth,photoLenth));

        BodyLocation body = new BodyLocation(bodyLicationName, testphoto);

        assertEquals(body.getBodyLocationName(),bodyLicationName);
        assertEquals(body.getBodyLocationPhotoArrayList(),testphoto);

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
