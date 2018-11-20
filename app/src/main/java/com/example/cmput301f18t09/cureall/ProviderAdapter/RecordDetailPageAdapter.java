package com.example.cmput301f18t09.cureall.ProviderAdapter;

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
 * The record details has some arraylist of photos, such as recordtracking photos
 * Therefore, this is an Adapter for recycleview used for presenting an arraylist of records of a paticular's problem
 */
public class RecordDetailPageAdapter extends RecyclerView.Adapter <RecordDetailPageAdapter.viewHolder> {
    private ArrayList<AllKindsOfPhotos> photosArrayList;
    //photo upload as bitmap test...
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    /**
     * The contructor of adapter
     * @param context
     * @param names
     * @param imageUrls
     */
    public RecordDetailPageAdapter(Context context, ArrayList<String> names, ArrayList<String> imageUrls) {
        mNames = names;
        mImageUrls = imageUrls;
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


