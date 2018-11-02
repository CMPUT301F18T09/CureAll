package com.example.cmput301f18t09.cureall;

import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.BodyLocation;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;


public  class BodyLocationTest {


    @Test

    public void addBodyLocationPhoto() {
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
    public void choosePaperDollPhoto() {

    }

    @Test
    public void getLocationFromPaperDollPhoto() {
    }

    @Test
    public void showPhotos() {

    }



}
