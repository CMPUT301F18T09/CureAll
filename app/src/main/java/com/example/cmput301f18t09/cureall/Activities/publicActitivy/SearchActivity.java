package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.R;

public class SearchActivity extends AppCompatActivity {
    private ImageButton back,startSearching;
    private TextView keywords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back = findViewById(R.id.back_btn1);
        keywords = findViewById(R.id.input_search);
        startSearching = findViewById(R.id.GO);


        //go search
        startSearching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this,SearchResultActivity.class));
            }
        });
        //go back
        back.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String From = getIntent().getStringExtra("From");
                if (From.equals("patient")) {
                    startActivity(new Intent(SearchActivity.this, PatientProblemDetailPageActivity.class));
                }
                else if (From.equals("provider")) {
                    startActivity(new Intent(SearchActivity.this, ProviderProblemDetailPageActivity.class));
                }
            }
        });
    }


}




