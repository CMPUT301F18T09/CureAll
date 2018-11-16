package com.example.cmput301f18t09.cureall.ProviderAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;

public class ProblemDetailPageAdapter extends RecyclerView.Adapter<ProblemDetailPageAdapter.viewHolder> {
    private ArrayList<Record> recordsArrayList;

    public static class viewHolder extends RecyclerView.ViewHolder{
        public ImageView titleSymbol;
        public TextView dateComment, recordTitle;
        public ImageButton showRecordDetailButton;
        public viewHolder( View itemView) {
            super(itemView);
            titleSymbol = itemView.findViewById(R.id.titleSymbol);
            dateComment = itemView.findViewById(R.id.dateComment);
            recordTitle = itemView.findViewById(R.id.recordTitle);
            showRecordDetailButton = (ImageButton) itemView.findViewById(R.id.showRecordDetailButton);

        }
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_each_record_in_recycle_view,viewGroup,false);
        viewHolder vh2 = new viewHolder(v);
        return vh2;
    }
    public ProblemDetailPageAdapter(ArrayList<Record> recordsList){
        recordsArrayList = recordsList;
    }
    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        Record currentRecord = recordsArrayList.get(position);
        viewHolder.recordTitle.setText(currentRecord.getTitle());
        viewHolder.showRecordDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),"pressed",Toast.LENGTH_SHORT).show();
            }
        });


    }
    @Override
    public int getItemCount() {
        return recordsArrayList.size();
    }
}
