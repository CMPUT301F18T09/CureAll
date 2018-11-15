package com.example.cmput301f18t09.cureall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class SearchPage extends AppCompatActivity {

    EditText Keywords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button search = (Button) findViewById(R.id.search_button);
        Keywords = (EditText)findViewById(R.id.Search_keywords);
        //EditText locations = (EditText)findViewById(R.id.Search_Location);
        //EditText bodys = (EditText)findViewById(R.id.Search_Body);

        search.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String keywords = Keywords.getText().toString();
                ElasticSearchController.SearchKeywordsTask sk = new ElasticSearchController.SearchKeywordsTask();

                sk.execute(keywords);

                try {
                    List<Problem> foundPatient= sk.get();
                    //problems.addAll(foundPatient);


                } catch (Exception e) {
                    Log.i("Error", "Failed to get the user from the async object");
                }

                Log.i("Read","read end");

            }
        });
    }




}
