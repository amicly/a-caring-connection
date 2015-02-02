package com.a_caring_reminder.app;



import android.app.Dialog;

import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;

import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, getArguments().getInt("HOURS"), getArguments().getInt("MINUTES"),
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        TextView habitTime = (TextView) getActivity().findViewById(R.id.text_view_habit_time);
        habitTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));

    }
}