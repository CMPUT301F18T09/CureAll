package com.example.cmput301f18t09.cureall.ProviderAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

public class ProviderMainPageAdapter extends RecyclerView.Adapter<ProviderMainPageAdapter.viewHolder> {
    private ArrayList<Patient> patientArrayList;

    public static class viewHolder extends RecyclerView.ViewHolder{
        public ImageButton showProblemButton, showLocationButton;
        public TextView PatientName;
        public viewHolder( View itemView) {
            super(itemView);
            showLocationButton = itemView.findViewById(R.id.showLocationButton);
            showProblemButton = itemView.findViewById(R.id.showProblemButton);
            PatientName = itemView.findViewById(R.id.patientName);

        }
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_each_patient_in_recycle_view,viewGroup,false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }
    public ProviderMainPageAdapter(ArrayList<Patient> patientsList){
        patientArrayList = patientsList;
    }
    @Override
    public void onBindViewHolder(viewHolder viewHolder, int position) {
        Patient currentPatient = patientArrayList.get(position);
        //*viewHolder...
        viewHolder.PatientName.setText(currentPatient.getUsername());


    }

    @Override
    public int getItemCount() {
        return patientArrayList.size();
    }
}
