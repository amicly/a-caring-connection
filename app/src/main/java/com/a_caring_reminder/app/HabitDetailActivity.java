package com.a_caring_reminder.app;

import android.app.AlarmManager;

import android.app.DialogFragment;

import android.app.PendingIntent;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.app.Activity;

import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import android.widget.Toast;

import com.a_caring_reminder.app.models.ScheduledAlarm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * An activity representing a single habit detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link HabitListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link HabitDetailFragment}.
 */
public class HabitDetailActivity extends Activity {


    private boolean editMode = true;
    public static final String ALARM_ID = "alarm_id";
    public static final String HABIT_ID = "habit_title";
    private EditText mHabitTime;
    private EditText mHabitDate;
    private EditText mHabitTitle;
    private EditText mHabitDescription;
    private EditText mHabitFrequency;
    private int mHabitID;
    private ArrayList<View> mTextViews = new ArrayList<View>();
    private ArrayList<View> mPickerViews = new ArrayList<View>();
    private String habitMode = "add";


    public String TAG = "ACR";

    Context mContext = this.getBaseContext();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);




        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();


            if (getIntent().getStringExtra(HabitDetailFragment.ARG_HABIT_ID) != null) {

                arguments.putString(HabitDetailFragment.ARG_HABIT_ID,
                        getIntent().getStringExtra(HabitDetailFragment.ARG_HABIT_ID));
                        editMode = false;
                        habitMode = "edit";
                        mHabitID = Integer.parseInt(getIntent().getStringExtra(HabitDetailFragment.ARG_HABIT_ID));



            }else {

                editMode = true;
            }

            HabitDetailFragment fragment = new HabitDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.habit_detail_container, fragment)
                    .commit();
        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.habit_detail_actions, menu);
        return super.onCreateOptionsMenu(menu);
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
            NavUtils.navigateUpTo(this, new Intent(this, HabitListActivity.class));
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

                deleteHabit(getIntent().getStringExtra(HabitDetailFragment.ARG_HABIT_ID));

                return true;


            case R.id.habit_details_save:

                //Check that the Habit schedule occurs in the future so that an Alarm is not immediately created

                if (!checkHabitTime())
                {

                    Toast.makeText(getApplicationContext(), "Please change the schedule start date to be in the future.", Toast.LENGTH_LONG).show();
                    return true;


                }

                //Persist the Habit Detail changes in the database

                saveHabitDetails();

                //Setup new alarms based on the current details
                saveScheduledAlarms();

                //Create intent to to load the Habit List Screen which should show the newly created Habit


                Intent habitListIntent = new Intent(this, HabitListActivity.class);

                startActivity(habitListIntent);

                finish();

                return true;





        }
        return super.onOptionsItemSelected(item);
    }

    public void showTimePickerDialog(View v) {

        mHabitTime = (EditText) findViewById(R.id.text_view_habit_time);
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

        mHabitDate = (EditText) findViewById(R.id.text_view_habit_date);
        String habitDateText = (String) mHabitDate.getText().toString();



        Bundle arguments = new Bundle();
        arguments.putString("DATE", habitDateText);
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.setArguments(arguments);
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public void showFrequencyPickerDialog(View v) {


        DialogFragment newFragment = new FrequencyPickerFragment();

        newFragment.show(getFragmentManager(), "frequencyPicker");
    }



    private void saveScheduledAlarms() {


        //Remove the currently scheduled Alarms before creating the alarms for the new Habit Details

        removeScheduledAlarms(String.valueOf(mHabitID));


        //Toast.makeText(getApplicationContext(), "Saving Reminders", Toast.LENGTH_SHORT).show();


        AcrDB acrDB = new AcrDB(getApplicationContext());
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

        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);

        Log.i(TAG, "Setting Scheduled Alarm for intent to "+String.valueOf(scheduledAlarmID));

        intent.putExtra("com.a_caring_reminder.app.alarm_id", scheduledAlarmID);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), scheduledAlarmID, intent, 0);

        //TODO: Change to Switch/Case statement

        if (mHabitFrequency.getText().toString().equals("One-time Event")) {


            am.set(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), pi);

        } else if (mHabitFrequency.getText().toString().equals("Hourly"))  {

            //Toast.makeText(getApplicationContext(), "Setting Spinner Frequency to Hourly", Toast.LENGTH_SHORT).show();

            am.setRepeating(AlarmManager.RTC_WAKEUP, alarmCalendar.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pi);

        } else if (mHabitFrequency.getText().toString().equals("Daily")) {

            //Toast.makeText(getApplicationContext(), "Setting Spinner Frequency to Daily", Toast.LENGTH_SHORT).show();

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

        AcrDB acrDB = new AcrDB(getApplicationContext());
        AcrQuery query = new AcrQuery(acrDB);

        //Remove Scheduled Alarms from AlarmManager


        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        List<ScheduledAlarm> alarmList = query.getScheduledAlarmsForHabit(habitID);

        Log.i("ACR", "AlarmList size is "+ alarmList.size());

        for (int iterator=1; iterator < alarmList.size(); iterator++ ){



            Log.i("ACR", "Removing Scheduled Alarm from AlarmManager with id "+ alarmList.get(iterator).getID());

            Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
            intent.putExtra("com.a_caring_reminder.app.alarm_id", Integer.parseInt(alarmList.get(iterator).getID()));
            PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), Integer.parseInt(alarmList.get(iterator).getID()), intent, 0);
            am.cancel(pi);


        }

        //Remove the Scheduled Alarms entries from the ACR database

        query.deleteScheduledAlarmsByHabitID(habitID);


    }

    private void deleteHabit(String id){

        //Remove Scheduled Alarms from ACR

        removeScheduledAlarms(id);

        AcrDB acrDB = new AcrDB(getApplicationContext());
        AcrQuery query = new AcrQuery(acrDB);

        //Delete the Habit

        query.deleteHabitByID(id);

        //Return to HabitListActivity

        Intent habitListIntent = new Intent(this, HabitListActivity.class);
        startActivity(habitListIntent);




    }


    /**

     saveHabitDetails is used to save all of the details set on the Habit Details screen. This includes both habit details and related schedule. At the very end there
     is a call to saveScheduledAlarms which should remove and set any alarms based on the habit details

     Method could be improved to pass in the HabitId and the relevant details. The method could then be made public and possibly used by other classes.

     **/


    private void saveHabitDetails(){


        AcrDB acrDB = new AcrDB(getApplicationContext());
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


        mHabitTitle = (EditText) findViewById(R.id.edit_text_habit_title);
        mHabitDescription = (EditText) findViewById(R.id.edit_text_habit_description);
        mHabitTime = (EditText) findViewById(R.id.text_view_habit_time);
        mHabitDate = (EditText) findViewById(R.id.text_view_habit_date);
        mHabitFrequency = (EditText) findViewById(R.id.edit_habit_recurrence);


        //Loading views into arrays to make enabled and disabling editing easier

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



            Toast.makeText(getApplicationContext(), "Editing Disabled", Toast.LENGTH_SHORT).show();

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


            //Toast.makeText(getApplicationContext(), "unlocking " + v.toString(), Toast.LENGTH_SHORT).show()
            Toast.makeText(getApplicationContext(), "Editing Enabled", Toast.LENGTH_SHORT).show();

        }


    }



}




