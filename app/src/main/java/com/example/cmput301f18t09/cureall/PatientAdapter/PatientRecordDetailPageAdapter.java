/**
 * Class name: PatientRecordDetailPageAdapter
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.PatientAdapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;
/**
 * The record details has some arraylist of photos, such as recordtracking photos
 * Therefore, this is an Adapter for recycleview used for presenting an arraylist of records of a paticular's problem
 */
public class PatientRecordDetailPageAdapter extends RecyclerView.Adapter<PatientRecordDetailPageAdapter.viewHolder> {
    private ArrayList<AllKindsOfPhotos> mphotosArrayList;
    private AllKindsOfPhotos photo;
    //photo upload as bitmap test...
    private ArrayList<String> mImageBitmaps = new ArrayList<>();/**new*/
    private Context mContext;
    private Bitmap bitmap;
    private String stringbitmap, photoName;
    private OnItemClickListener mlistener;

    /**
     * The contructor of adapter
     */
    public PatientRecordDetailPageAdapter(Context context,ArrayList<AllKindsOfPhotos> photosArrayList) {
        mContext = context;
        mphotosArrayList = photosArrayList;/**new*/
    }
    // contructor ends
    /**
     * the view holder is used to contain the recordtracking photos
     * for each item in recycleview, it shows a single photo.
     */
    public static class viewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public viewHolder(View itemView, final OnItemClickListener listener2) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener2 != null){
                        listener2.onItemClick(position);
                    }
                }
            });

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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_each_photo_in_recycle_view,viewGroup,false);

        return new viewHolder(v,mlistener);
    }
    /**
     * load each image based on their url and name, and put it into the item inside the recycleview
     * Glide is used to help us load the image
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final viewHolder viewHolder, final int position) {
        Log.i("Show","show pic");
        photo = mphotosArrayList.get(position);
        stringbitmap = photo.getPhotoLocation();
        photoName = photo.getPhotoType();
        try {
            byte [] encodeByte= Base64.decode(stringbitmap,Base64.DEFAULT);
            bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
        }
        viewHolder.imageView.setImageBitmap(bitmap);

    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

    @Override
    public int getItemCount() {
        return mphotosArrayList.size();
    }

}