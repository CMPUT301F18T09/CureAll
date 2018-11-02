package com.example.cmput301f18t09.cureall;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CareProviderTest {
    Date date = new Date();
    Problem problem = new Problem ("problemName","description", date );
    CareProvider provider = new CareProvider("username", "password", "email", "phone");
    int patientID = 111;
    Patient patient = new Patient( "username", "passward", "email", "phone", patientID );
    String comment = "comment";
    public void addPatient() {

        String patientName = patient.getUsername();
        provider.addPatientByName(patientName);
        assertTrue( provider.getPatientArrayList().contains(patient) );
    }

    public void addCommit(){
        provider.addComment(problem, comment);
        assertTrue(problem.getCommentArraylist().contains(comment));

    }



}
