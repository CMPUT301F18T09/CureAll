package com.example.cmput301f18t09.cureall.PatientAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

public class PatientProblemListPageAdapter extends RecyclerView.Adapter<PatientProblemListPageAdapter.viewHolder> {
    private ArrayList<Problem> problemArrayList;

    public static class viewHolder extends RecyclerView.ViewHolder{
        public ImageButton problemDetailButton, problemDeleteButton;
        public TextView title,date,numberOfRecords;
        public viewHolder( View itemView) {
            super(itemView);
            problemDetailButton = (ImageButton) itemView.findViewById(R.id.problemDetailButton);
            problemDeleteButton = (ImageButton) itemView.findViewById(R.id.problemDeleteButton);
            title = itemView.findViewById(R.id.title);
            date = itemView.findViewById(R.id.date);
            numberOfRecords = itemView.findViewById(R.id.numberOfRecords);

        }
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_each_problem_in_recycle_view,viewGroup,false);
        viewHolder vh2 = new viewHolder(v);
        return vh2;
    }
    public PatientProblemListPageAdapter(ArrayList<Problem> problemList){
        problemArrayList = problemList;
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