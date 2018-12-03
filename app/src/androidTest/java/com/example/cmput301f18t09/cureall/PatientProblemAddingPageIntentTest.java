package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemAddingPageActivity;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.model.Problem;

import org.junit.Before;
import org.junit.Rule;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PatientProblemAddingPageIntentTest {
    @Rule
    public IntentsTestRule<PatientProblemAddingPageActivity> patientProblemDetailPageActivityIntentsTestRule
            = new IntentsTestRule<>(PatientProblemAddingPageActivity.class,
            false,
            false);

    @Before
    public void before() {
        Patient patient = new Patient("u9","1","1");
        ProblemController problemController = new ProblemController();
        ArrayList<Problem> problems = problemController.GetProblemNum(patient.getUsername());
        Problem problem = new Problem(patient.getUsername()
                ,"1"
                ,"1"
                , DateFormat.getDateTimeInstance().format(new Date())
                , "1");
        ProblemController.AddProblem(problem,patient.getUsername());
        //passDataToProblemAdding(patient.getUsername(),problems);
        Intent intent= new Intent();
        intent.putExtra("ComeFromPatientMainPage", "ComeFromPatientMainPage");
        patientProblemDetailPageActivityIntentsTestRule.launchActivity(intent);

    }

 /*   @Test
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


    public void passDataToProblemAdding(String username, ArrayList<Problem> problems){
        Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences2 = targetConxet.getSharedPreferences("PatientMainPageData",targetConxet.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(username);*//**save in gson format*//*
        String json2 = gson.toJson(problems);
        editor2.putString("username",json);
        editor2.putString("patientProblems",json2);
        editor2.apply();
    }*/
}