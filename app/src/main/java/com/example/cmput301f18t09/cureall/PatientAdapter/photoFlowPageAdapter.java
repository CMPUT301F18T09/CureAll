/**
 * Class name: photoFlowPageAdapter
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.PatientAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * This adapter is used for pager slide view
 * It is a little different than the recycleviewa adapter, as it do not need a viewholder to carry a cardview inside a recycleview
 */

//TODO change data type here!!!!string to bitmap arraylist
public class photoFlowPageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> imageUrls;
    private Bitmap bitmap;
    private String stringbitmap;

    /**
     * the constructor get the image source and context
     * @param context
     * @param imageUrls
     */
    public photoFlowPageAdapter(Context context, ArrayList<String> imageUrls){
        mContext = context;
        this.imageUrls = imageUrls;
    }

    /**
     * count the images in pager view
     * @return
     */
    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    /**
     * load the images, into a imageview of pager viewer
     * The Picasso library is used to help us load relative data
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        stringbitmap = imageUrls.get(position);
        try {
            byte [] encodeByte= Base64.decode(stringbitmap,Base64.DEFAULT);
            bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch(Exception e) {
            e.getMessage();
        }

        imageView.setImageBitmap(bitmap);
        //imageView.setImageResource(mImageIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        container.addView(imageView);

        return imageView;
    }

    /**
     * when you slide, it will show you another image, but not this one
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
         container.removeView((ImageView)object);
    }
}
