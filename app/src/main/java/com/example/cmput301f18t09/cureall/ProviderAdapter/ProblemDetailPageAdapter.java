/**
 * Class name: ProblemDetailPageAdapter
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.ProviderAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;

/**
 * THIS IS THE PROVDER VERSION OF VIEWING PROBLEM DETAIL FORM A PATIENT
 * The problem Details means presents all information for a problems, which means it will present a set of records...
 * This is an Adapter for recycleview used for presenting patient's records
 * It get a arraylist of record that present them in a order by time
 * In this version, the preview in recycleview of each record is not fit very well,
 * Sometimes, the record has the preview information overlap with each other, and hard to identify the records between each other
 * The improvement can be achieved by next project
 */
public class ProblemDetailPageAdapter extends RecyclerView.Adapter<ProblemDetailPageAdapter.viewHolder> {
    private ArrayList<Record> recordsArrayList;
    private OnItemClickListener mlistener;
    public static class viewHolder extends RecyclerView.ViewHolder{
        public ImageView titleSymbol;
        public TextView dateComment, recordTitle;
        public ImageButton showRecordDetailButton;
        /**
         * The view holder is used to carry a card view
         * which is used to contain the preview information of a record
         * Once you click a button on each record, it will show you the detail of this record
         */
        public viewHolder( View itemView,final OnItemClickListener listener2) {
            super(itemView);
            titleSymbol = itemView.findViewById(R.id.titleSymbol);
            dateComment = itemView.findViewById(R.id.dateComment);
            recordTitle = itemView.findViewById(R.id.recordTitle);

            showRecordDetailButton = (ImageButton) itemView.findViewById(R.id.showRecordDetailButton);

            showRecordDetailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener2 != null){
                        listener2.onDetailClick(position);
                    }
                }
            });

        }
    }
    /**
     * make an interface for each item in recycleview, such that they are able to
     * be clicked in activity...
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDetailClick(int position);
    }
    /**
     * inilistialize a listener for each item in recycleview
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener  listener){
        mlistener = listener;
    }
    /**
     * A create of viewholder (or card view) layout in recycleview, based on the corresponding xml file.
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_each_record_in_recycle_view,viewGroup,false);
        viewHolder vh2 = new viewHolder(v,mlistener);
        return vh2;
    }
    /**
     * A constructor of Adapter
     * @param recordsList
     */
    public ProblemDetailPageAdapter(ArrayList<Record> recordsList){
        recordsArrayList = recordsList;
    }
    /**
     * for each item, set its preview information such as title and date...
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        Record currentRecord = recordsArrayList.get(position);
        viewHolder.recordTitle.setText(currentRecord.getTitle());
        /**
         * You can only view the record time, and descriptions in record detail page
         * The preview function is not finished yet, only the record title will be presented.
         * There are some bugs about the layout of cardview, sometimes the words may overlap with each other
         * The bug will be fixed at next project, or final version
         */

    }
    @Override
    public int getItemCount() {
        return recordsArrayList.size();
    }
}

