package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.widget.GridView;

import com.example.cmput301f18t09.cureall.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * photo slide show activity
 */
public class PhotoGallary extends AppCompatActivity {
    private ArrayList<Bitmap> bitmaps = new ArrayList<>();
    private GridView gridView;
    private int columnWidth;
    public static final int NUM_OF_COLUMNS = 1;
    public static final int GRID_PADDING = 5;

    /**
     *
     * @param savedInstanceState (build in)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallary);
        getDataFromProblemDetailPage();
        gridView = (GridView) findViewById(R.id.gridView);
        InitilizeGridLayout();
        ImageAdapter imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);
        imageAdapter.setImage(bitmaps);

    }

    /**
     * get data from ProblemDetailPage
     */
    public void getDataFromProblemDetailPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("allPhotos",null);
        Type type = new TypeToken<ArrayList<Bitmap>>(){}.getType();
        bitmaps  = gson.fromJson(json,type);
    }

    /**
     * initialization
     */
    private void InitilizeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) (((getScreenWidth()) - ((NUM_OF_COLUMNS + 1) * padding)) / NUM_OF_COLUMNS);

        gridView.setNumColumns(NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,(int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    /**
     *
     * @return screen width
     */
    @SuppressLint("NewApi")
    public int getScreenWidth() {
        int columnWidth ;
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }
}