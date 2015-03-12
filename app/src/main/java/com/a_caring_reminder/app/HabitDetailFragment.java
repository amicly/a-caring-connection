package com.a_caring_reminder.app;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a_caring_reminder.app.connection_management.AlarmReceiver;
import com.a_caring_reminder.app.data.AcrDB;
import com.a_caring_reminder.app.data.AcrDBContract;
import com.a_caring_reminder.app.data.AcrQuery;
import com.a_caring_reminder.app.models.ScheduledAlarm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A fragment representing a single habit detail screen.
 * This fragment is either contained in a {@link HabitListActivity}
 * in two-pane mode (on tablets) or a {@link HabitDetailActivity}
 * on handsets.
 */
public class HabitDetailFragment extends Fragment implements View.OnClickListener {



    public String TAG = getClass().getSimpleName();
    private boolean editMode = true;
    public static final String ALARM_ID = "alarm_id";
    public static final String HABIT_ID = "habit_title";
    private EditText mContactName;
    private EditText mContactNumber;
    private EditText mHabitTime;
    private EditText mHabitDate;
    private EditText mHabitTitle;
    private EditText mHabitDescription;
    private EditText mHabitFrequency;

    private int mHabitID;
    private ArrayList<View> mTextViews = new ArrayList<View>();
    private ArrayList<View> mPickerViews = new ArrayList<View>();
    private String habitMode = "add";
    private static final int CONTACT_PICKER_RESULT = 1001;
    private static final int PICK_CONTACT = 7;

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

        setHasOptionsMenu(true);

        //Create the database here so that it gets the Activity Context.
        acrDB = new AcrDB(getActivity());
        AcrQuery query = new AcrQuery(acrDB);


        if (getArguments().getString(HabitDetailFragment.ARG_HABIT_ID) != null) {


            editMode = false;
            habitMode = "edit";
            mHabitID = Integer.parseInt(getArguments().getString(HabitDetailFragment.ARG_HABIT_ID));


        }else {

            editMode = true;
        }

        if (getArguments().containsKey(ARG_HABIT_ID)) {
            // Load the content using a query with an id specified by the fragment

            mTitle= query.getHabitTitle(getArguments().getString(ARG_HABIT_ID));
            mItem = query.getHabitDetail(getArguments().getString(ARG_HABIT_ID));
            mTime = query.getHabitTime(getArguments().getString(ARG_HABIT_ID));
            mRecurrence = query.getHabitRecurrenceDetails(getArguments().getString(ARG_HABIT_ID));

            getActivity().setTitle(query.getHabitTitle(getArguments().getString(ARG_HABIT_ID)));


        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_habit_detail, container, false);

        mContactName = (EditText) rootView.findViewById(R.id.edit_text_contact_title);
        mContactNumber = (EditText) rootView.findViewById(R.id.edit_text_contact_number);
        mHabitTime = (EditText) rootView.findViewById(R.id.text_view_habit_time);
        mHabitDate = (EditText) rootView.findViewById(R.id.text_view_habit_date);
        mHabitFrequency = (EditText) rootView.findViewById(R.id.edit_habit_recurrence);


        mContactName.setOnClickListener(this);
        mHabitTime.setOnClickListener(this);
        mHabitDate.setOnClickListener(this);
        mHabitFrequency.setOnClickListener(this);





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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_text_contact_title:

                showContactPicker(v);

                break;

            case R.id.text_view_habit_time:

                showTimePickerDialog(v);

                break;

            case R.id.text_view_habit_date:

                showDatePickerDialog(v);

                break;

            case R.id.edit_habit_recurrence:

                showFrequencyPickerDialog(v);

