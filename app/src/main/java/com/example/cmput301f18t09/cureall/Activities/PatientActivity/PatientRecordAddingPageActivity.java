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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * For this activity, user(patient) can add record into a problem
 */
public class PatientRecordAddingPageActivity extends AppCompatActivity {
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


    public static final int IMAGE_GALLERY_REQUEST = 20;
    final int REQUEST_IMAGE_CAPTURE = 1;
    private String mCurrentPhotoPath;
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
        patient =(Patient)getIntent().getSerializableExtra("patient");
        record = (Record) getIntent().getSerializableExtra("record");
        problem = (Problem)getIntent().getSerializableExtra("problem");
        records = (ArrayList<Record>)getIntent().getSerializableExtra("records");
        problems = (ArrayList<Problem>)getIntent().getSerializableExtra("problems");
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
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this,
                    "com.example.cmput301f18t09.cureall.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}

    /**
     * create for image file
     * @return image
     * @throws IOException
     */
    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        //add photos into array;ist
        //pictures not save!!!!!!!!!when leave the activity!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!to select bodylocation.
        //when back from body location, everything not save!!!!!!!!!!!!!!!!!!!!!!!!!
        pictures.add(new AllKindsOfPhotos(mCurrentPhotoPath,"type",0.0,0.0,0.0));
        return image;
    }
    //////////////////////////////////////////////////////////////////////////////

    /**
     * deal with the result for activity done
     * @param requestCode   (build in)
     * @param resultCode    (build in)
     * @param data          (build in)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            Drawable drawable = new BitmapDrawable(bitmap);


        }
        if (resultCode == RESULT_OK) {
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

                    // show the image to the use
                    writeSymbol.setImageBitmap(image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    // show a message to the user indictating that the image is unavailable.
                    Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                }
            }
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
                Bundle bundle = new Bundle();
                System.out.println(pictures);
                record.setRecordTrackingPhotoArrayList(pictures);
                bundle.putSerializable("problem",problem);
                bundle.putSerializable("record", record);
                bundle.putSerializable("records", records);
                bundle.putSerializable("patient",patient);
                bundle.putSerializable("problems", problems);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // set for backButton listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientRecordAddingPageActivity.this, PatientProblemDetailPageActivity.class);
                Bundle bundle = new Bundle();
                record.setRecordTrackingPhotoArrayList(pictures);
                bundle.putSerializable("problem",problem);
                bundle.putSerializable("record", record);
                bundle.putSerializable("records", records);
                bundle.putSerializable("patient",patient);
                bundle.putSerializable("problems", problems);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        //set for cameraButton listener
        //question!!!!!!!!!!!BUG!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                invokeCamera();
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
                Bundle bundle = new Bundle();
                record.setRecordTrackingPhotoArrayList(pictures);
                bundle.putSerializable("problem",problem);
                bundle.putSerializable("record", record);
                bundle.putSerializable("records", records);
                bundle.putSerializable("patient",patient);
                bundle.putSerializable("problems", problems);
                intent.putExtras(bundle);
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
                saveRecord(problem.getUsername(),record,problem.getId());

                Intent intent = new Intent(PatientRecordAddingPageActivity.this, PatientProblemDetailPageActivity.class);
                Bundle bundle = new Bundle();
                record.setRecordTrackingPhotoArrayList(pictures);
                bundle.putSerializable("problem",problem);
                bundle.putSerializable("record", record);
                bundle.putSerializable("records", records);
                bundle.putSerializable("patient",patient);
                bundle.putSerializable("problems", problems);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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

}
