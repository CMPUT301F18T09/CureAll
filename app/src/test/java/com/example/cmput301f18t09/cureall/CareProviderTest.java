/**..*/
package com.example.cmput301f18t09.cureall;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class CareProviderTest {
    // Creating a problem instance for testing
    Problem problem = new Problem ("problemName","title","description", "time" ,"");
    // Creating a provider instance for testing
    CareProvider provider = new CareProvider("username", "password", "email", "phone");
    // Creating a patient instance for testing
    Patient patient = new Patient( "username", "passward", "email", "phone");

    String comment = "comment";
    /**
     * Init for class CareProviderTest
     * problem      a new problem for testing
     * provider     a new care provider for testing
     * comment      a string to be added as comment
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
