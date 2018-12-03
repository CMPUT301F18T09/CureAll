/**
 * Class name: RecordDetailPageAdapter
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.ProviderAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cmput301f18t09.cureall.model.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;
/**
 * The record details has some arraylist of photos, such as recordtracking photos
 * Therefore, this is an Adapter for recycleview used for presenting an arraylist of records of a paticular's problem
 */
public class RecordDetailPageAdapter extends RecyclerView.Adapter <RecordDetailPageAdapter.viewHolder> {
    private ArrayList<AllKindsOfPhotos> mphotosArrayList;
    //photo upload as bitmap test...

    private Context mContext;
    /**
     * The contructor of adapter
     * @param context
     * @param
     */
    public RecordDetailPageAdapter(Context context, ArrayList<AllKindsOfPhotos> photosArrayList) {
        mphotosArrayList = photosArrayList;
        mContext = context;
    }
    // contructor ends
    /**
     * the view holder is used to contain the recordtracking photos
     * for each item in recycleview, it shows a single photo.
     * For provider version, the photo can not be shown yet.
     * This problem can be fixed in next project
     */
    public static class viewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public viewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
    /**
     * define the viewholder format based on a source of xml file
     * It define the layour of a image in each item of recycleview
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_each_photo_in_recycle_view,viewGroup,false);

        return new viewHolder(v);
    }
    /**
     * load each image based on their url and name, and put it into the item inside the recycleview
     * Glide is used to help us load the image
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(viewHolder viewHolder, final int position) {
        Log.i("Show","show pic");
        AllKindsOfPhotos photo = mphotosArrayList.get(position);
        String stringbitmap = photo.getPhotoLocation();
        Bitmap bitmap;
        try {
            byte [] encodeByte= Base64.decode(stringbitmap,Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            viewHolder.imageView.setImageBitmap(bitmap);
        } catch(Exception e) {
            e.getMessage();
        }
    }

    @Override
    public int getItemCount() {
        return mphotosArrayList.size();
    }
}


