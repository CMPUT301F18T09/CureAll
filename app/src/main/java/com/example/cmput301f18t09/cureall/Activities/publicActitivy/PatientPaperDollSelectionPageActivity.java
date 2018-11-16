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
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

public class PatientPaperDollSelectionPageActivity extends AppCompatActivity {
    private ImageView paperDoll,paperDollColorDivide;
    private TextView femaleText, maleText;
    private Switch switch1;

    private float lastTouchX;  // position x
    private float lastTouchY;  // position y

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
    }
    View.OnTouchListener touchListener= new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent e) {
            int color = getHotspotColor(R.id.paperDollColorDivide,(int) e.getX(),(int) e.getY());
            BodyColor bodyColor = new BodyColor();
            BodyPart bodyPart = bodyColor.getBodyPart(color);
            Log.i("Click", "Color = "+ Integer.toString(color));
            Intent intent = new Intent(PatientPaperDollSelectionPageActivity.this, PatientBodyLocationPhotoAddingPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("body part", bodyPart);

            intent.putExtras(bundle);

            startActivity(intent);
            return false;
        }
    };
    /**
    View.OnTouchListener touchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (e.getActionMasked() == MotionEvent.ACTION_UP) {
                int color = getHotspotColor(R.id.colorMap,(int) e.getX(),(int) e.getY());  // an integer represents the color

                Log.i("Click", "X = "+ e.getX() + " Y = " + e.getY());

                if (bodyColor.getBodyPart(color) == BodyPart.NULL) {  // the position is invalid
                    return false;
                }

                // position is valid save it into variables
                lastTouchX = (int) e.getX();
                lastTouchY = (int) e.getY();
                Toast toast = Toast.makeText(getApplicationContext(), ""+bodyColor.getBodyPart(color), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                return true;
            }

            // let the touch event pass on to whoever needs it
            return false;
        }
    };
     **/
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

}
