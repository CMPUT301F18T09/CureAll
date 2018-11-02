package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertTrue;

public class ProblemTest {

    public void testAddRecord(){
        Problem problem = new Problem("title","description",new Date());
        Record record = new Record("record","comment",new Date());
        problem.addRecord(record);
        assertTrue(problem.getRecord(record));
    }
}
