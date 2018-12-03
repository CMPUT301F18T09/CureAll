package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;

import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderAddPatientActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderMainPageActivity;

import org.junit.After;
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

public class ProviderMainPageIntentTest {

    @Rule
    public IntentsTestRule<ProviderMainPageActivity> patientProblemDetailPageActivityIntentsTestRule
            = new IntentsTestRule<>(ProviderMainPageActivity.class,
            false,
            false);

    @Before
    public void before() {
 /*       Context targetConxet = InstrumentationRegistry.getInstrumentation().getTargetContext();
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
        passDataToRecordDetailPage(record);*/
        Intent intent= new Intent();
        intent.putExtra("username","wade");
        patientProblemDetailPageActivityIntentsTestRule.launchActivity(intent);

    }

    @Test
    public void AddPatientTest() {
        // Test switch to ProviderAddPatientActivity
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.addPatientButton)).perform(click());
        intended(hasComponent(ProviderAddPatientActivity.class.getName()));
        // Test add patient function
        onView(withId(R.id.input_patient)).perform(typeText("u9"),closeSoftKeyboard());
        onView(withId(R.id.add_button));
        intended(hasComponent(ProviderMainPageActivity.class.getName()));


    }

    @Test
    public void SearchTest() {
        Espresso.closeSoftKeyboard();
       // onView((withId(R.id.))).perform(click());
       // intended(hasComponent(PatientPhotoFlowPageActivity.class.getName()));


    }
    @After
    public void after()
    {

    }


}