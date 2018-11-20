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
/**
 * This is an adapter for recycleview used for presenting patient's problems
 * It get a arraylist of problems that present them in a order by time
 * In this version, the preview in recycleview of each problem is not fit very well,
 * Sometimes, the problem has the preview information overlap with each other, and hard to identify the records between each other
 * The improvement can be achieved by next project
 */
public class PatientProblemListPageAdapter extends RecyclerView.Adapter<PatientProblemListPageAdapter.viewHolder> {
    private ArrayList<Problem> problemArrayList;
    private OnItemClickListener mlistener;
    /**
     * The view holder is used to carry a card view
     * which is used to contain the preview information of a problem
     * Once you click the buttons on each card view of a problem, it will show problem details or
     * delete the problems...
     */
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
            /**
             * Here, we set the button listener of each button
             */
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

            problemDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener2 != null){
                        listener2.onDeleteClick(position);
                    }
                }
            });



        }
    }
    /**
     * create the interface that allows the button can be clicked
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDetailClick(int position);
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener  listener){
        mlistener = listener;
    }
    /**
     * constructor of this adapter
     * @param problemList
     */
    public PatientProblemListPageAdapter(ArrayList<Problem> problemList){
        problemArrayList = problemList;


    }
    /**
     * A create of viewholder (or card view) layout in recycleview, based on the corresponding xml file.
     * @param viewGroup
     * @param i
     * @return
     */
    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.patient_each_problem_in_recycle_view,viewGroup,false);
        viewHolder vh2 = new viewHolder(v, mlistener);
        return vh2;
    }



    /**
     * for each item, set its preview information such as title and date...
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        Problem currentProblem = problemArrayList.get(position);
        viewHolder.title.setText(currentProblem.getTitle());
        if(currentProblem!= null){
            /**
             * get the number of records for this problem
             * This part has not been finished yet, so you cant see how many records, a problem has.
             * It will be finished at the next project
             */
        }
        viewHolder.date.setText(currentProblem.getTime());
        // viewHolder.date.setText(currentProblem.getTime());
        //viewHolder.numberOfRecords.setText(currentProblem.get(...));

    }
    @Override
    public int getItemCount() {


        return problemArrayList.size();
    }
}