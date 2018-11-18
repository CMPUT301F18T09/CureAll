package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.R;

public class PatientShowProviderCommentPageActivity extends AppCompatActivity {
    private ImageButton backButton;
    private TextView title, problemDescription,problemProviderComment,
            titleContent,descriptionContent,commentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_show_provider_comment_page);
        backButton=findViewById(R.id.backButton);
        title=findViewById(R.id.title);
        problemDescription=findViewById(R.id.problemDescription);
        problemProviderComment=findViewById(R.id.problemProviderComment);
        titleContent=findViewById(R.id.titleContent);
        descriptionContent=findViewById(R.id.descriptionContent);
        commentContent=findViewById(R.id.commentContent);
    }
}
