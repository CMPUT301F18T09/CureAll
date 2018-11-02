package com.example.cureall;

import  org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

import static junit.framework.TestCase.assertTrue;

import static junit.framework.TestCase.assertFalse;

public class PatientTest {

    @Test
    public void testAddProblem() {
        Problem problem = new Problem("title","description",new Date());
        Patient patient = new Patient("patientId");
        patient.addProblem(problem);
        assertTrue(patient.hasProblem(problem));
    }

    public void testDeleteProblem() {
        Problem problem = new Problem("title","description",new Date());
        Patient patient = new Patient("patientId");
        patient.addProblem(problem);
        patient.deleteProblem(problem);
        assertTrue(patient.hasProblem(problem));
    }

    public void testEditProblem() {
        Problem problem = new Problem("title","description",new Date());
        Patient patient = new Patient("patientId");
        patient.addProblem(problem);
        Problem newProblem = new Problem("newTitle","newDescription",new Date());
        patient.editProblem(problem,newProblem);
        assertTrue(patient.hasProblem(newProblem));
        assertFalse(patient.hasProblem(Problem));
    }
}
