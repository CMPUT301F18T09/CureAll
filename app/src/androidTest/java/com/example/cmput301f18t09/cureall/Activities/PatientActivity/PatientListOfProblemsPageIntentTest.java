package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemListPageAdapter;
import com.example.cmput301f18t09.cureall.R;

import org.hamcrest.CustomMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.security.PublicKey;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnHolderItem;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PatientListOfProblemsPageIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> patientListOfProblemsPageActivityIntentsTestRule
            = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void SetUp() {
/*        Patient patient = new Patient("u9","1","1");
        Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences2 = targetConxet.getSharedPreferences("LoginData",targetConxet.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(patient);*//**save in gson format*//*
        String json2 = gson.toJson(patient.getProblemArrayList());
        editor2.putString("patientObject",json);
        editor2.putString("patientProblems",json2);


        intent.putExtra("ComeFromLogin", "ComeFromLogin");
        patientListOfProblemsPageActivityIntentsTestRule.launchActivity(intent);*/
        onView(withId(R.id.patientLogin)).perform(click());
        onView(withId(R.id.userNameInput)).perform(typeText("u9"), closeSoftKeyboard());
        onView(withId(R.id.passwordInputpass)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        // Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //Patient patient = new Patient("u9","1","1");
        //UserLoginActivity userLoginActivity = new UserLoginActivity();


        //Intent intent = new Intent(targetConxet,PatientListOfProblemsPageActivity.class);
        //intent.putExtra("ComeFromLogin", "ComeFromLogin");
        //userLoginActivity.passDataToPatient(patient,patient.getProblemArrayList());
        /**ends
         *
         */
        // patientListOfProblemsPageActivityIntentsTestRule.launchActivity(intent);
    }

    @Test
    public void problemAddingTest() {
        onView(withId(R.id.problemAddingButton)).perform(click());
        intended(hasComponent(PatientProblemAddingPageActivity.class.getName()));

    }

    @Test
    public void problemDetailTest() {


        //onView((withId(R.id.listOfProblems))).perform(RecyclerViewActions.scrollToPosition(0), actionOnHolderItem(withTitle(2131296590),click()));
        onView((withId(R.id.listOfProblems))).perform(RecyclerViewActions.scrollToPosition(0),click());

    }

        public static Matcher<RecyclerView.ViewHolder> withTitle(final int title)
        {
            return new BoundedMatcher<RecyclerView.ViewHolder, PatientProblemListPageAdapter.viewHolder>(PatientProblemListPageAdapter.viewHolder.class)
            {
                @Override
                protected boolean matchesSafely(PatientProblemListPageAdapter.viewHolder item)
                {
                    return (int)item.problemDetailButton.getId()== title;
                }

                @Override
                public void describeTo(Description description)
                {
                    description.appendText("view holder with title: " + title);
                }
            };
        }
}