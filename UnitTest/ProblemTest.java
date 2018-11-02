package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class ProblemTest {

    @Test
    public void testGetTitle(){
        Problem problem = new Problem("title","description",new Date());
        String title = problem.getTitle();
        assertEquals(title,"title");
    }

    public void testSetTitle(){
        Problem problem = new Problem("title","description",new Date());
        String title = problem.setTitle("newTitle");
        assertEquals(title,"newTitle");
    }
}
