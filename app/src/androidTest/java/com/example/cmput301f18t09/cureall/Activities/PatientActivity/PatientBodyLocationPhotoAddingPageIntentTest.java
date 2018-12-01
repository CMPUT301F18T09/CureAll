package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.LocationPickerActivity;
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

public class PatientBodyLocationPhotoAddingPageIntentTest {
    @Rule
    public IntentsTestRule<PatientPaperDollSelectionPageActivity> patientProblemDetailPageActivityIntentsTestRule
            = new IntentsTestRule<>(PatientPaperDollSelectionPageActivity.class,
            false,
            false);

    @Before
    public void before() {
  /*      Patient patient = new Patient ("u9","1","1");
        ArrayList<Problem> problems = ProblemController.GetProblemNum(patient.getUsername());
        Problem problem = new Problem(patient.getUsername()
                ,"1"
                ,"1"
                , DateFormat.getDateTimeInstance().format(new Date())
                , "1");
        ProblemController.AddProblem(problem,patient.getUsername());
        ArrayList<Record> records = RecordController.GetRecordNum(patient.getUsername()
                ,ProblemController.GetProblemNum(patient.getUsername()).get(0).getId());*/
        Record record = new Record("1","1",new Date());
/*        records.add(record);
        passDataToAddingRecordPage(problem,problems,patient,records);*/
        passDataToRecordAddingPage(record);
        Intent intent= new Intent();
        intent.putExtra("ComeFromRecordAddingPage","ComeFromRecordAddingPage");
        patientProblemDetailPageActivityIntentsTestRule.launchActivity(intent);

    }

    @Test
    public void chooseBodyLocationTest() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.paperDoll)).perform(click());
        //intended(hasComponent(PatientPaperDollSelectionPageActivity.class.getName()));

    }

    @After
    public void after()
    {

    }


    public void passDataToRecordAddingPage(Record record){
        Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences2 = targetConxet.getSharedPreferences("BodyLocationPhotoAddingData",targetConxet.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(record);/**save in gson format*/
        editor2.putString("record",json);
        editor2.apply();
    }
}