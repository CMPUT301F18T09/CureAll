package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;
import com.example.cmput301f18t09.cureall.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class PatientListOfProblemsPageActivityTest {

    public ActivityTestRule<PatientListOfProblemsPageActivity> PLOPP = new ActivityTestRule<>(PatientListOfProblemsPageActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void search(){
        onView(withId(R.id.searchButton)).perform(click());
    }

    @Test
    public void addProblem(){
        onView(withId(R.id.problemAddingButton)).perform(click());
    }

    @Test
    public void SelectProblem(){
        onView(withId(R.id.problemDetailButton)).perform(click());
    }

    @Test
    public void DeleteProblem(){
        onView(withId(R.id.problemDeleteButton)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}