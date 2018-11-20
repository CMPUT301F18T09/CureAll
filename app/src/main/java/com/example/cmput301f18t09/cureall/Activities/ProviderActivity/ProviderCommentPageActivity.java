package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;

public class ProviderCommentPageActivity extends AppCompatActivity {
    private ImageButton backButton, saveButton;
    private EditText providerComments;
    private Problem problem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_comment_page);
        backButton = (ImageButton) findViewById(R.id.backButton);
        saveButton = (ImageButton) findViewById(R.id.saveButton);
        providerComments = (EditText) findViewById(R.id.providerComments);
        Intent incomingIntent = getIntent();
        problem = (Problem) getIntent().getSerializableExtra("problem");
    }
    @Override
    protected void onStart() {

        super.onStart();

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Log.i("Patient",patient.getEmail());
                problem.setDoctorcomment(providerComments.getText().toString());
                ElasticSearchController.EditProblemTask editProblemTask = new ElasticSearchController.EditProblemTask();

                ElasticSearchParams params = new ElasticSearchParams("",problem,problem.getId());

                editProblemTask.execute(params);

                Intent intent = new Intent(ProviderCommentPageActivity.this,ProviderProblemDetailPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem",problem);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
