/**
 * Class name: PatientPaperDollSelectionPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.PaperDollController.*;

import com.example.cmput301f18t09.cureall.PaperDollController.BodyColor;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;

/**
 * For this activity, user(patient) can select from paper doll to define a body location
 * This Activity provides user a paper doll photo
 * to choose a body part, such as arm, head, neck, back....
 */
public class PatientPaperDollSelectionPageActivity extends AppCompatActivity {
    private ImageView paperDoll,paperDollColorDivide;
    private TextView femaleText, maleText;
    private Switch switch1;
    private Problem problem;
    private ArrayList<Problem> problems;
    private Record record;
    private Patient patient;
    private BodyLocation bodyLocation;
    private ArrayList<Record> records;

    final int REQUEST_BODY = 2;
    /**
     * initialize some buttons/ image buttons from its relative xml file
     * that allows the user to click from the image
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_paper_doll_selection_page);
        initializeAllElements();

        paperDoll.setOnTouchListener(touchListener);
    }

    /**
     * set init value for elements used in this activity
     * (or give reference)
     *  including buttons, textviews,  patients, records, problems
     *  There is still a problem, which is the gender can not be switch right now.
     *  The two gender will be presented in next project
     */
    public void initializeAllElements(){
        paperDoll =findViewById(R.id.paperDoll);
        paperDollColorDivide =findViewById(R.id.paperDollColorDivide);
        femaleText =findViewById(R.id.femaleText);
        maleText =findViewById(R.id.maleText);
        switch1 =findViewById(R.id.switch1);// the switch has not been applied yet, we only have a male gender right now

        patient =(Patient)getIntent().getSerializableExtra("patient");
        record = (Record) getIntent().getSerializableExtra("record");
        problem = (Problem)getIntent().getSerializableExtra("problem");
        records = (ArrayList<Record>)getIntent().getSerializableExtra("records");
        problems = (ArrayList<Problem>)getIntent().getSerializableExtra("problems");
    }
    /**
     * A touch listener handles all functions that deal with the case of which body location
     * the patient has selected.
     * It save the patient's choice, and pass the data to next activity for adding body location photos.
     */
    View.OnTouchListener touchListener= new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            int color = getHotspotColor(R.id.paperDollColorDivide,(int) e.getX(),(int) e.getY());
            BodyColor bodyColor = new BodyColor();
            BodyPart bodyPart = bodyColor.getBodyPart(color);
            Log.i("Click", "Color = "+ Integer.toString(color));
            BodyLocation bodyLocation = checkBody(bodyPart);
            record.setBodyLocation(bodyLocation);
            Intent intent = new Intent(PatientPaperDollSelectionPageActivity.this, PatientBodyLocationPhotoAddingPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("problem",problem);
            bundle.putSerializable("record", record);
            bundle.putSerializable("records", records);
            bundle.putSerializable("patient",patient);
            bundle.putSerializable("problems", problems);
            intent.putExtras(bundle);
            startActivity(intent);
            return false;
        }
    };

    /**
     * This function specific each part of body location with color.
     * It uses the same copy of paper doll, but full of different color on it
     * It set above the visible paper doll image, inorder to get the user click data and it is set in invisible.
     * So that the patient will see a paper doll image, but clicked on another paper doll image that is invisible
     * @param hotspotId
     * @param x         x_coordinate
     * @param y         y_coordinate
     * @return          color value
     */
    public int getHotspotColor (int hotspotId, int x, int y) {
        ImageView img = findViewById (hotspotId);
        if (img == null) {
            Log.d ("BodyMapSelection", "Hot spot image not found");
            return 0;
        } else {
            img.setDrawingCacheEnabled(true);
            Bitmap hotspot = Bitmap.createBitmap(img.getDrawingCache());
            if (hotspot == null) {
                Log.d ("BodyMapSelection", "Hot spot bitmap was not created");
                return 0;
            } else {
                img.setDrawingCacheEnabled(false);
                return hotspot.getPixel(x, y);
            }
        }
    }

    /**
     * This function will handle the different case after a user click on the image
     * Each part of image is marked with a color,
     * and each color is associated with a body part.
     * By referring the object bodypart, we will figure out which body location of paper doll, a specific body part that has been chosen
     * @param bodyPart
     * @return
     */
    private BodyLocation checkBody(BodyPart bodyPart)
    {
        BodyLocation bodyLocation = new BodyLocation(null,new ArrayList<String>());
        switch (bodyPart){

            // define color values
            //head:-260100
            // neck: -5634052
            // shoulder: -16316420
            // chest: -9500676
            // upper-arm: -16282372
            // lower-arm: -16253703
            // hand: -16253703
            // belt: -260143
            // back:-260224
            // hip: -16253893
            // thigh: -1508345
            // knee: -210169
            // shank: -230905
            // foot: -238329
            case head:
                bodyLocation.setBodyLocationName("head");
                break;
            case neck:
                bodyLocation.setBodyLocationName("neck");
                break;
            case shoulder:
                bodyLocation.setBodyLocationName("shoulder");
                break;
            case chest:
                bodyLocation.setBodyLocationName("chest");
                break;
            case upper_arm:
                bodyLocation.setBodyLocationName("upper_arm");
                break;
            case lower_arm:
                bodyLocation.setBodyLocationName("lower_arm");
                break;
            case hand:
                bodyLocation.setBodyLocationName("hand");
                break;
            case belt:
                bodyLocation.setBodyLocationName("belt");
                break;
            case back:
                bodyLocation.setBodyLocationName("back");
                break;
            case hip:
                bodyLocation.setBodyLocationName("hip");
                break;
            case thigh:
                bodyLocation.setBodyLocationName("thigh");
                break;
            case knee:
                bodyLocation.setBodyLocationName("knee");
                break;
            case shank:
                bodyLocation.setBodyLocationName("shank");
                break;
            case foot:
                bodyLocation.setBodyLocationName("foot");
                break;
            case NULL:
                break;
        }
        return bodyLocation;
    }
/*  does not use
    @Override protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EXIT) {
            if (resultCode == RESULT_OK) {
                this.finish();

            }
        }
    }*/
    /**
     * when leave this page, kill the activity
     */
    @Override
    public void onStop(){
        super.onStop();
        finish();
    }

// does not use
/*    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_RECORD_ADDING)
        {
            if(resultCode==RESULT_OK)
            {
                problem = (Problem)getIntent().getSerializableExtra("problem");
                records = (ArrayList<Record>)data.getSerializableExtra("records");
                mAdapter.notifyDataSetChanged();
            }
        }
    }*/
}
