/**..*/
package com.example.cmput301f18t09.cureall;

import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.model.Problem;

import org.junit.Test;

import java.util.ArrayList;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PatientTest {

    @Test
    //test if successfully added problem
    public void testAddProblem() {
        Problem problem = new Problem("username","title","description","time","comment");
        Patient patient = new Patient("username", "password", "email", "phone");
        patient.addProblem(problem);
        assertTrue(patient.hasProblem(problem));

    }
    @Test
    //test if successfully deleted problem
    public void testDeleteProblem() {
        Problem problem = new Problem("username","title","description","time","comment");
        Patient patient = new Patient("username", "password", "email", "phone");
        patient.addProblem(problem);
        patient.deleteProblem(problem);
        assertFalse(patient.hasProblem(problem));

    }
    @Test
    //test if successfully edited problem
    public void testEditProblem() {
        Problem problem = new Problem("username","title","description","time","comment");
        Patient patient = new Patient("username", "password", "email", "phone");

        patient.addProblem(problem);
        ArrayList<Problem> problemlist = patient.getProblemArrayList();

        Problem newProblem = new Problem("username","title","description","time","comment");

        patient.editProblem(problem,newProblem);

        assertTrue(patient.hasProblem(newProblem));     //now new problem exists instead of old problem
        assertFalse(patient.hasProblem(problem));
    }
}
