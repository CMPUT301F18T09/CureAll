package com.example.cmput301f18t09.cureall.ProviderAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;
/**
 * THIS IS PROVIDER VERSION OF VIEWING PROBLEM LIST
 * This is an adapter for recycleview used for presenting patient's problems
 * It get a arraylist of problems that present them in a order by time
 * In this version, the preview in recycleview of each problem is not fit very well,
 * Sometimes, the problem has the preview information overlap with each other, and hard to identify the records between each other
 * The improvement can be achieved by next project
 */
public class ProblemListPageAdapter extends RecyclerView.Adapter<ProblemListPageAdapter.viewHolder> {
    private ArrayList<Problem> problemArrayList;
    private OnItemClickListener mlistener;
    public static class viewHolder extends RecyclerView.ViewHolder{
        public ImageView problemSymbol;
        public TextView ProblemTitle;
        private AdapterView.OnItemClickListener mlistener;
        /**
         * The view holder is used to carry a card view
         * which is used to contain the preview information of a problem
         * Once you click each problem inside recycleview, it will show problem details
         * Provider version can not delete patient's problem
         */
        public viewHolder( View itemView,final OnItemClickListener listener2) {
            super(itemView);
            problemSymbol = itemView.findViewById(R.id.problemSymbol);
            ProblemTitle = itemView.findViewById(R.id.dateComment);
            /**
             * Here, we set the item listener of each button
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


        }
    }
    /**
     * create the interface that allows the item can be clicked
     */
    public interface OnItemClickListener{
        void onItemClick(int position);
        void onDetailClick(int position);
    }

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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_each_problem_in_recycle_view,viewGroup,false);
        viewHolder vh2 = new viewHolder(v,mlistener);
        return vh2;
    }
    /**
     * constructor of this adapter
     * @param problemList
     */
    public ProblemListPageAdapter(ArrayList<Problem> problemList){
        problemArrayList = problemList;
    }
    /**
     * for each item, set its preview information such as title and date...
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        Problem currentProblem = problemArrayList.get(position);
        viewHolder.ProblemTitle.setText(currentProblem.getTitle());
        /**
         * get the number of records for this problem
         * This part has not been finished yet, so you cant see how many records, a problem has.
         * It will be finished at the next project
         */
    }
    @Override
    public int getItemCount() {
        return problemArrayList.size();
    }
}
