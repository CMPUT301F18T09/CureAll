package com.example.shabi;

import com.example.cmput301f18t09.cureall.Patient;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


public class TestCareProvider {
    @Test

    public void addPatient() {

        Patient patient = new Patient( "title", "description" );
        String title = patient.getTitle();
        Assign assign = new Assign("patient");
        assign.addPatient(patient);

        assertTrue( assign.hasPatient("title") );
    }

    private void getPatient(){
        Patient patient = addPatient();
        String patientTitle = patient.getTitle();

        assertEquals( patientTitle, "patientTitle" );
    }

    public void openAmapOfAllRecord(){
        MapLocation mapLocation;
        mapLocation = new maplocation("title","description",new Date() );
        String title = mapLocation.getTitle();

        assertEquals( title, "title" );
        }

    public void viewPatient(){
        Patient patient = new Patient( "title", "description" );
        String title = patient.getTitle();

        assertEquals( title, "title" );
    }
}
