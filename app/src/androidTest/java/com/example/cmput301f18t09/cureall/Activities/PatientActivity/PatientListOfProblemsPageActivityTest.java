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

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
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

/*
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;

    public class ActivityBrowseProblemsIntentTest {

        @Rule
        public IntentsTestRule<ActivityBrowseProblems> intentsTestRule =
                new IntentsTestRule<>(ActivityBrowseProblems.class, false, false);

        @Before
        // create mock patient with mock problem
        public void setup() {
            Problem pr = new Problem("Test Problem", new DatePickerFragment(), "Test Problem's Description");
            Patient p = new Patient("Test Patient", "test@example.com", "555-555-1234", "Patient");
            p.getProblems().add(pr);
            Intent i = new Intent();
            i.putExtra("Patient", p);
            intentsTestRule.launchActivity(i);
        }

        @Test
        public void TestViewProblemFromBrowse() {
            onData(anything()).inAdapterView(withId(R.id.ProblemList)).atPosition(0)
                    .perform(click());
            intended(hasComponent(ActivityViewProblem.class.getName()));
        }

        @Test
        public void TestAddProblemFromBrowse() {
            onView(withId(R.id.addProblemButton))
                    .perform(click());
            intended(hasComponent(ActivityAddProblem.class.getName()));
        }

        @Test
        public void TestSearchProblemFromBrowse() {
            onView(withId(R.id.searchButton))
                    .perform(click());
            intended(hasComponent(ActivitySearch.class.getName()));
        }

        @Test
        public void TestViewProfileFromBrowse() {
            onView(withId(R.id.usernameText))
                    .perform(click());
            intended(hasComponent(ActivityUserProfile.class.getName()));
        }
*/

    }