/**
 * Class name: PatientRecordAddingPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.DialogueForAddingPhotoName;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.LocationPickerActivity;
import com.example.cmput301f18t09.cureall.model.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.model.BodyLocation;
import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.model.Record;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;
import com.example.cmput301f18t09.cureall.model.Sync;
import com.example.cmput301f18t09.cureall.model.UserState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * For this activity, user(patient) can add record into a problem
 */
public class PatientRecordAddingPageActivity extends AppCompatActivity implements DialogueForAddingPhotoName.DialogListener {
    private ArrayList<String> photoPaths = new ArrayList<String>();
    private ImageButton backButton, saveButton, fromAlbumButton,
            cameraButton,geoLocationSelectButton,timeSelectButton,bodyLocationSelectButton;
    private ImageView writeSymbol;
    private TextView maxLength30, maxLength300;
    private EditText titleInput, descriptionInput;
    private String title;
    private String description;
    private Date date;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private RecordController recordController;
    private Problem problem;
    private ArrayList<Problem> problems;
    private Record record;
    private Patient patient;
    private BodyLocation bodyLocation;
    private ArrayList<Record> records;
    private AllKindsOfPhotos newpicture;
    private String photoName = "default";
    private String control = "default";
    private Calendar cal;
    private int year ;
    private int month ;
    private int day ;
    private int hour ;
    private int min ;


