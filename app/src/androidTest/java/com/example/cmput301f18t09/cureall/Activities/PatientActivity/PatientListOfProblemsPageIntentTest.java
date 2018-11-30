package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientListOfProblemsPageActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.UserLoginActivity;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.R;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class PatientListOfProblemsPageIntentTest {

    @Rule
    public IntentsTestRule<MainActivity> patientListOfProblemsPageActivityIntentsTestRule
            = new IntentsTestRule<>(MainActivity.class);
    @Before
    public void SetUp(){
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
        onView(withId(R.id.userNameInput)).perform(typeText("u9"),closeSoftKeyboard());
        onView(withId(R.id.passwordInputpass)).perform(typeText("1"),closeSoftKeyboard());
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
    public void problemAddingTest(){
        onView(withId(R.id.problemAddingButton)).perform(click());
        intended(hasComponent(PatientProblemAddingPageActivity.class.getName()));

    }

}