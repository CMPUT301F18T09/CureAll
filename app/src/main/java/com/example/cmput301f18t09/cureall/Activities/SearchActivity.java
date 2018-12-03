package com.example.cmput301f18t09.cureall.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.cmput301f18t09.cureall.R;

import com.example.cmput301f18t09.cureall.SearchController.ProblemSearchController;
import com.example.cmput301f18t09.cureall.SearchController.RecordSearchController;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.ShowSearchResultActivity;
import com.example.cmput301f18t09.cureall.model.GeoLocation;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.model.Record;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchActivity extends AppCompatActivity {

    String search_method;
    Button key_word_search_button;
    Button geo_search_button;
    Button body_search_button;
    ArrayList<Problem> problems = new ArrayList<>();
    ArrayList<Record> records = new ArrayList<>();
    ArrayList<String> record_string = new ArrayList<>();
    private GeoLocation recordGeoLocation;
    private Double longitude;
    private Double latitude;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String geolocation;
    private LatLng location;
    int RECORD_PLACE_PICKER_REQUEST = 1;
    int PROBLEM_PLACE_PICKER_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);
        key_word_search_button = (Button) findViewById(R.id.key_word_search);
        geo_search_button = (Button) findViewById(R.id.geo_search);
        body_search_button = (Button) findViewById(R.id.body_search);
        search_method = getIntent().getStringExtra("search method");
        // The code bleow is from location pick, it may cause bug
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        //recordGeoLocation = (GeoLocation) getIntent().getSerializableExtra("ComefromLocationPicker");
        if (search_method.equals("problem")) {
            // if search method equals problem, then using functions inside problem search controller
            key_word_search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder msg_editor = new AlertDialog.Builder(SearchActivity.this);
                    final EditText user_input = new EditText(SearchActivity.this);
                    msg_editor.setView(user_input);
                    msg_editor.setCancelable(true);
                    msg_editor.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            final String msg = user_input.getText().toString();
                            // get key words
                            String[] items = (String[]) Arrays.asList(msg.split("\\s*,\\s*")).toArray();
                            // call key words search of problemSearch controller
                            problems = ProblemSearchController.KeywordSearch(items);
                            Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("problems",problems);
                            // notice : only implemented record now, will implement if check statement for implement
                            // problem in the future
                            intent.putExtra("type","problem");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    msg_editor.show();
                }
            });
            body_search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder msg_editor = new AlertDialog.Builder(SearchActivity.this);
                    final EditText user_input = new EditText(SearchActivity.this);
                    msg_editor.setView(user_input);
                    msg_editor.setCancelable(true);
                    msg_editor.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            final String msg = user_input.getText().toString();
                            // get key words
                            // call key words search of problemSearch controller
                            problems=ProblemSearchController.BodySearch(msg);
                            Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("problems",problems);
                            // notice : only implemented record now, will implement if check statement for implement
                            // problem in the future
                            intent.putExtra("type","problem");
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                    msg_editor.show();
                }
            });
            geo_search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling

                        return;
                    }

                    //locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(builder.build(SearchActivity.this), PROBLEM_PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }
            });

        } else {
            // if search method equals problem, then using functions inside record search controller
            key_word_search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder msg_editor = new AlertDialog.Builder(SearchActivity.this);
                    final EditText user_input = new EditText(SearchActivity.this);
                    msg_editor.setView(user_input);
                    msg_editor.setCancelable(true);
                    msg_editor.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            final String msg = user_input.getText().toString();
                            // get key words
                            String[] items = (String[]) Arrays.asList(msg.split("\\s*,\\s*")).toArray();
                            // call key words search of problemSearch controller

                            records = RecordSearchController.KeywordSearch(items);
                            for(Record each:records)
                            {
                                record_string.add(each.getTitle());
                            }
                            Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("records",record_string);
                            // notice : only implemented record now, will implement if check statement for implement
                            // problem in the future
                            intent.putExtra("type","record");
                            intent.putExtras(bundle);
                            startActivity(intent);

                        }
                    });
                    msg_editor.show();
                }
            });

            body_search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder msg_editor = new AlertDialog.Builder(SearchActivity.this);
                    final EditText user_input = new EditText(SearchActivity.this);
                    msg_editor.setView(user_input);
                    msg_editor.setCancelable(true);
                    msg_editor.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            //ArrayList<String> record_string_list = new ArrayList<>();
                            final String msg = user_input.getText().toString();
                            // get key words
                            // call key words search of problemSearch controller
                            records = RecordSearchController.BodySearch(msg);
                            for(Record each:records)
                            {
                                record_string.add(each.getTitle());
                            }
                            Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("records",record_string);
                            // notice : only implemented record now, will implement if check statement for implement
                            // problem in the future
                            intent.putExtra("type","record");
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    msg_editor.show();
                }
            });

            geo_search_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(SearchActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling

                        return;
                    }

                    //locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    try {
                        startActivityForResult(builder.build(SearchActivity.this), RECORD_PLACE_PICKER_REQUEST);
                    } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                        e.printStackTrace();
                    }
                }


            });
        }
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == RECORD_PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(this,data);
                location = place.getLatLng();
                records=RecordSearchController.GeoSearch(location.latitude,location.longitude);
     /*           Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("records",records);
                // notice : only implemented record now, will implement if check statement for implement
                // problem in the future
                intent.putExtra("type","record");
                intent.putExtras(bundle);
                startActivity(intent);*/
                for(Record each:records)
                {
                    record_string.add(each.getTitle());
                }
                Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("records",record_string);
                // notice : only implemented record now, will implement if check statement for implement
                // problem in the future
                intent.putExtra("type","record");
                intent.putExtras(bundle);
                startActivity(intent);

                //location is geolocation...
                //can be converted to string and double...

            }
        }
        else if (requestCode == PROBLEM_PLACE_PICKER_REQUEST)
        {
            if(resultCode==RESULT_OK)
            {
                Place place = PlacePicker.getPlace(this,data);
                location = place.getLatLng();
                problems=ProblemSearchController.GeoSearch(location.latitude,location.longitude);
                Intent intent = new Intent(SearchActivity.this,ShowSearchResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problems",problems);
                // notice : only implemented record now, will implement if check statement for implement
                // problem in the future
                intent.putExtra("type","problem");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }


    /**
     * This fucntion is to get your current geoLocation by gps
     * this function is not finished as we will be finished in project 5
     */
/*    private void configureButton() {
        //button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get conttrunt geolocation
                locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
            }
        });
    }*/

}
