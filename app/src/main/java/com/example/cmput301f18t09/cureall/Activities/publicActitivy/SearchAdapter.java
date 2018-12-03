package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchAdapter extends ArrayAdapter<String> {


    private static final String TAG = "PersonListAdapter";

    private static Date date;


    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    /**
     * Holds variables in a View
     */
    private static class ViewHolder {
        TextView name;
        TextView date;
        TextView msg;
    }

    /*
     * defult constructor
     * */
    public SearchAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        //get the information of an item which is clicked
        String name = getItem(position);
/*        date = getItem(position).getDate();
        String msg = getItem(position).getMessage();*/




        final View result;

        //ViewHolder object
        ViewHolder holder;


        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            //  holder.name = (TextView) convertView.findViewById(R.id.textView1);
            holder.date = (TextView) convertView.findViewById(R.id.textView2);
            //holder.msg = (TextView) convertView.findViewById(R.id.textView3);

            result = convertView;

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();;
        }

        holder.date.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


            }
        });

        lastPosition = position;

        DateFormat pattern = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");


        //holder.name.setText(name);
        holder.date.setText(name);
        //holder.msg.setText(name);

        return convertView;
    }

}
