package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientListOfProblemsPageActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;
import com.example.cmput301f18t09.cureall.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


public class ProviderSignUpIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> mainIntentRule = new IntentsTestRule<>(MainActivity.class);
    @Before
    public void SetUp(){
        onView(withId(R.id.patientSignUp)).perform(click());
    }
    @Test
    public void patientLoginTest(){
        onView(withId(R.id.userNameInput)).perform(typeText("u9"),closeSoftKeyboard());
        onView(withId(R.id.passwordInputpass)).perform(typeText("1"),closeSoftKeyboard());
        onView(withId(R.id.confirmPasswodInput)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.emailAddressInput)).perform(typeText("1"), closeSoftKeyboard());
        onView(withId(R.id.phoneNumberInput)).perform(typeText("1"),closeSoftKeyboard());
        onView(withId(R.id.continueButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

}