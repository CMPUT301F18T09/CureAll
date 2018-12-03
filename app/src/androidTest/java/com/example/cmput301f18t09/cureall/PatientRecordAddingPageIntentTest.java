package com.example.cmput301f18t09.cureall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientPaperDollSelectionPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientRecordAddingPageActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.LocationPickerActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.ViewLocationOnMapActivity;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
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

public class PatientRecordAddingPageIntentTest {
    @Rule
    public IntentsTestRule<PatientRecordAddingPageActivity> patientProblemDetailPageActivityIntentsTestRule
            = new IntentsTestRule<>(PatientRecordAddingPageActivity.class,
            false,
            false);

    @Before
    public void before() {
        ProblemController problemController = new ProblemController();
        RecordController recordController = new RecordController();
        Patient patient = new Patient("qazwsxed","1","1");
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
    public void PickLocationIntentTest() {
        Espresso.closeSoftKeyboard();
        onView((withId(R.id.geoLocationSelectButton))).perform(click());
        intended(hasComponent(LocationPickerActivity.class.getName()));


    }


    @Test
    public void PickLocationTest() {
        Espresso.closeSoftKeyboard();
        onView((withId(R.id.geoLocationSelectButton))).perform(click());
        intended(hasComponent(LocationPickerActivity.class.getName()));
 /*       Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
        Place place = new Place() {
            @Override
            public String getId() {
                return null;
            }

            @Override
            public List<Integer> getPlaceTypes() {
                return null;
            }

            @Override
            public CharSequence getAddress() {
                return null;
            }

            @Override
            public Locale getLocale() {
                return null;
            }

            @Override
            public CharSequence getName() {
                return null;
            }

            @Override
            public LatLng getLatLng() {
                return null;
            }

            @Override
            public LatLngBounds getViewport() {
                return null;
            }

            @Override
            public Uri getWebsiteUri() {
                return null;
            }

            @Override
            public CharSequence getPhoneNumber() {
                return null;
            }

            @Override
            public float getRating() {
                return 0;
            }

            @Override
            public int getPriceLevel() {
                return 0;
            }

            @Override
            public CharSequence getAttributions() {
                return null;
            }

            @Override
            public Place freeze() {
                return null;
            }

            @Override
            public boolean isDataValid() {
                return false;
            }
        };
        // Build a result to return from the Camera app
        Intent resultData = new Intent();
        resultData.putExtra("data", (Serializable) place);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // Stub out the Camera. When an intent is sent to the Camera, this tells Espresso to respond
        // with the ActivityResult we just created
        intending(toPackage("com.android.camera2")).respondWith(result);

        // Now that we have the stub in place, click on the button in our app that launches into the Camera
        onView(withId(R.id.GetCurrentLocation)).perform(click());

        // We can also validate that an intent resolving to the "camera" activity has been sent out by our app
        intended(toPackage("com.android.camera2"));*/

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