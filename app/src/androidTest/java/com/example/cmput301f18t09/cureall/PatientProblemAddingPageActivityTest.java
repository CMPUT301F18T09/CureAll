package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.app.Activity;
import android.view.View;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemAddingPageActivity;
import com.example.cmput301f18t09.cureall.R;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class PatientProblemAddingPageActivityTest {

    @Rule
    public ActivityTestRule<PatientProblemAddingPageActivity> pProblemTestRule = new ActivityTestRule<PatientProblemAddingPageActivity>(PatientProblemAddingPageActivity.class);

    private PatientProblemAddingPageActivity pProblem = null;

    @Before
    public void setUp() throws Exception {

        pProblem = pProblemTestRule.getActivity();
    }

    @Test
    public void TestLaunch(){

        View view = pProblem.findViewById( R.id.tvMainText );

        assertNotNull( view );
    }

    @After
    public void tearDown() throws Exception {

        pProblem = null;
    }
}