package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientRecordAddingPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientListOfProblemsPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemAddingPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientRecordDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientShowProviderCommentPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderAListOfProblemsPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderCommentPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderMainPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderRecordDetailPageActivity;
import com.example.cmput301f18t09.cureall.R;

public class UserLoginActivity extends AppCompatActivity {
    private ImageView loveSymbol;
    private EditText userNameInput, passwordInput;
    private ImageButton loginButton, backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initalizeAllElements();
    }



    public void initalizeAllElements(){
        loveSymbol = (ImageView) findViewById(R.id.loveSymbol);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInputpass);
        loginButton = (ImageButton) findViewById(R.id.searchButton);
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
            case R.id.showEachPhotoInRecord:
                Intent intent5 = new Intent(this, ProviderRecordDetailPageActivity.class);
                startActivity(intent5);
                return true;
            //the above are the test for provider's page... the belows are for patients
            case R.id.PatientShowListProblem:
                Intent intent6 = new Intent(this, PatientListOfProblemsPageActivity.class);
                startActivity(intent6);
                return true;
            case R.id.PatientAddProblemPage:
                Intent intent7 = new Intent(this, PatientProblemAddingPageActivity.class);
                startActivity(intent7);
                return true;
            case R.id.PatientProblemDatailPage:
                Intent intent8 = new Intent(this, PatientProblemDetailPageActivity.class);
                startActivity(intent8);
                return true;
            case R.id.PatientAddRecordPage:
                Intent intent9 = new Intent(this, PatientRecordAddingPageActivity.class);
                startActivity(intent9);
                return true;
            case R.id.PatientShowProviderCommentPage:
                Intent intent10 = new Intent(this, PatientShowProviderCommentPageActivity.class);
                startActivity(intent10);
                return true;
            case R.id.PatientRecordDetailPage:
            Intent intent11 = new Intent(this, PatientRecordDetailPageActivity.class);
            startActivity(intent11);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
