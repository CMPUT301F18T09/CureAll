package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientBodyLocationPhotoAddingPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientPhotoFlowPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientRecordAddingPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientListOfProblemsPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemAddingPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientRecordDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientShowProviderCommentPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientViewBodyLocationPhotoPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderAListOfProblemsPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderCommentPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderMainPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderRecordDetailPageActivity;
import com.example.cmput301f18t09.cureall.CareProvider;
import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;
import java.util.List;

public class UserLoginActivity extends AppCompatActivity {
    private ImageView loveSymbol;
    private EditText userNameInput, passwordInput;
    private Button loginButton, backButton;
    private ArrayList<Problem> problems;
    private ProblemController problemController = new ProblemController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initalizeAllElements();
        Intent incomingIntent = getIntent();
        final String Role = incomingIntent.getStringExtra("Role");
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                login(Role);
            }
        });
    }



    public void initalizeAllElements(){
        loveSymbol = (ImageView) findViewById(R.id.loveSymbol);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInputpass);
        loginButton = (Button) findViewById(R.id.loginButton);
        backButton = (Button) findViewById(R.id.backButton);
    }

    public void login(String Role){
        ArrayList<Patient> patients = new ArrayList<Patient>();
        ArrayList <CareProvider> doctors = new ArrayList<CareProvider>();
        if (Role.equals("Patient")){
            setResult(RESULT_OK);

            String Username = userNameInput.getText().toString();
            String Password = passwordInput.getText().toString();

            ElasticSearchController.GetPatientTask getuserTask = new ElasticSearchController.GetPatientTask();
            getuserTask.execute(Username);

            try {
                List<Patient> foundPatient= getuserTask.get();
                patients.addAll(foundPatient);


            } catch (Exception e) {
                Log.i("Chen", "Failed to get the user from the async object");
            }

            Log.i("Read","read end");

            String pass =  patients.get(0).getPassword();
            if (pass.equals(Password)){
                problems = problemController.GetProblemNum(patients.get(0).getUsername());
                Intent intent = new Intent(UserLoginActivity.this,PatientListOfProblemsPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("patient", patients.get(0));
                bundle.putSerializable("problems",problems);
                intent.putExtras(bundle);
                //Patient user = patients.get(0);
                // intent.putExtra(,patients);
                startActivity(intent);
            }

        }
        else{

            setResult(RESULT_OK);

            String Username = userNameInput.getText().toString();
            String Password = passwordInput.getText().toString();

            ElasticSearchController.GetDoctorTask getuserTask = new ElasticSearchController.GetDoctorTask();
            getuserTask.execute(Username);

            try {
                List<CareProvider> foundDoctor= getuserTask.get();
                Log.i("CareProvider",Integer.toString(foundDoctor.size()));
                doctors.addAll(foundDoctor);
                Log.i("CareProvider",Integer.toString(doctors.size()));

            } catch (Exception e) {
                Log.i("Error", "Failed to get the user from the async object");
            }
            Log.i("CareProviderout",Integer.toString(doctors.size()));
            String pass =  doctors.get(0).getPassword();
            if (pass.equals(Password)){
                Intent intent = new Intent(UserLoginActivity.this,ProviderMainPageActivity.class);
                intent.putExtra("username", doctors.get(0).getUsername());
                startActivity(intent);
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        String theUser = getIntent().getStringExtra("Role");
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
            case R.id.PatientPaperDollPage:
                Intent intent12 = new Intent(this, PatientPaperDollSelectionPageActivity.class);
                startActivity(intent12);
                return true;
            case R.id.PatientBodyLocationPhotoAddingPage:
                Intent intent13 = new Intent(this, PatientBodyLocationPhotoAddingPageActivity.class);
                startActivity(intent13);
                return true;
            case R.id.PatientBodyLocationPhotoViewgPage:
                Intent intent14 = new Intent(this, PatientViewBodyLocationPhotoPageActivity.class);
                startActivity(intent14);
                return true;
            case R.id.PatientPhotoFlowAnimation:
                Intent intent15 = new Intent(this, PatientPhotoFlowPageActivity.class);
                startActivity(intent15);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
