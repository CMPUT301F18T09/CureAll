package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.LocationPickerActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.ViewLocationOnMapActivity;
import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.GeoLocation;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

public class PatientRecordAddingPageIntentTest {
    @Rule
    public IntentsTestRule<PatientRecordAddingPageActivity> patientProblemDetailPageActivityIntentsTestRule
            = new IntentsTestRule<>(PatientRecordAddingPageActivity.class,
            false,
            false);

    @Before
    public void before() {
        Patient patient = new Patient ("u9","1","1");
        ArrayList<Problem> problems = ProblemController.GetProblemNum(patient.getUsername());
        Problem problem = new Problem(patient.getUsername()
                ,"1"
                ,"1"
                , DateFormat.getDateTimeInstance().format(new Date())
                , "1");
        ProblemController.AddProblem(problem,patient.getUsername());
        ArrayList<Record> records = RecordController.GetRecordNum(patient.getUsername()
                ,ProblemController.GetProblemNum(patient.getUsername()).get(0).getId());
        Record record = new Record("1","1",new Date());
        records.add(record);
        passDataToAddingRecordPage(problem,problems,patient,records);
        Intent intent= new Intent();
        intent.putExtra("ComeFromProblemDetail","ComeFromProblemDetail");
        patientProblemDetailPageActivityIntentsTestRule.launchActivity(intent);

    }

    @Test
    public void chooseBodyLocationTest() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.bodyLocationSelectButton)).perform(click());
        intended(hasComponent(PatientPaperDollSelectionPageActivity.class.getName()));

    }

    @Test
    public void PickLocationTest() {
        Espresso.closeSoftKeyboard();
        onView((withId(R.id.geoLocationSelectButton))).perform(click());
        intended(hasComponent(LocationPickerActivity.class.getName()));


    }
    @After
    public void after()
    {

    }


    public void passDataToAddingRecordPage(Problem problem, ArrayList<Problem> problems, Patient patient, ArrayList<Record> records){
        Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences2 = targetConxet.getSharedPreferences("ProblemDetailData",targetConxet.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        String json2 = gson.toJson(problems);
        String json3 = gson.toJson(patient);
        String json4 = gson.toJson(records);
        editor2.putString("problem",json);
        editor2.putString("problems",json2);
        editor2.putString("patient",json3);
        editor2.putString("records",json4);
        editor2.apply();
    }
}