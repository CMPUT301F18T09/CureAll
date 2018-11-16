package com.example.cmput301f18t09.cureall.PatientAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class photoFlowPageAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> imageUrls;
    public photoFlowPageAdapter(Context context, ArrayList<String> imageUrls){
        mContext = context;
        this.imageUrls = imageUrls;
    }


    @Override
    public int getCount() {
        return imageUrls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        Picasso.get()
                .load(imageUrls.get(position))
                .into(imageView);
        //imageView.setImageResource(mImageIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
         container.removeView((ImageView)object);
    }
}
