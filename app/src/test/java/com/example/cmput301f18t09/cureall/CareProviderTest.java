package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertTrue;

public class CareProviderTest {
    //Date date = new Date();
    // Creating a problem instance for testing
    Problem problem = new Problem ("problemName","title","description", "time" ,"");
    // Creating a provider instance for testing
    CareProvider provider = new CareProvider("username", "password", "email", "phone");

    int patientID = 111;
    // Creating a patient instance for testing
    Patient patient = new Patient( "username", "passward", "email", "phone");

    String comment = "comment";
    /*
        Testing method "addPatient" by adding a new patient to exisiting patient list
     */
/*    @Test
    public void testAddPatient() {
        Patient patient = new Patient("username","pass","email","phone");
        String patientName = patient.getUsername();
        provider.addPatientByName(patientName);
        assertTrue( provider.getPatientArrayList().contains(patient) );
    }*/
    /*
        Testing method "addComment" by adding a new comment to a exsiting problem
    */
    @Test
    public void testAddCommit(){
        provider.addComment(problem, comment);
        assertTrue(problem.getDoctorcomment().contains(comment));

    }



}
