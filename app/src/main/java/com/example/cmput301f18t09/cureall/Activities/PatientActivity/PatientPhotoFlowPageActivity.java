package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.PatientAdapter.photoFlowPageAdapter;
import com.example.cmput301f18t09.cureall.R;

public class PatientPhotoFlowPageActivity extends AppCompatActivity {
    private ImageButton backButton;
    private TextView fixedText1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_photo_flow_page);
        backButton = findViewById(R.id.backButton);
        fixedText1 = findViewById(R.id.fixedText1);

        //viewPager...
        ViewPager viewPager = findViewById(R.id.viewPager);
        photoFlowPageAdapter adapter = new photoFlowPageAdapter(this);
        viewPager.setAdapter(adapter);

    }
}
