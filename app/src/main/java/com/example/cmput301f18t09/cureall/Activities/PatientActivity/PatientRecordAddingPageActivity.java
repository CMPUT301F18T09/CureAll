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

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.DialogueForAddingPhotoName;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.LocationPickerActivity;
import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    private String photoName;


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
        //photoPaths = (ArrayList<String>) getIntent().getSerializableExtra("photo paths");
        //record = (Record) getIntent().getSerializableExtra("record");
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
        //set bodyLocationSelectButton listener
        bodyLocationSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientRecordAddingPageActivity.this, PatientPaperDollSelectionPageActivity.class);
                //TODO replace this with shared perference
                record.setRecordTrackingPhotoArrayList(pictures);
                saveDataToLocal(patient,records,problems,problem);
                passDataToPaperDollSelectionPage(record);
                intent.putExtra("ComeFromRecordAddingPage","ComeFromRecordAddingPage");
                startActivity(intent);
            }
        });

        // set for backButton listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientRecordAddingPageActivity.this, PatientProblemDetailPageActivity.class);

                record.setRecordTrackingPhotoArrayList(pictures);
                //meaningless?
                passDataToProblemDetailPage(problem, problems, records);
                intent.putExtra("ComeFromRecordAddingPage","ComeFromRecordAddingPage");
                startActivity(intent);
            }
        });

        //set for cameraButton listener
        //question!!!!!!!!!!!BUG!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
                /*if (record.getRecordTrackingPhotoArrayList().size() < 10){
                    Toast.makeText(PatientRecordAddingPageActivity.this, "Your tracking photo is less than 10", Toast.LENGTH_SHORT).show();
                }
                else if(record.getGeoLocation() == null){
                    Toast.makeText(PatientRecordAddingPageActivity.this, "You didn't specific any geoLocation yet!", Toast.LENGTH_SHORT).show();
                }
                else if(record.getBodyLocation() == null){
                    Toast.makeText(PatientRecordAddingPageActivity.this, "You didn't specific any body Location yet!", Toast.LENGTH_SHORT).show();
                }*/

                title = titleInput.getText().toString();
                description = descriptionInput.getText().toString();
                date = new Date();
                record.setTitle(title);
                record.setComment(description);
                record.setTime(new Date());
                record.setRecordTrackingPhotoArrayList(pictures);
                records.add(record);
                //TODO this is online save, we also need a local save for record
                saveRecord(problem.getUsername(),record,problem.getId());

                Intent intent = new Intent(PatientRecordAddingPageActivity.this, PatientProblemDetailPageActivity.class);
                //TODO replace this with shared perrference
                saveDataToLocal(patient,records,problems,problem);
                intent.putExtra("ComeFromRecordAddingPageSAVE","ComeFromRecordAddingPageSAVE");
                startActivity(intent);
            }
        });

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


    }
    //onImageGalleryClicked
    public void onImageGalleryClicked() {
        // invoke the image gallery using an implict intent.
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        // where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        // finally, get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);
        // set the data and type.  Get all imageypes.
        photoPickerIntent.setDataAndType(data, "image/*");
        // we will invoke this activity, and get something back from it.
        startActivityForResult(photoPickerIntent, IMAGE_GALLERY_REQUEST);
    }
//////////////////////////////////////////////////////////////////////////////////
    //dispatchTakePictureIntent
    private void dispatchTakePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    // Ensure that there's a camera activity to handle the intent
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
            //TODO to string
            ByteArrayOutputStream baos=new  ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
            byte [] b=baos.toByteArray();
            String temp= Base64.encodeToString(b, Base64.DEFAULT);
            newpicture = new AllKindsOfPhotos(bitmap,temp,photoName,0.0,0.0,0.0);
            openDialogue();
            newpicture.setPhotoType(photoName);
            pictures.add(newpicture);
            /**
            Bitmap image;
            InputStream inputStream;
            Uri imageUri = data.getData();
            try {
                inputStream = getContentResolver().openInputStream(imageUri);
                image = BitmapFactory.decodeStream(inputStream);
                AllKindsOfPhotos newpicture = new AllKindsOfPhotos(image,"","type",0.0,0.0,0.0);
                pictures.add(newpicture);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }**/
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
                newpicture = new AllKindsOfPhotos(image,"","type",0.0,0.0,0.0);
                pictures.add(newpicture);
                // show the image to the use
                writeSymbol.setImageBitmap(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                // show a message to the user indictating that the image is unavailable.
                Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void openDialogue(){
        DialogueForAddingPhotoName dialogueForAddingPhotoName = new DialogueForAddingPhotoName();
        dialogueForAddingPhotoName.show(getSupportFragmentManager(),"dialogueForAddingPhotoName");
        dialogueForAddingPhotoName.setCancelable(false);
    }
    @Override
    public void applyTexts(String name) {
        photoName = name;
    }

    /**
     * save new added record online
     * @param username      username
     * @param record        new added record
     * @param problemID     corresponding problemID
     */
    public void saveRecord(String username, Record record, String problemID){
        ElasticSearchParams param = new ElasticSearchParams(username,record,problemID);
        ElasticSearchController.AddRecordTask addRecordTask = new ElasticSearchController.AddRecordTask();
        addRecordTask.execute(param);
        /**
         * set the record title and description based on the input you enter in both two edittext.
         * A small bug is that, before you click the save button, make sure the content in edittext is what you want to save
         * Because, some time, the data will lost after you adding photos or geolocations.
         */
        record.setTitle(titleInput.getText().toString());
        record.setComment(descriptionInput.getText().toString());
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

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

    public void getDataFromBodyLocationSelectionPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("BodyLocationPhotoAddingData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("record",null);
        Type type = new TypeToken<Record>(){}.getType();
        record = gson.fromJson(json,type);
    }
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
    public void passDataToPaperDollSelectionPage(Record record){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordAddingData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(record);/**save in gson format*/
        editor2.putString("record",json);
        editor2.apply();
    }


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


}
