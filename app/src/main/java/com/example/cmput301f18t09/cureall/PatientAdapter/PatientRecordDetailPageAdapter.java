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
import com.example.cmput301f18t09.cureall.ProviderAdapter.RecordDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

public class PatientRecordDetailPageAdapter extends RecyclerView.Adapter<PatientRecordDetailPageAdapter.viewHolder> {
    private ArrayList<AllKindsOfPhotos> photosArrayList;
    //photo upload as bitmap test...
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;

    public PatientRecordDetailPageAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls) {
        mNames = names;
        mImageUrls = imageUrls;
        mContext = context;
    }
    // contructor ends

    public static class viewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public viewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);

        }
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_each_photo_in_recycle_view,viewGroup,false);

        return new viewHolder(v);
    }

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
/**
 @Override
 public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
 View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_each_photo_in_recycle_view,viewGroup,false);
 viewHolder vh2 = new viewHolder(v);
 return vh2;
 }
 public RecordDetailPageAdapter(ArrayList<AllKindsOfPhotos> photosList){
 photosArrayList = photosList;
 }
 @Override
 public void onBindViewHolder(viewHolder viewHolder, int position) {
 AllKindsOfPhotos currentPhoto = photosArrayList.get(position);
 viewHolder.imageButton.setOnClickListener(new View.OnClickListener() {
 @Override
 public void onClick(View v) {
 Toast.makeText(v.getContext(),"firstPhoto",Toast.LENGTH_SHORT).show();
 }
 });

 }
 @Override
 public int getItemCount() {
 return photosArrayList.size();
 }
 }
 **/