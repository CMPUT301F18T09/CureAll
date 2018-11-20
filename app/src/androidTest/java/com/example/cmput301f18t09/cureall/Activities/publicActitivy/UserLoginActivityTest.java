package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class UserLoginActivityTest {

    @Rule
    public ActivityTestRule<UserLoginActivity> userIntentsRule = new ActivityTestRule<>(UserLoginActivity.class);

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void PatientLogin(){
        onView(withId(R.id.userNameInput)).perform(typeText("connor"), closeSoftKeyboard());
        onView(withId(R.id.passwordInputpass)).perform(typeText("cpass"), closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
    }


    @After
    public void tearDown() throws Exception {
    }
}