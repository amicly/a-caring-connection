package com.a_caring_reminder.app;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A fragment representing a single habit detail screen.
 * This fragment is either contained in a {@link HabitListActivity}
 * in two-pane mode (on tablets) or a {@link HabitDetailActivity}
 * on handsets.
 */
public class HabitDetailFragment extends Fragment {

    //SQLite class
    AcrDB acrDB;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_HABIT_ID = "item_id";
    //public static final String SPINNER_ID = "spinner_id";


    /**
     * The id to query for the data the fragment is presenting.
     */
    private String mItem;
    private String mTime;
    private String mTitle;
    private String mRecurrence;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public HabitDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create the database here so that it gets the Activity Context.
        acrDB = new AcrDB(getActivity());
        AcrQuery query = new AcrQuery(acrDB);

        if (getArguments().containsKey(ARG_HABIT_ID)) {
            // Load the content using a query with an id specified by the fragment

            mTitle= query.getHabitTitle(getArguments().getString(ARG_HABIT_ID));
            mItem = query.getHabitDetail(getArguments().getString(ARG_HABIT_ID));
            mTime = query.getHabitTime(getArguments().getString(ARG_HABIT_ID));
            mRecurrence = query.getHabitRecurrenceDetails(getArguments().getString(ARG_HABIT_ID));

            getActivity().setTitle(query.getHabitTitle(getArguments().getString(ARG_HABIT_ID)) + " Habit Details");


        }






    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_habit_detail, container, false);

        // Show the content as text in a TextView.
        if (mItem != null) {

            ((EditText) rootView.findViewById(R.id.edit_text_habit_title)).setText(mTitle);
            ((EditText) rootView.findViewById(R.id.edit_text_habit_description)).setText(mItem);
            ((EditText) rootView.findViewById(R.id.text_view_habit_time)).setText(mTime);
            ((EditText) rootView.findViewById(R.id.text_view_habit_date)).setText(mRecurrence.split("\\s+")[1]);

        } else {

            ((EditText) rootView.findViewById(R.id.text_view_habit_date)).setText(String.valueOf( (Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()).get(Calendar.MONTH)) +1) +"/"+String.valueOf( Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()).get(Calendar.DAY_OF_MONTH))+"/"+String.valueOf( Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()).get(Calendar.YEAR)));

            DateFormat dateFormat = new SimpleDateFormat("HH:mm");

            long currentTime = new Date().getTime();
            Date date = new Date(currentTime + 600000);
            mTime = dateFormat.format(date);
            ((EditText) rootView.findViewById(R.id.text_view_habit_time)).setText(mTime);

        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        HabitDetailActivity mActivityReference = (HabitDetailActivity) getActivity();

        mActivityReference.setHabitDetailFields();


    }
}



