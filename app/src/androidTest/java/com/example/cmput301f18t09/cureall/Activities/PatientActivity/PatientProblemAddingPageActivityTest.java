package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.test.rule.ActivityTestRule;
import android.view.View;

import com.example.cmput301f18t09.cureall.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.security.PublicKey;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import android.app.Activity;
import android.support.test.espresso.intent.matcher.IntentMatchers;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultCode;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.ActivityResultMatchers.hasResultData;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith( AndoridJUnit4.class )
public class PatientProblemAddingPageActivityTest {

    @Rule
    public ActivityTestRule<PatientProblemAddingPageActivity> PPAP = new ActivityTestRule<>(PatientProblemAddingPageActivity.class);


    private PatientProblemAddingPageActivity mPPAP = null;

    @Before
    public void setUp() throws Exception {
        mPPAP = PPAP.getActivity();
    }

    @Test
    public void lunch(){
        View view = mPPAP.findViewById(R.id.maxLength30);
        assertNotNull( view );
    }
    @Test
    public void PatientLogin(){
        onView(withId(R.id.titleInput)).perform(typeText("connor"), closeSoftKeyboard());
        onView(withId(R.id.descriptionInput)).perform(typeText("cpass"), closeSoftKeyboard());
        onView(withId(R.id.saveButton)).perform(click());

       /* assertThat(intentsTestRule.getActivityResult(), hasResultCode(Activity.RESULT_OK));
        assertThat(intentsTestRule.getActivityResult(),
                hasResultData(IntentMatchers.hasExtraWithKey("Problem")));*/

    }

    @Test
    public void clickAddProblemButton()throws Exception{
        onView( withId(  R.id.maxLength30) ).perform(click());
        onView( withId( R.id.cast_notification_id )).check(matches(isDisplayed()));
    }



    @After
    public void tearDown() throws Exception {
        mPPAP = null;
    }
}
