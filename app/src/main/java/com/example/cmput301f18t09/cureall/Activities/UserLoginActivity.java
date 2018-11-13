package com.example.cmput301f18t09.cureall.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.cmput301f18t09.cureall.R;

public class UserLoginActivity extends AppCompatActivity {
    private ImageView loveSymbol;
    private EditText userNameInput, passwordInput;
    private ImageButton loginButton, backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initalizeAllElements();
        setContentView(R.layout.activity_user_login);
    }



    public void initalizeAllElements(){
        loveSymbol = (ImageView) findViewById(R.id.loveSymbol);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInputpass);
        loginButton = (ImageButton) findViewById(R.id.backButton);
        backButton = (ImageButton) findViewById(R.id.loginButton);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        String theUser = getIntent().getStringExtra("user");
        if (theUser.equals("Patient")){
            inflater.inflate(R.menu.patient_testview_menu,menu);
        }
        else{
            inflater.inflate(R.menu.provider_testview_menu,menu);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.ProviderTest:
                return true;
            case R.id.showListPatients:
                Intent intent = new Intent(this, ProviderMainPageActivity.class);
                startActivity(intent);
                return true;
            case R.id.showListProblem:
                Intent intent2 = new Intent(this, ProviderAListOfProblemsPageActivity.class);
                startActivity(intent2);
                return true;
            case R.id.ProviderComments:
                Intent intent3 = new Intent(this, ProviderCommentPageActivity.class);
                startActivity(intent3);
                return true;
            case R.id.showProblemDetail:
                Intent intent4 = new Intent(this, ProviderProblemDetailPageActivity.class);
                startActivity(intent4);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
