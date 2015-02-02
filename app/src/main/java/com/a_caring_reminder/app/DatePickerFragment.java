
package com.a_caring_reminder.app;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {


    private Calendar mCalendar;
    protected Date StartDate;
    private int year;
    private int day;
    private int month;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker


        try {

             StartDate = new SimpleDateFormat("MM/dd/yyyy").parse(getArguments().getString("DATE"));

            } catch (java.text.ParseException e) {

        }


        mCalendar = Calendar.getInstance();
        mCalendar.setTime(StartDate);
        year = mCalendar.get(Calendar.YEAR);
        month = mCalendar.get(Calendar.MONTH);
        day = mCalendar.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {

        //Add one to month as it is zero based

        month += 1;

        EditText habitDate = (EditText) getActivity().findViewById(R.id.text_view_habit_date);
        habitDate.setText(month +"/" + day + "/" + year );




    }
}