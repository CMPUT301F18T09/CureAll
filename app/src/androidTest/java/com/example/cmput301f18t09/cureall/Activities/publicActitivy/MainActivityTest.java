package com.example.cmput301f18t09.cureall.Activities.publicActitivy;


//import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.ActivityTestRule;

import com.example.cmput301f18t09.cureall.R;


import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.intent.rule.IntentsTestRule;

import static android.provider.ContactsContract.Directory.PACKAGE_NAME;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.matcher.ViewMatchers.withId;



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