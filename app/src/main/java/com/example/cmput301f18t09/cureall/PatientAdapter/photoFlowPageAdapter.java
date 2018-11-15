package com.example.cmput301f18t09.cureall.PatientAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cmput301f18t09.cureall.R;

public class photoFlowPageAdapter extends PagerAdapter {
    private Context mContext;
    private int[] mImageIds = new int[]{
          R.drawable.paper_doll_test,R.drawable.paper_doll_test
    };
    public photoFlowPageAdapter(Context context){
        mContext = context;
    }


    @Override
    public int getCount() {
        return mImageIds.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(mImageIds[position]);
        container.addView(imageView,0);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
         container.removeView((ImageView)object);
    }
}
