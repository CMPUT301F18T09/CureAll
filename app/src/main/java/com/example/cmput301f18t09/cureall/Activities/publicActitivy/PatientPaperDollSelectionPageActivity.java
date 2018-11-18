package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientBodyLocationPhotoAddingPageActivity;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.PaperDollController.*;

import com.example.cmput301f18t09.cureall.PaperDollController.BodyColor;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;

public class PatientPaperDollSelectionPageActivity extends AppCompatActivity {
    private ImageView paperDoll,paperDollColorDivide;
    private TextView femaleText, maleText;
    private Switch switch1;
    private Problem problem;

    private float lastTouchX;  // position x
    private float lastTouchY;  // position y
    Record record;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_paper_doll_selection_page);
        initializeAllElements();

        paperDoll.setOnTouchListener(touchListener);
    }

    public void initializeAllElements(){
        paperDoll =findViewById(R.id.paperDoll);
        paperDollColorDivide =findViewById(R.id.paperDollColorDivide);
        femaleText =findViewById(R.id.femaleText);
        maleText =findViewById(R.id.maleText);
        switch1 =findViewById(R.id.switch1);
        record = (Record) getIntent().getSerializableExtra("record");
        problem = (Problem)getIntent().getSerializableExtra("problem");
        //int a = 1;
    }
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
            bundle.putSerializable("record", record);
            bundle.putSerializable("problem", problem);
            //bundle.putSerializable("body", bodyLocation);


            intent.putExtras(bundle);

            startActivity(intent);
            return false;
        }
    };

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

}
