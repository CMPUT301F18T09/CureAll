package com.example.cmput301f18t09.cureall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientPhotoFlowPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientRecordDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.ViewLocationOnMapActivity;
import com.example.cmput301f18t09.cureall.model.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.model.BodyLocation;
import com.example.cmput301f18t09.cureall.model.GeoLocation;
import com.example.cmput301f18t09.cureall.model.Record;
import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class PatientRecordDetailPageIntentTest {

    @Rule
    public IntentsTestRule<PatientRecordDetailPageActivity> patientProblemDetailPageActivityIntentsTestRule
            = new IntentsTestRule<>(PatientRecordDetailPageActivity.class,
            false,
            false);

    @Before
    public void before() {
        Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
        GeoLocation geoLocation = new GeoLocation(35.15,69.72);
        Bitmap bitmap = BitmapFactory.decodeResource(targetConxet.getResources(),R.drawable.male_background);
        AllKindsOfPhotos allKindsOfPhotos = new AllKindsOfPhotos(bitmap,"","type",0.0,0.0,0.0);
        ArrayList<AllKindsOfPhotos> allKindsOfPhotos1 = new ArrayList<AllKindsOfPhotos>();
        allKindsOfPhotos1.add(allKindsOfPhotos);
        BodyLocation bodyLocation = new BodyLocation("neck",allKindsOfPhotos1);
        Record record = new Record("1","1",new Date());
        record.setBodyLocation(bodyLocation);
        record.setGeoLocation(geoLocation);
        record.setRecordTrackingPhotoArrayList(new ArrayList<AllKindsOfPhotos>());
        passDataToRecordDetailPage(record);
        Intent intent= new Intent();
        intent.putExtra("ComeFromProblemDetail","ComeFromProblemDetail");
        patientProblemDetailPageActivityIntentsTestRule.launchActivity(intent);

    }

    @Test
    public void ViewGeolocationTest() {
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.geoLocationButton)).perform(click());
        intended(hasComponent(ViewLocationOnMapActivity.class.getName()));

    }

    @Test
    public void RecordDetailTest() {
        Espresso.closeSoftKeyboard();
        onView((withId(R.id.viewBodyLocationPhotoButton))).perform(click());
        intended(hasComponent(PatientPhotoFlowPageActivity.class.getName()));


    }
    @After
    public void after()
    {

    }


    public void passDataToRecordDetailPage(Record record){
        Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
        SharedPreferences sharedPreferences2 = targetConxet.getSharedPreferences("ProblemDetailData",targetConxet.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(record);
        editor2.putString("record",json);
        editor2.apply();
    }
}