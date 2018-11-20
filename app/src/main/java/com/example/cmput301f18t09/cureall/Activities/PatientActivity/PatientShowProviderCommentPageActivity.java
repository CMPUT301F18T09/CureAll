package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;

public class PatientShowProviderCommentPageActivity extends AppCompatActivity {
    private ImageButton backButton;
    private TextView titleTextView, problemDescription,problemProviderComment,
            titleContent,descriptionContent,commentContent;
    private Problem problem;
    private Patient patient;
    private ArrayList<Problem> problems;
    ArrayList<Record> records = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_show_provider_comment_page);
        backButton=findViewById(R.id.backButton);
        titleTextView=findViewById(R.id.title);
        problemDescription=findViewById(R.id.problemDescription);
        problemProviderComment=findViewById(R.id.problemProviderComment);
        titleContent=findViewById(R.id.titleContent);
        descriptionContent=findViewById(R.id.descriptionContent);
        commentContent=findViewById(R.id.commentContent);
        problem = (Problem)getIntent().getSerializableExtra("problem");
        records = (ArrayList<Record>)getIntent().getSerializableExtra("records");
        problems = (ArrayList<Problem>)getIntent().getSerializableExtra("problems");
        patient = (Patient)getIntent().getSerializableExtra("patient");

        commentContent.setText(problem.getDoctorcomment());
        descriptionContent.setText((problem.getDescription()));
        titleContent.setText(problem.getTitle());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientShowProviderCommentPageActivity.this,PatientProblemDetailPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem", problem);
                bundle.putSerializable("problems",problems);
                bundle.putSerializable("patient",patient);
                bundle.putSerializable("records", records);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
