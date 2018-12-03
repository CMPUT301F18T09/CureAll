package com.example.cmput301f18t09.cureall;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientListOfProblemsPageActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class PatientLoginTest {
    @Rule
    public IntentsTestRule<MainActivity> mainIntentRule = new IntentsTestRule<>(MainActivity.class);
    @Before
    public void SetUp(){
        onView(withId(R.id.patientLogin)).perform(click());
    }
    @Test
    public void patientLoginTest(){
        onView(withId(R.id.userNameInput)).perform(typeText("u9"),closeSoftKeyboard());
        onView(withId(R.id.passwordInputpass)).perform(typeText("1"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(PatientListOfProblemsPageActivity.class.getName()));
    }
}