package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;


import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class PatientTest {

    @Test
    public void testAddProblem() {
        Problem problem = new Problem("username","title","description","time","comment");
        Patient patient = new Patient("username", "password", "email", "phone");
        patient.addProblem(problem);
        ArrayList<Problem> problems = patient.getProblemArrayList();
        assertTrue(patient.hasProblem(problem));

    }
    @Test
    public void testDeleteProblem() {
        Problem problem = new Problem("username","title","description","time","comment");
        Patient patient = new Patient("username", "password", "email", "phone");
        patient.addProblem(problem);
        patient.deleteProblem(problem);
        assertFalse(patient.hasProblem(problem));

    }
    @Test
    public void testEditProblem() {
        Problem problem = new Problem("username","title","description","time","comment");
        Patient patient = new Patient("username", "password", "email", "phone");
        patient.addProblem(problem);
        ArrayList<Problem> problemlist = patient.getProblemArrayList();

        Problem newProblem = new Problem("username","title","description","time","comment");

        patient.editProblem(problem,newProblem);

        assertTrue(patient.hasProblem(newProblem));
        assertFalse(patient.hasProblem(problem));
    }
}
