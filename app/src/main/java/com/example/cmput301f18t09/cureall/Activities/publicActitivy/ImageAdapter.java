package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.BitSet;

public class ImageAdapter extends BaseAdapter{
    private Context context;
    public ArrayList<Bitmap> photos = new ArrayList<>();

    public ImageAdapter(Context c){
        context = c;
    }

    public void setImage(ArrayList<Bitmap>photosFromAllProblems){
        photos = photosFromAllProblems;
    }
    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public Object getItem(int position) {
        return photos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(photos.get(position));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        Integer height = getScreenWidth();
        imageView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, height));
        return imageView;
    }
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
