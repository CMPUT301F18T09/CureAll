package com.example.shabi;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


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
