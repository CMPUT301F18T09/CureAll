package com.example.cmput301f18t09.cureall.ProviderAdapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

/**
 * This a an adapter for recycleview used for presenting a list of patients
 * It get an arraylist of patients that has been added by provider by time
 *
 */
public class ProviderMainPageAdapter extends RecyclerView.Adapter<ProviderMainPageAdapter.viewHolder> {
    private ArrayList<Patient> patientArrayList;
    private OnItemClickListener mlistener;
    public static class viewHolder extends RecyclerView.ViewHolder{
        public ImageButton showProblemButton, showLocationButton;
        public TextView PatientName;
        private AdapterView.OnItemClickListener mlistener;
        /**
         * The view holder is used to carry a card view
         * which is used to contain the preview information of a patient
         * Once you click each patient inside recycleview, it will show you the patient information
         * Once you click the button beside the patient name, it will bring you to a page showing a list of patient's problems
         */
        public viewHolder( View itemView,final OnItemClickListener listener2) {
            super(itemView);
            showLocationButton = itemView.findViewById(R.id.showLocationButton);
            showProblemButton = itemView.findViewById(R.id.showProblemButton);
            PatientName = itemView.findViewById(R.id.patientName);
            /**
             * Here, we set the item listener of each button, and the item itself
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

            showProblemButton.setOnClickListener(new View.OnClickListener() {
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
     * create the interface that allows the item and the button inside the item can be clicked
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.provider_each_patient_in_recycle_view,viewGroup,false);
        viewHolder vh = new viewHolder(v,mlistener);
        return vh;
    }
    /**
     * constructor of this adapter
     * @param patientsList
     */
    public ProviderMainPageAdapter(ArrayList<Patient> patientsList){
        patientArrayList = patientsList;
    }
    /**
     * for each item, set its preview information such as patient's name...
     * @param viewHolder
     * @param position
     */
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
