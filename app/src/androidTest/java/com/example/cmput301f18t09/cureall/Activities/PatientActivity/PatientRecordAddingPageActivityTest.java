package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;

import com.example.cmput301f18t09.cureall.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class PatientRecordAddingPageActivityTest {
    @Rule
    public ActivityTestRule<PatientRecordAddingPageActivity> PRAP = new ActivityTestRule<>(PatientRecordAddingPageActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void addRecordDetail(){
        Espresso.onView(ViewMatchers.withId(R.id.titleInput)).perform(typeText("connor"), closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.descriptionInput)).perform(typeText("cpass"), closeSoftKeyboard());

    }

    @Test
    public void addRecord(){
        onView(withId(R.id.saveButton)).perform(click());

    }

    @Test
    public void addBodyLoc(){
        onView(withId(R.id.bodyLocationSelectButton)).perform(click()); }

    @Test
    public void addPhoto(){
        onView(withId(R.id.fromAlbumButton)).perform(click());
        onView(withId(R.id.cameraButton)).perform(click());
    }

    @Test
    public void addLoction(){
        onView(withId(R.id.geoLocationButton)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }
}