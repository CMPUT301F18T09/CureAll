package com.example.cmput301f18t09.cureall;

import android.util.Log;

import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ElasticSearchControllerTest {

    @Test
    public void PatientEsTest()
    {
        Patient patient = new Patient("TestPatient","testmail","testPh");
        PatientController.AddPatient(patient);
        Patient back_patient = PatientController.GetNumPatients(patient.getUsername()).get(0);

        // Test add patient and delete patient
        assertEquals(patient.getUsername(),back_patient.getUsername());
        assertEquals(patient.getEmail(),back_patient.getEmail());
        assertEquals(patient.getPhone(),back_patient.getPhone());

    }

    @Test
    public void RecordEsTest()
    {
        Record record = new Record("Test Record","Test comment", new Date());
        record.setUsername("TestUser");
        record.setProblemid("Testid");
        RecordController.AddRecord(record);
        try
        {
            Thread.currentThread().sleep(1000);//毫秒
        }
        catch(Exception e){}
        Record back_record = RecordController.GetRecordNum("TestUser","Testid").get(0);
        // Test add record and get record from es
        assertEquals(record.getProblemid(),back_record.getProblemid());
        // Test delete Record from es
        for(Record each:RecordController.GetRecordNum("TestUser","Testid"))
        {
            RecordController.DeleteRecord(each);
            try
            {
                Thread.currentThread().sleep(1000);//毫秒
            }
            catch(Exception e){}
        }
        ArrayList<Record> back_record1 = RecordController.GetRecordNum("TestUser","Testid");
        assertEquals(new ArrayList<>(),back_record1);
    }


    @Test
    public  void ProblemEsTest()
    {
        Problem problem = new Problem("TestUser","Test Title", "Test Desc", "Test Time","Test comm");
        ProblemController.AddProblem(problem,problem.getUsername());
        try
        {
            Thread.currentThread().sleep(1000);//毫秒
        }
        catch(Exception e){}
        Problem back_problem = ProblemController.GetProblemNum("TestUser").get(0);
        //Test add and get Problem from es
        assertEquals(problem.time,back_problem.time);

        //Test delete problem
        for(Problem each:ProblemController.GetProblemNum("TestUser"))
        {
            ProblemController.DelteProblem(each);
            try
            {
                Thread.currentThread().sleep(1000);//毫秒
            }
            catch(Exception e){}
        }
        ArrayList<Problem> back_prob = ProblemController.GetProblemNum("TestUser");
        assertEquals(new ArrayList<>(),back_prob);

    }


}