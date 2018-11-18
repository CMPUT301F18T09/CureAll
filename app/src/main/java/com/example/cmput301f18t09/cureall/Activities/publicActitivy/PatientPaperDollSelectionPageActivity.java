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
            case HAND:
                bodyLocation.setBodyLocationName("HAND");
                break;
            case HEAD:
                bodyLocation.setBodyLocationName("HEAD");
                break;
            case HIP:
                bodyLocation.setBodyLocationName("HIP");
                break;
            case FOOT:
                bodyLocation.setBodyLocationName("FOOT");
                break;
            case KNEE:
                bodyLocation.setBodyLocationName("KNEE");
                break;
            case CHEST:
                bodyLocation.setBodyLocationName("CHEST");
                break;
            case FOREARM:
                bodyLocation.setBodyLocationName("FOREARM");
                break;
            case SHOULDER:
                bodyLocation.setBodyLocationName("SHOULDER");
                break;
            case LOWER_LEG:
                bodyLocation.setBodyLocationName("LOWER LEG");
                break;
            case UPPER_LEG:
                bodyLocation.setBodyLocationName("UPPER LEG");
                break;
            case LOWER_BACK:
                bodyLocation.setBodyLocationName("LOWER_BACK");
                break;
            case UPPER_BACK:
                bodyLocation.setBodyLocationName("UPPER_BACK");
                break;
            case NULL:
                break;
        }
        return bodyLocation;
    }

}
