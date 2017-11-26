package com.example.jeff.myapplication;

/**
 * Created by jeff on 11/18/17.
 */

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;



public class ExpenseDialog extends DialogFragment {

    private EditText editName;
    private EditText editAmount;
    private dialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.new_expense, null);

        //builder.setMessage(R.string.new_charge)
        builder.setView(view)
                .setTitle("New Charge")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // user confirms dialog
                        String name = editName.getText().toString();
                        String amount = editAmount.getText().toString();
                        listener.applyTexts(name, amount);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        editName = view.findViewById(R.id.Budget_Name);
        editAmount = view.findViewById(R.id.Budget_Amount);

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (dialogListener) context;
        }
        catch(ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement dialogListener");
        }
    }


    public interface dialogListener{
        void applyTexts(String name, String amount);
    }
}
