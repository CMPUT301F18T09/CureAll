package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.cmput301f18t09.cureall.R;
/**
 * this activity is used to show user's search result
 * This activity is not finished and not applied into app(project 4)
 * The prototype of this activity and base code has been created.
 */
public class SearchResultActivity extends AppCompatActivity {

    private ImageButton back;
    private ListView results;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        back = findViewById(R.id.back_btn2);
        results = findViewById(R.id.result_list);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchResultActivity.this,SearchActivity.class));
            }
        });
    }
}
