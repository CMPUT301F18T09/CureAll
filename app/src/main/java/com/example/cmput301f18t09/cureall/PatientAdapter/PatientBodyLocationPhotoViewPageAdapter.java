/**
 * Class name: PatientBodyLocationPhotoViewPageAdapter
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.PatientAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;
/**
 * This is an Adapter for recycleview used for presenting recordTracking Photos
 * It get the photo names and urls as resources
 * In this version, the photos resources are from local
 * The improvement can be achieved by changing photo recources, from online resources..
 */
public class PatientBodyLocationPhotoViewPageAdapter extends RecyclerView.Adapter<PatientBodyLocationPhotoViewPageAdapter.viewHolder> {
    private ArrayList<AllKindsOfPhotos> photosArrayList;
    //photo upload as bitmap test...
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    /**
     * A constructor of Adapter
     * @param context
     * @param names
     * @param imageUrls
     */
    public PatientBodyLocationPhotoViewPageAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls) {
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
    }
    // contructor ends
    /**
     * The view holder is used to carry a card view
     * which is used to present the photo object
     */
    public static class viewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public viewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }
    /**
     * set the corresponding xml file for this activity
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_each_photo_in_recycle_view,viewGroup,false);

        return new viewHolder(v);
    }
    /**
     * put the image into the cardview holder
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(viewHolder viewHolder, final int position) {
        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,mNames.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }
}