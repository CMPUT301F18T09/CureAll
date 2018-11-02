package com.example.cmput301f18t09.cureall;

import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;
import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;



public  class BodyLocationTest {
    @Test

    public void addBodyLocationPhoto() {
        /*photo should be store in bit format, then use assertTrue to test*/


    }

    @Test
    public void choosePaperDollPhoto() {
        /*photo should be store in bit format, then use assertTrue to test*/


    }

    @Test
    public void getLocationFromPaperDollPhoto() {
        String location = "location";
        AllKindsOfPhotos test = new AllKindsOfPhoto(location);
        assertEquals(test.getLocationFromPaperDollPhoto().size(),1);

    }

    @Test
    public void showPhotos() {
        /*不会*/

    }



}
