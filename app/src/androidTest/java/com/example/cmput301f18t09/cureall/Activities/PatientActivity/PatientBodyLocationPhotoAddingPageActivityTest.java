package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

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

public class PatientBodyLocationPhotoAddingPageActivityTest {

    @Rule
    public ActivityTestRule<PatientBodyLocationPhotoAddingPageActivity> PBLPAP = new ActivityTestRule<>(PatientBodyLocationPhotoAddingPageActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void addBodyLocation(){
        onView(withId(R.id.frontPhotoButton)).perform(click());
        onView(withId(R.id.backPhotoButton)).perform(click());
        onView(withId(R.id.saveButton)).perform(click());
    }

    @Test
    public void addMoreBodyLocation(){
        onView(withId(R.id.cameraButton)).perform(click());
        onView(withId(R.id.saveButton)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}