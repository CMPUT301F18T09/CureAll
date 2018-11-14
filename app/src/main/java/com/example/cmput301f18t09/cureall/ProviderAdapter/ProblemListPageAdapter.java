package com.example.cmput301f18t09.cureall.ProviderAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

public class ProblemListPageAdapter extends RecyclerView.Adapter<ProblemListPageAdapter.viewHolder> {
    private ArrayList<Problem> problemArrayList;

    public static class viewHolder extends RecyclerView.ViewHolder{
        public ImageView problemSymbol;
        public TextView ProblemTitle;
        public viewHolder( View itemView) {
            super(itemView);
            problemSymbol = itemView.findViewById(R.id.problemSymbol);
            ProblemTitle = itemView.findViewById(R.id.dateComment);

        }
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_each_problem_in_recycle_view,viewGroup,false);
        viewHolder vh2 = new viewHolder(v);
        return vh2;
    }
    public ProblemListPageAdapter(ArrayList<Problem> problemList){
        problemArrayList = problemList;
    }
    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        Problem currentProblem = problemArrayList.get(position);
        viewHolder.ProblemTitle.setText(currentProblem.getTitle());
    }
    @Override
    public int getItemCount() {
        return problemArrayList.size();
    }
}
