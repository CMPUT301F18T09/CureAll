package com.example.cmput301f18t09.cureall;

import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderMainPageActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;

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


public class ProviderLoginTest {

    @Rule
    public IntentsTestRule<MainActivity> mainIntentRule = new IntentsTestRule<>(MainActivity.class);
    @Before
    public void SetUp(){
        onView(withId(R.id.ProviderLogin)).perform(click());
    }
    @Test
    public void providerLoginTest(){
        onView(withId(R.id.userNameInput)).perform(typeText("chenfangpei"),closeSoftKeyboard());
        onView(withId(R.id.passwordInputpass)).perform(typeText("1"),closeSoftKeyboard());
        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(ProviderMainPageActivity.class.getName()));
    }
}
