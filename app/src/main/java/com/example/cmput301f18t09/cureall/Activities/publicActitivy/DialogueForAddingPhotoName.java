package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmput301f18t09.cureall.R;

public class DialogueForAddingPhotoName extends AppCompatDialogFragment{
    private EditText editText2;
    private DialogListener listener;//comment
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.photo_name_input_dialogue,null);
        builder.setView(view)
                .setTitle("Give a name")
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = editText2.getText().toString();
                        listener.applyTexts(username);
                    }
                });
    editText2 = view.findViewById(R.id.editText2);
    return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener =(DialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    public interface DialogListener{
        void applyTexts(String name);
    }
}
