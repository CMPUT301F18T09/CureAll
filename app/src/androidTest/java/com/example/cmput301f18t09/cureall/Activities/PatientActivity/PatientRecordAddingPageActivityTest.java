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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class PatientRecordAddingPageActivityTest {
    @Rule
    public ActivityTestRule<PatientRecordAddingPageActivity> PRAP = new ActivityTestRule<>(PatientRecordAddingPageActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void addRecordDetail(){
        Espresso.onView( withId(R.id.titleInput)).perform(typeText("connor"), closeSoftKeyboard());
        Espresso.onView( withId(R.id.descriptionInput)).perform(typeText("cpass"), closeSoftKeyboard());

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
    public void addLocation(){
        onView(withId(R.id.geoLocationButton)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
    }



/*    public class ActivityAddRecordIntentTest {

        @Rule
        public IntentsTestRule<ActivityAddRecord> intentsTestRule =
                new IntentsTestRule<>(ActivityAddRecord.class, false, false);

        @Before
        // create mock intent with mock patient and mock problem
        public void before() {
            Patient p = new Patient("Test Patient", "test@example.com", "555-555-1234", "Patient");
            Problem pr = new Problem("Test Problem", new DatePickerFragment(), "Test Problem's Description");
            p.getProblems().add(pr);

            Intent intent = new Intent();
            intent.putExtra("Patient", p);
            intent.putExtra("Position", 0);
            intent.putExtra("Flag", "a");
            intentsTestRule.launchActivity(intent);
        }

        @Test
        public void TestResultFromActivity() {
            String testRecordTitle = "Record Title";
            String testRecordComment = "Record Comment";

            onView(withId(R.id.record_title_text))
                    .perform(typeText(testRecordTitle), closeSoftKeyboard());
            onView(withId(R.id.record_comment_text))
                    .perform(typeText(testRecordComment), closeSoftKeyboard());
            onView(withId(R.id.record_create_button))
                    .perform(click());
            assertThat(intentsTestRule.getActivityResult(), hasResultCode(Activity.RESULT_OK));
            assertThat(intentsTestRule.getActivityResult(),
                    hasResultData(IntentMatchers.hasExtraWithKey("Record")));
        }
    }*/
}