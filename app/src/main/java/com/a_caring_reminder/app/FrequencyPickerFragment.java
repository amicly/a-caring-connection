package com.a_caring_reminder.app;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

public class FrequencyPickerFragment extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction



        int frequencyIndex = 0;
        String[] frequencyArray = getActivity().getResources().getStringArray(R.array.frequency);
        EditText editFrequency = (EditText) getActivity().findViewById(R.id.edit_habit_recurrence);


        for( int i = 0; i < frequencyArray.length; i++)
        {

            Log.i("ACR", "Frequency Index is " + i);
            Log.i("ACR", "Frequency Index Value is " + frequencyArray[i]);
            Log.i("ACR", "Recurrence Text is " + editFrequency.getText().toString());

            if (frequencyArray[i].equals(editFrequency.getText().toString()))
            {
                Log.i("ACR", "Setting frequency index to " + i);
                frequencyIndex = i;
                break;

            }

        }


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.choose_habit_frequency).setSingleChoiceItems(frequencyArray, frequencyIndex, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String[] frequencyArray = getActivity().getResources().getStringArray(R.array.frequency);
                String selectedString = frequencyArray[which];


                EditText editFrequency = (EditText) getActivity().findViewById(R.id.edit_habit_recurrence);
                editFrequency.setText(selectedString);

                dialog.dismiss();

            }});


        // Create the AlertDialog object and return it
        return builder.create();
    }


}