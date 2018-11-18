package com.example.cmput301f18t09.cureall.PatientAdapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

public class PatientProblemListPageAdapter extends RecyclerView.Adapter<PatientProblemListPageAdapter.viewHolder> {
    private ArrayList<Problem> problemArrayList;
    private OnItemClickListener mlistener;

    public class viewHolder extends RecyclerView.ViewHolder{
        public Button problemDetailButton, problemDeleteButton;
        public TextView title,date,numberOfRecords;
        private AdapterView.OnItemClickListener mlistener;
        public viewHolder( View itemView, final OnItemClickListener listener2) {
            super(itemView);
            problemDetailButton = (Button) itemView.findViewById(R.id.problemDetailButton);
            problemDeleteButton = (Button) itemView.findViewById(R.id.problemDeleteButton);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            numberOfRecords = itemView.findViewById(R.id.numberOfRecords);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener2 != null){
                        listener2.onItemClick(position);
                    }
                }
            });

            problemDetailButton.setOnClickListener(new View.OnClickListener() {
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
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDetailClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener  listener){
        mlistener = listener;
    }

    public PatientProblemListPageAdapter(ArrayList<Problem> problemList){
        problemArrayList = problemList;
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_each_problem_in_recycle_view,viewGroup,false);
        viewHolder vh2 = new viewHolder(v, mlistener);
        return vh2;
    }




    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        Problem currentProblem = problemArrayList.get(position);
        viewHolder.title.setText(currentProblem.getTitle());
        // viewHolder.date.setText(currentProblem.getTime());
        //viewHolder.numberOfRecords.setText(currentProblem.get(...));

    }
    @Override
    public int getItemCount() {
        return problemArrayList.size();
    }
}