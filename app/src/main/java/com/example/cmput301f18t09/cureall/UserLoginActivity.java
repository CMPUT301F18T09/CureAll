package com.example.cmput301f18t09.cureall;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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
}
