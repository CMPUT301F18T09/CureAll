package com.example.cmput301f18t09.cureall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.ViewLocationOnMapActivity;
import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.model.Record;
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
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PatientProblemDetailPageIntentTest {


    @Rule
    public IntentsTestRule<PatientProblemDetailPageActivity> patientProblemDetailPageActivityIntentsTestRule
            = new IntentsTestRule<>(PatientProblemDetailPageActivity.class,
                                                        false,
                                                        false);


    @Before
    public void before() {

//        PatientListOfProblemsPageActivity patientListOfProblemsPageActivity = new PatientListOfProblemsPageActivity();
//        patientListOfProblemsPageActivity.passDataToProblemDetail(problem,records,problems,patient);
        ProblemController problemController = new ProblemController();
        RecordController recordController = new RecordController();
       // Patient patient = new Patient("fangpeich","1","1");
        Patient patient = PatientController.GetNumPatients("fangpeich").get(0);
        ArrayList<Problem> problems = problemController.GetProblemNum(patient.getUsername());
        Problem problem = new Problem(patient.getUsername()
                ,"1"
                ,"1"
                , DateFormat.getDateTimeInstance().format(new Date())
                , "1");
        ProblemController.AddProblem(problem,patient.getUsername());
        ArrayList<Record> records = recordController.GetRecordNum(patient.getUsername()
                ,problemController.GetProblemNum(patient.getUsername()).get(0).getId());
        Record record = new Record("1","1",new Date());
        records.add(record);
        passDataToProblemDetail(problem,records,problems,patient);
        Intent intent= new Intent();
        intent.putExtra("ComeFromPatientMainPage","ComeFromPatientMainPage");
        patientProblemDetailPageActivityIntentsTestRule.launchActivity(intent);

    }

/*    @Test
    public void ViewBodyPhotoTest() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.geoLocationButton)).perform(click());
        intended(hasComponent(ViewLocationOnMapActivity.class.getName()));

    }*/

    @Test
    public void RecordDetailTest() {
        Espresso.closeSoftKeyboard();
        onView((withId(R.id.listOfRecords))).perform(RecyclerViewActions.scrollToPosition(0),click(),closeSoftKeyboard());


    }
    @After
    public void after()
    {

    }
    public void passDataToProblemDetail(Problem problem, ArrayList<Record> records, ArrayList<Problem> problems, Patient patient){
        Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences2 = targetConxet.getSharedPreferences("PatientMainPageData",targetConxet.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        String json2 = gson.toJson(records);
        String json3 = gson.toJson(problems);
        String json4 = gson.toJson(patient);
        editor2.putString("problem",json);
        editor2.putString("records",json2);
        editor2.putString("problems",json3);
        editor2.putString("patient",json4);
        editor2.apply();
    }

}