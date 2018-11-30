package com.example.cmput301f18t09.cureall.Activities.publicActitivy;


import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientSignUpActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderSignUpActivity;
import com.example.cmput301f18t09.cureall.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    //public IntentsTestRule<MainActivity> mIntentsRule = new IntentsTestRule<>(MainActivity.class,false,false);
    public IntentsTestRule<MainActivity> mIntentsRule = new IntentsTestRule<>(MainActivity.class);

/*    @Before
    public void before() {
        mIntentsRule.launchActivity(new Intent());
        // waitForUserListUpdate();
    }*/
    @Test
    public void patientSignUpTest(){
        onView(withId(R.id.patientSignUp)).perform(click());
        intended(hasComponent(PatientSignUpActivity.class.getName()));
    }

    @Test
    public void providerSignUpTest(){
        onView(withId(R.id.ProviderSignUp)).perform(click());
        intended(hasComponent(ProviderSignUpActivity.class.getName()));
    }
    @Test
    public void patientLoginTest(){
        onView(withId(R.id.patientLogin)).perform(click());
        intended(hasComponent(UserLoginActivity.class.getName()));
    }

    @Test
    public void providerLoginTest(){
        onView(withId(R.id.ProviderLogin)).perform(click());
        intended(hasComponent(UserLoginActivity.class.getName()));
    }

}