    public static final int IMAGE_GALLERY_REQUEST = 20;
    final int REQUEST_IMAGE_CAPTURE = 1;
    //photos
    private ArrayList<AllKindsOfPhotos> pictures ;
    public static final int CAMERA_REQUEST_CODE = 228;
    /**
     * get patients info and corresponding problems & records
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_record_page);
        initializer();
        Intent intent = getIntent();
        if (intent.getStringExtra("ComeFromProblemDetail") != null && intent.getStringExtra("ComeFromProblemDetail").equals("ComeFromProblemDetail")){
            getDataFromProblemDetail();
        }
        else if(intent.getStringExtra("ComeFromGeoLocationSelectionPage") != null && intent.getStringExtra("ComeFromGeoLocationSelectionPage").equals("ComeFromGeoLocationSelectionPage")){
            loaaDataFromLocal();
            getDataFromGeolocationSelectionPage();
        }
        else if(intent.getStringExtra("ComeFromBodyLocationPhotoAddingPage") != null && intent.getStringExtra("ComeFromBodyLocationPhotoAddingPage").equals("ComeFromBodyLocationPhotoAddingPage")){
            loaaDataFromLocal();
            getDataFromBodyLocationSelectionPage();
        }


        //TODO once we use shared perfence we will not face this problem
        if (record != null) {
            bodyLocation = record.getBodyLocation();
            pictures = record.getRecordTrackingPhotoArrayList();
            System.out.println(pictures);
        }
        else {
            pictures = new ArrayList<AllKindsOfPhotos>();
        }
        if (pictures == null)
        {
            pictures = new ArrayList<AllKindsOfPhotos>();
            if(record.getRecordTrackingPhotoArrayList() == null){
                record.setRecordTrackingPhotoArrayList(pictures);
            }
            else {
                pictures = record.getRecordTrackingPhotoArrayList();
            }

        }
        if (record == null &&problem != null) {

            record = new Record(null,null,null);
            record.setProblemid(problem.getId());
            record.setUsername(problem.getUsername());
        }
    }
    /**
     * set for listener for all buttons
     */
    @Override
    protected void onStart() {
        super.onStart();
        record.setTime(new Date());
        //set bodyLocationSelectButton listener
        bodyLocationSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control = "kill";
                Intent intent = new Intent(PatientRecordAddingPageActivity.this, PatientPaperDollSelectionPageActivity.class);
                //TODO replace this with shared perference
                record.setRecordTrackingPhotoArrayList(pictures);
                saveDataToLocal(patient,records,problems,problem);
                passDataToPaperDollSelectionPage(record);
                intent.putExtra("ComeFromRecordAddingPage","ComeFromRecordAddingPage");
                startActivity(intent);
            }
        });

        // implement user selet time use case
        timeSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cal = Calendar.getInstance();
                //Date date = emotions.get(finalPosition).getDate();


                cal.setTime(date);

                year = cal.get(Calendar.YEAR);
                month =cal.get(Calendar.MONTH);
                day = cal.get(Calendar.DAY_OF_MONTH);
                hour = cal.get(Calendar.HOUR_OF_DAY);
                min =cal.get(Calendar.MINUTE);
                // emotion used to remember which item are being edited
                //final Mood emotion = emotions.get(finalPosition);

                //using DatePicker + TimePicker to avoid user input some not correct values

                new TimePickerDialog(PatientRecordAddingPageActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        //cal.setTime(emotions.get(finalPosition).getDate());
                        cal.setTime(date);
                        year = cal.get(Calendar.YEAR);
                        month =cal.get(Calendar.MONTH);
                        day = cal.get(Calendar.DAY_OF_MONTH);
                        cal.set(year,month,day,i,i1);
                        // finish setting date
                        date = cal.getTime();
                        // setting the date for record to the date
                        record.setTime(date);

                        //setTime(finalPosition,year,month,day,i,i1,cal,emotions, adapter);
                        //FileEditor.saveInFile(HistoryOperation.this,FeelsBook.FILENAME,emotions);
                    }
                }   ,hour
                        ,min
                        ,true).show();

                new DatePickerDialog(PatientRecordAddingPageActivity.this,
                        AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        cal.set(i,i1,i2);
                        date = cal.getTime();
                        //setDate(finalPosition,i,i1,i2,cal,emotions,adapter);
                        //FileEditor.saveInFile(HistoryOperation.this,FeelsBook.FILENAME,emotions);

                        //finalPosition = emotions.indexOf(emotion);
                    }
                }   ,year
                        ,month
                        ,day).show();
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                invokeCamera();
                //TODO
                dispatchTakePictureIntent();
            }
        });

        //set for fromAlbumButton listener
        fromAlbumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onImageGalleryClicked();
            }
        });

        //set for geoLocationSelectButton listener
        geoLocationSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass problem and record to ...

                record.setRecordTrackingPhotoArrayList(pictures);
                Intent intent = new Intent(PatientRecordAddingPageActivity.this,LocationPickerActivity.class);
                saveDataToLocal(patient,records,problems,problem);
                passDataToGeolocationSelectionPage(problem,record);
                intent.putExtra("ComeFromRecordAddingPage","ComeFromRecordAddingPage");
                startActivity(intent);
            }
        });

        //set for saveButton listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**
                 * the check if statement has not finished yet!
                 */
                title = titleInput.getText().toString();
                description = descriptionInput.getText().toString();
                date = new Date();
                record.setTitle(title);
                record.setComment(description);
                //todo deal with no selection time
                //record.setTime(new Date());
                record.setRecordTrackingPhotoArrayList(pictures);
                records.add(record);
                Record temp = record;
                control = "kill";
                UserState currentState = new UserState(PatientRecordAddingPageActivity.this);
                if (currentState.getState()){
                    //TODO only save to es
                    record.setTitle(titleInput.getText().toString());
                    record.setComment(descriptionInput.getText().toString());
                    temp = saveRecord(problem.getUsername(),record,problem.getId());
                    temp.setState("Online");

                }
                else{
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                    temp.setID(temp.getTitle()+currentDateTimeString);
                    //decryptF
                    record.setTitle(titleInput.getText().toString());
                    record.setComment(descriptionInput.getText().toString());
                    temp.setState("offline");

                }
                saveLocal(problem.getUsername(), record, problem.getId(),temp);
                Sync sync = new Sync(PatientRecordAddingPageActivity.this,problem.getUsername());
                sync.UpdateTracker(problem.getUsername());


                PatientController.SaveLocalTracker(PatientRecordAddingPageActivity.this,problem.getUsername());

                Intent intent = new Intent(PatientRecordAddingPageActivity.this, PatientProblemDetailPageActivity.class);

                passDataToProblemDetailPage(problem,problems,records);
                intent.putExtra("ComeFromRecordAddingPageSAVE","ComeFromRecordAddingPageSAVE");
                startActivity(intent);
            }
        });

    }

    /**
     * each image is able to be clicked
     */
    public void onImageGalleryClicked() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        Uri data = Uri.parse(pictureDirectoryPath);
        photoPickerIntent.setDataAndType(data, "image/*");
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }

    /**
     * jump activity
     */
    private void dispatchTakePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
}
    /**
     * deal with the result for activity done
     * @param requestCode   (build in)
     * @param resultCode    (build in)
     * @param data          (build in)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            System.out.println(bitmap.getRowBytes());
            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
            String temp= Base64.encodeToString(b, Base64.DEFAULT);
            newpicture = new AllKindsOfPhotos(bitmap,temp,photoName,0.0,0.0,0.0);
            openDialogue();

        }
        if (requestCode == IMAGE_GALLERY_REQUEST) {
            // if we are here, we are hearing back from the image gallery.
            // the address of the image on the SD Card.
            Uri imageUri = data.getData();
            // declare a stream to read the image data from the SD Card.
            InputStream inputStream;
            // we are getting an input stream, based on the URI of the image.
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                // get a bitmap from the stream.
                Bitmap image = BitmapFactory.decodeStream(inputStream);
                ByteArrayOutputStream baos=new  ByteArrayOutputStream();
                image.compress(Bitmap.CompressFormat.JPEG,10, baos);
                System.out.println(image.getRowBytes());
                byte [] b=baos.toByteArray();
                String temp= Base64.encodeToString(b, Base64.DEFAULT);
                newpicture = new AllKindsOfPhotos(image,temp,"default name",0.0,0.0,0.0);
                openDialogue();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // show a message to the user indictating that the image is unavailable.
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * open dialogue for adding photos
     */
    public void openDialogue(){
        DialogueForAddingPhotoName dialogueForAddingPhotoName = new DialogueForAddingPhotoName();
        dialogueForAddingPhotoName.show(getSupportFragmentManager(),"dialogueForAddingPhotoName");
        dialogueForAddingPhotoName.setCancelable(false);
    }

    /**
     *
     * @param name photo name
     */
    @Override
    public void applyTexts(String name) {
        photoName = name;
        newpicture.setPhotoType(photoName);
        pictures.add(newpicture);
    }

    /**
     *
     * @param username username
     * @param record current record
     * @param problemID problem ID
     * @return the record to be saved
     */
    //TODO
    public Record saveRecord(String username, Record record, String problemID){
        Record temp = record;
        record.setState("Online");
        ElasticSearchParams param = new ElasticSearchParams(username,record,problemID,"add");
        ElasticSearchController.AddRecordTask addRecordTask = new ElasticSearchController.AddRecordTask();
        addRecordTask.execute(param);
        try {
            record = addRecordTask.get();
            Sync sync = new Sync(PatientRecordAddingPageActivity.this,username);
            sync.UpdateTracker(username);
        }catch (Exception e){
            Log.i("Record","Something wrong happened when tried to save record to es");
        }


        /**
         * set the record title and description based on the input you enter in both two edittext.
         * A small bug is that, before you click the save button, make sure the content in edittext is what you want to save
         * Because, some time, the data will lost after you adding photos or geolocations.
         */
        record.setTitle(titleInput.getText().toString());
        record.setComment(descriptionInput.getText().toString());

        return temp;
    }

    /**
     * behaviour of activity stops
     */
    //TODO
    @Override
    protected void onStop() {
        super.onStop();
        if (control.equals("kill")){
            finish();
        }


    }

    /**
     * get data from ProblemDetail
     */
    public void getDataFromProblemDetail(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("problem",null);
        String json2 = sharedPreferences2.getString("problems",null);
        String json3 = sharedPreferences2.getString("patient",null);
        String json4= sharedPreferences2.getString("records",null);
        Type type = new TypeToken<Problem>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Problem>>(){}.getType();
        Type type3 = new TypeToken<Patient>(){}.getType();
        Type type4 = new TypeToken<ArrayList<Record>>(){}.getType();
        problem = gson.fromJson(json,type);
        problems = gson.fromJson(json2,type2);
        patient = gson.fromJson(json3,type3);
        records =gson.fromJson(json4,type4);
    }

    /**
     * get data from GeolocationSelectionPage
     */
    public void getDataFromGeolocationSelectionPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("GeoLocationData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("problem",null);
        String json2 = sharedPreferences2.getString("record",null);
        Type type = new TypeToken<Problem>(){}.getType();
        Type type2 = new TypeToken<Record>(){}.getType();
        problem = gson.fromJson(json,type);
        record = gson.fromJson(json2,type2);
    }

    /**
     * get data from BodyLocationSelectionPage
     */
    public void getDataFromBodyLocationSelectionPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("BodyLocationPhotoAddingData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("record",null);
        Type type = new TypeToken<Record>(){}.getType();
        record = gson.fromJson(json,type);
    }

    /**
     * load data from local file
     */
    public void loaaDataFromLocal(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordAddingData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json1 = sharedPreferences2.getString("patient",null);
        String json2= sharedPreferences2.getString("records",null);
        String json3 = sharedPreferences2.getString("problems",null);
        String json4 = sharedPreferences2.getString("problem",null);
        Type type1 = new TypeToken<Patient>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Record>>(){}.getType();
        Type type3 = new TypeToken<ArrayList<Problem>>(){}.getType();
        Type type4 = new TypeToken<Problem>(){}.getType();
        patient = gson.fromJson(json1,type1);
        records =gson.fromJson(json2,type2);
        problems = gson.fromJson(json3,type3);
        problem = gson.fromJson(json4,type4);
    }


    /**
     *
     * @param problem problem
     * @param problems arrayList of problems
     * @param records arrayList of records
     */
    public void passDataToProblemDetailPage(Problem problem, ArrayList<Problem> problems, ArrayList<Record> records){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordAddingData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        String json2 = gson.toJson(problems);
        String json4 = gson.toJson(records);
        editor2.putString("problem",json);
        editor2.putString("problems",json2);
        editor2.putString("records",json4);
        editor2.apply();
    }

    /**
     *
     * @param problem current problem
     * @param record current record
     */
    public void passDataToGeolocationSelectionPage(Problem problem, Record record){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordAddingData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        String json2 = gson.toJson(record);
        editor2.putString("problem",json);
        editor2.putString("record",json2);
        editor2.apply();
    }

    /**
     *
     * @param record current record
     */
    public void passDataToPaperDollSelectionPage(Record record){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordAddingData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(record);/**save in gson format*/
        editor2.putString("record",json);
        editor2.apply();
    }


    /**
     *
     * @param patient current patient
     * @param records arrayList of records
     * @param problems arrayList of problems
     * @param problem current problem
     */
    public void saveDataToLocal(Patient patient, ArrayList<Record> records, ArrayList<Problem> problems,Problem problem){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordAddingData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(patient);/**save in gson format*/
        String json2 = gson.toJson(records);
        String json3 = gson.toJson(problems);
        String json4 = gson.toJson(problem);
        editor2.putString("patient",json);
        editor2.putString("records",json2);
        editor2.putString("problems",json3);
        editor2.putString("problem",json4);
        editor2.apply();
    }

    /**
     * set init value for elements used in this activity
     * (or give reference)
     * including buttons, textviews, new record's info
     */
    private void initializer()
    {
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);
        fromAlbumButton = findViewById(R.id.fromAlbumButton);
        cameraButton = findViewById(R.id.cameraButton);
        geoLocationSelectButton = findViewById(R.id.geoLocationSelectButton);
        timeSelectButton = findViewById(R.id.timeSelectButton);
        bodyLocationSelectButton = findViewById(R.id.bodyLocationSelectButton);
        writeSymbol = findViewById(R.id.writeSymbol);
        maxLength30 = findViewById(R.id.maxLength30);
        maxLength300 = findViewById(R.id.maxLength300);
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        recordController = new RecordController();
        date = new Date();
    }

    /**
     *
     * @param username username
     * @param record current record
     * @param problemID problem ID
     * @param temp the record to be saved
     */
    //TODO add a save local function
    private void saveLocal(String username, Record record, String problemID,Record temp) {

        ArrayList<Record> AllRecords = new ArrayList<>();
        AllRecords = RecordController.loadFromFile(PatientRecordAddingPageActivity.this, "records.txt", AllRecords, username);

        Log.i("ID",temp.getID());
        AllRecords.add(temp);
        RecordController.saveInFile(PatientRecordAddingPageActivity.this,"records.txt",AllRecords,username);

    }

}