                break;




        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.habit_detail_actions, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this.getActivity(), new Intent(this.getActivity(), HabitListActivity.class));
            return true;
        }





        switch (item.getItemId()) {


            case R.id.habit_details_edit_action:


                if (editMode) {

                    editMode = false;
                    setHabitDetailFields();

                }
                else

                {

                    editMode = true;
                    setHabitDetailFields();

                }

                return true;

            case R.id.habit_details_delete:

                deleteHabit(getArguments().getString(HabitDetailFragment.ARG_HABIT_ID));

                return true;


            case R.id.habit_details_save:

                //Check that the Habit schedule occurs in the future so that an Alarm is not immediately created

                if (!checkHabitTime())
                {

                    Toast.makeText(getActivity().getApplicationContext(), "Please change the schedule start date to be in the future.", Toast.LENGTH_LONG).show();
                    return true;


                }

                //Persist the Habit Detail changes in the database

                saveHabitDetails();

                //Setup new alarms based on the current details
                saveScheduledAlarms();

                //Create intent to to load the Habit List Screen which should show the newly created Habit


                Intent habitListIntent = new Intent(getActivity(), HabitListActivity.class);

                startActivity(habitListIntent);


                return true;





        }
        return super.onOptionsItemSelected(item);
    }





    public void showTimePickerDialog(View v) {

        mHabitTime = (EditText) getActivity().findViewById(R.id.text_view_habit_time);
        String habitTimeText = mHabitTime.getText().toString();

        Integer mHours= Integer.parseInt( habitTimeText.split(":")[0]);
        Integer mMinutes = Integer.parseInt( habitTimeText.split(":")[1]);

        Bundle arguments = new Bundle();
        arguments.putInt("HOURS", mHours);
        arguments.putInt("MINUTES", mMinutes);

        DialogFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(arguments);
        newFragment.show(getFragmentManager(), "timePicker");
    }

    public void showDatePickerDialog(View v) {

        mHabitDate = (EditText) getActivity().findViewById(R.id.text_view_habit_date);
        String habitDateText = (String) mHabitDate.getText().toString();



        Bundle arguments = new Bundle();
        arguments.putString("DATE", habitDateText);
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(arguments);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showContactPicker(View v) {

        Log.i(TAG, "Attempting to launch Contact Picker");

        /*

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT);

        */


        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        switch (reqCode) {
            case (PICK_CONTACT) :
                if (resultCode == Activity.RESULT_OK) {
                    Uri contactData = data.getData();
                    Cursor c =  getActivity().getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        String contactID =  c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String contactNumber = null;


                        Cursor cursorPhone = getActivity().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                                new String[]{contactID},
                                null);

                        if (cursorPhone.moveToFirst()) {
                            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        } else {

                            contactNumber = "No Phone Number for Contact";
                        }

                        cursorPhone.close();


                        TextView contactTextView = (TextView) getActivity().findViewById(R.id.edit_text_contact_title);
                        contactTextView.setText(name);
                        mContactNumber.setText(contactNumber);


                    }
                }
                break;
        }



    }




    public void showFrequencyPickerDialog(View v) {


        DialogFragment newFragment = new FrequencyPickerFragment();

        newFragment.show(getFragmentManager(), "frequencyPicker");
    }



    private void saveScheduledAlarms() {


        //Remove the currently scheduled Alarms before creating the alarms for the new Habit Details

        removeScheduledAlarms(String.valueOf(mHabitID));


        //Toast.makeText(getActivity().getApplicationContext(), "Saving Reminders", Toast.LENGTH_SHORT).show();


        AcrDB acrDB = new AcrDB(getActivity().getApplicationContext());
        AcrQuery query = new AcrQuery(acrDB);


        //Get a random Message from the Message Table
        int supportMessageID = query.getRandomSupportMessageID();


        //Add one to the highest current Scheduled Alarm
        int scheduledAlarmID = (query.getMaxScheduledAlarm() + 1);

        Log.i(TAG, "The max scheduled alarm ID when creating an alarm is " + String.valueOf(scheduledAlarmID));

        //TODO:Write the database values to the database - Should probably be refactored and placed in arcQuery

        SQLiteDatabase db = acrDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AcrDBContract.ScheduledAlarmEntry.COLUMN_NAME_ENTRY_ID, scheduledAlarmID);
        values.put(AcrDBContract.ScheduledAlarmEntry.COLUMN_NAME_MESSAGE_ID, supportMessageID);
        values.put(AcrDBContract.ScheduledAlarmEntry.COLUMN_NAME_HABIT_ID, mHabitID);
        values.put(AcrDBContract.ScheduledAlarmEntry.COLUMN_NAME_TIME_OF_DAY, mHabitTime.getText().toString());
        values.put(AcrDBContract.ScheduledAlarmEntry.COLUMN_NAME_DATE, mHabitDate.getText().toString());
        db.insert(AcrDBContract.ScheduledAlarmEntry.TABLE_NAME, null, values);

        Calendar alarmCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        Date mStartDate = new Date();

        try {

            mStartDate = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(mHabitDate.getText().toString() + " " + (mHabitTime.getText().toString()));

            Log.i("ACR", mStartDate.toString());


        } catch (ParseException e) {

            Log.d("ACR", "Date Parse Exception when saving Alarm");

        }

        alarmCalendar.setTime(mStartDate);

        //Create Alarms in AlarmManager

        AlarmManager am = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);

        Log.i(TAG, "Setting Scheduled Alarm for intent to "+String.valueOf(scheduledAlarmID));

        intent.putExtra("com.a_caring_reminder.app.alarm_id", scheduledAlarmID);
        PendingIntent pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), scheduledAlarmID, intent, 0);

        //TODO: Change to Switch/Case statement

        if (mHabitFrequency.getText().toString().equals("One-time Event")) {


            am.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pi);

        } else if (mHabitFrequency.getText().toString().equals("Hourly"))  {

            //Toast.makeText(getActivity().getApplicationContext(), "Setting Spinner Frequency to Hourly", Toast.LENGTH_SHORT).show();

            am.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pi);

        } else if (mHabitFrequency.getText().toString().equals("Daily")) {

            //Toast.makeText(getActivity().getApplicationContext(), "Setting Spinner Frequency to Daily", Toast.LENGTH_SHORT).show();

            am.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

        } else if (mHabitFrequency.getText().toString().equals("Weekly")) {

            am.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), (AlarmManager.INTERVAL_DAY * 7), pi);
        }

    }

    private boolean checkHabitTime()

    {


        Calendar alarmCalendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());

        Date mStartDate = new Date();

        try {

            mStartDate = new SimpleDateFormat("MM/dd/yyyy HH:mm").parse(mHabitDate.getText().toString() + " " + (mHabitTime.getText().toString()));

            Log.i("ACR", mStartDate.toString());


        } catch (ParseException e) {

            Log.d("ACR", "Date Parse Exception when saving Alarm");

        }

        alarmCalendar.setTime(mStartDate);


        Calendar currentTime = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());



        Log.i("ACR", mStartDate.toString());
        Log.i("ACR", String.valueOf(currentTime.getTimeInMillis()));
        Log.i("ACR", String.valueOf(alarmCalendar.getTimeInMillis()));


        return currentTime.getTimeInMillis() < alarmCalendar.getTimeInMillis();


    }




    private void removeScheduledAlarms(String habitID){

        AcrDB acrDB = new AcrDB(getActivity().getApplicationContext());
        AcrQuery query = new AcrQuery(acrDB);

        //Remove Scheduled Alarms from AlarmManager


        AlarmManager am = (AlarmManager) getActivity().getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        List<ScheduledAlarm> alarmList = query.getScheduledAlarmsForHabit(habitID);

        Log.i("ACR", "AlarmList size is "+ alarmList.size());

        for (int iterator=1; iterator < alarmList.size(); iterator++ ){



            Log.i("ACR", "Removing Scheduled Alarm from AlarmManager with id "+ alarmList.get(iterator).getID());

            Intent intent = new Intent(getActivity().getApplicationContext(), AlarmReceiver.class);
            intent.putExtra("com.a_caring_reminder.app.alarm_id", Integer.parseInt(alarmList.get(iterator).getID()));
            PendingIntent pi = PendingIntent.getBroadcast(getActivity().getApplicationContext(), Integer.parseInt(alarmList.get(iterator).getID()), intent, 0);
            am.cancel(pi);


        }

        //Remove the Scheduled Alarms entries from the ACR database

        query.deleteScheduledAlarmsByHabitID(habitID);


    }

    private void deleteHabit(String id){

        //Remove Scheduled Alarms from ACR

        removeScheduledAlarms(id);

        AcrDB acrDB = new AcrDB(getActivity().getApplicationContext());
        AcrQuery query = new AcrQuery(acrDB);

        //Delete the Habit

        query.deleteHabitByID(id);

        //Return to HabitListActivity

        Intent habitListIntent = new Intent(getActivity(), HabitListActivity.class);
        startActivity(habitListIntent);




    }


    /**

     saveHabitDetails is used to save all of the details set on the Habit Details screen. This includes both habit details and related schedule. At the very end there
     is a call to saveScheduledAlarms which should remove and set any alarms based on the habit details

     Method could be improved to pass in the HabitId and the relevant details. The method could then be made public and possibly used by other classes.

     **/


    private void saveHabitDetails(){


        AcrDB acrDB = new AcrDB(getActivity().getApplicationContext());
        AcrQuery query = new AcrQuery(acrDB);

        if (habitMode.equals("add")){

            mHabitID = (query.getMaxHabitID() + 1);
            query.addHabit(mHabitID, mHabitTitle.getText().toString(), mHabitDescription.getText().toString());
            int mScheduleID = (query.getMaxScheduleID() + 1);
            query.addSchedule(mScheduleID, mHabitID, mHabitTime.getText().toString(), mHabitDate.getText().toString(), "yes", mHabitFrequency.getText().toString());



        } else {

            query.updateHabitDetails(mHabitID, mHabitTitle.getText().toString(), mHabitDescription.getText().toString());
            int mScheduleID = query.getScheduleID(mHabitID);
            query.updateScheduleDetails(mScheduleID, mHabitID, mHabitTime.getText().toString(), mHabitDate.getText().toString(), "yes", mHabitFrequency.getText().toString());
        }


        Log.i("ACR", String.valueOf(mHabitID));
        Log.i("ACR", mHabitTitle.getText().toString());
        Log.i("ACR", mHabitDescription.getText().toString());


    }

    /**

     setHabitDetailFields is used to lock and unlock the fields on the Habit Details page for editing. It uses a boolean switch named editMode
     to determine whether it should lock or unlock the fields.

     This method could possibly be improved by passing the desired editMode to the method instead of using a global flag

     **/


    protected void setHabitDetailFields() {

        //Set private variables to access views

        mContactName = (EditText) getActivity().findViewById(R.id.edit_text_contact_title);
        mHabitTitle = (EditText) getActivity().findViewById(R.id.edit_text_habit_title);
        mHabitDescription = (EditText) getActivity().findViewById(R.id.edit_text_habit_description);
        mHabitTime = (EditText) getActivity().findViewById(R.id.text_view_habit_time);
        mHabitDate = (EditText) getActivity().findViewById(R.id.text_view_habit_date);
        mHabitFrequency = (EditText) getActivity().findViewById(R.id.edit_habit_recurrence);


        //Loading views into arrays to make enabled and disabling editing easier

        mTextViews.add(mContactName);
        mTextViews.add(mHabitTitle);
        mTextViews.add(mHabitDescription);
        mPickerViews.add(mHabitTime);
        mPickerViews.add(mHabitDate);
        mPickerViews.add(mHabitFrequency);


        //Boolean switch editMode - if editMode is false we will lock all the fields

        if (editMode == false)

        {

            for (View v : mTextViews) {
                v.setFocusable(false);
                v.setFocusableInTouchMode(false);
                v.setEnabled(false);
            }
            for (View v : mPickerViews) {
                v.setClickable(false);
                v.setEnabled(false);
            }



            Toast.makeText(getActivity().getApplicationContext(), "Editing Disabled", Toast.LENGTH_SHORT).show();

        } else if (editMode == true) {

            for (View v : mTextViews) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                v.setEnabled(true);
            }

            for (View v : mPickerViews) {
                v.setClickable(true);
                v.setEnabled(true);
            }


            //Toast.makeText(getActivity().getApplicationContext(), "unlocking " + v.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(getActivity().getApplicationContext(), "Editing Enabled", Toast.LENGTH_SHORT).show();

        }


    }

}



