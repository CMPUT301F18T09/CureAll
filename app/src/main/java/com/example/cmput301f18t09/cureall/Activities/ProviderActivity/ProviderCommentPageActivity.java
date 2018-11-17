package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cmput301f18t09.cureall.R;

public class ProviderCommentPageActivity extends AppCompatActivity {
    private ImageButton backButton, saveButton;
    private EditText providerComments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_comment_page);
        backButton = (ImageButton) findViewById(R.id.backButton);
        saveButton = (ImageButton) findViewById(R.id.saveButton);
        providerComments = (EditText) findViewById(R.id.providerComments);
    }

}
