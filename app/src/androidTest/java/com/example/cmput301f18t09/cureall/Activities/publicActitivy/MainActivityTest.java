package com.example.cmput301f18t09.cureall.Activities.publicActitivy;


//import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.ActivityTestRule;

import com.example.cmput301f18t09.cureall.R;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;



public class MainActivityTest {
    private static final String MESSAGE = "patient";
    private static final String patientname = "connor";
    private static final String patientpwd = "cpass";

    ////

    @Rule
    public ActivityTestRule<MainActivity> mIntentsRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void goSignup(){

        onView(withId(R.id.ProviderSignUp)).perform(click());


        onView(withId(R.id.patientSignUp)).perform(click());

    }

    @Test
    public void goLogin(){

        onView(withId(R.id.patientLogin)).perform(click());


        onView(withId(R.id.ProviderLogin)).perform(click());

        }

    @After
    public void tearDown() throws Exception {
    }
}