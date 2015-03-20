package com.a_caring_reminder.app.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.a_caring_reminder.app.models.ReceivedMessage;
import com.a_caring_reminder.app.models.Reminder;
import com.a_caring_reminder.app.models.ScheduleItems;
import com.a_caring_reminder.app.models.ScheduledAlarm;
import com.a_caring_reminder.app.models.SupportMessage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Dan Bryant on 6/15/2014.
 */
public class AcrQuery {
    /**
     * Class created to store query methods for a_caring_reminder
     */
    private final AcrDB acrDB;
    private final String LOG_TAG = getClass().getSimpleName();

    public AcrQuery(AcrDB acrDB){
        this.acrDB = acrDB;

    }

    public boolean chkCredentials(){
        Cursor db;
        try {

            db = acrDB.getReadableDatabase().rawQuery("Select username From User", null);
            if (db.getCount() > 0) {

                return true;
            } else {
                return false;
            }
        }
        catch (Exception ex){
            Log.d("getDetail", ex.getMessage());
            return false;
        }

    }

    /***Queries related to the Habits Table ***/


    public String getHabitDetail(String id){

        Cursor db;
        try {

            db = acrDB.getReadableDatabase().rawQuery("Select descriptions From Habit Where rowid = '" + id + "'", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getString(0);
            } else {
                return "none";
            }
        }
        catch (Exception ex){
            Log.d("getDetail", ex.getMessage());
            return "none";
        }

    }

    public String getHabitTitle(String id){

        Cursor db;
        try {

            db = acrDB.getReadableDatabase().rawQuery("Select title From Habit Where id = '" + id + "'", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getString(0);
            } else {
                return "none";
            }
        }
        catch (Exception ex){
            Log.d("getDetail", ex.getMessage());
            return "none";
        }

    }

    public String getHabitTime(String id){

        Cursor db;
        try {

            db = acrDB.getReadableDatabase().rawQuery("Select time_of_day From Schedule Where habit_id = '" + id + "'", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getString(0);
            } else {
                return "none";
            }
        }
        catch (Exception ex){
            Log.d("getDetail", ex.getMessage());
            return "none";
        }

    }

    //Query to update the Habit Details

    public int updateHabitDetails(int habitID, String habitTitle, String habitDescription){

        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("title", habitTitle);
            values.put("descriptions", habitDescription);
            return sqLiteDatabase.update("Habit", values, "id =?", new String[]{String.valueOf(habitID)});
        }
        catch (Exception ex){
            Log.d("Exception when executing updateHabitDetails", ex.getMessage());
            return -1;
        }


    }

    public List getTodaysHabitList(){

        Cursor db;

        db = acrDB.getReadableDatabase().rawQuery("Select Habit.id, Habit.descriptions, Schedule.time_of_day From Habit" +
                ", Schedule Where Schedule.habit_id = Habit.id", null);
        return habitScheduleToList(db);
    }

    public List getHabitList(){

        Cursor db;

        db = acrDB.getReadableDatabase().rawQuery("Select Habit.id, Habit.title, Schedule.time_of_day From Habit" +
                ", Schedule Where Schedule.habit_id = Habit.id", null);
        return habitScheduleToList(db);
    }

    public int deleteHabitByID(String id){

        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            return sqLiteDatabase.delete("Habit", "id="+id, null);
        }
        catch (Exception ex){
            Log.d("getHabitRecurrenceDetails", ex.getMessage());
            return -1;
        }

    }

    public int getMaxHabitID(){

        Cursor db;
        try {
            db = acrDB.getReadableDatabase().rawQuery("Select MAX(id) from Habit", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getInt(0);
            } else {
                return -1;
            }
        }
        catch (Exception ex){
            Log.d("getMaxHabitID", ex.getMessage());
            return -1;
        }

    }

    //Query to add a habit

    public int addHabit(int habitID, String habitTitle, String habitDescription){

        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("id", habitID);
            values.put("title", habitTitle);
            values.put("descriptions", habitDescription);
            return (int) sqLiteDatabase.insert("Habit", "1", values);
        }
        catch (Exception ex){
            Log.d("Exception when executing updateHabitDetails", ex.getMessage());
            return -1;
        }


    }


    /**
     * A generic database insert method using a JSONObject; not tested as of 6-16-14
     * @param a
     * @param tbl
     * @return
     */
    boolean insertBasicData(JSONObject a, String tbl){

        try{
            String cols = "(";
            String vals = "(";

            JSONArray names = a.names();

            cols += names.getString(0);
            vals += "'" + a.getString(names.getString(0)) + "'";
            for (int i = 1; i < names.length(); i++){
                cols += ", " + names.getString(i);
                vals += ", " + "'" + a.getString(names.getString(i)).replace("'", "\''") + "'";
            }
            cols += ")";
            vals += ")";

            acrDB.getWritableDatabase().execSQL("Insert Into " + tbl + " " + cols + " values " + vals);

        }
        catch (JSONException jex){
            String jer = jex.getMessage() + ":// " + jex.getLocalizedMessage();
        }
        return true;
    }

    /**
     * A method to turn a cursor into a List object for simple listviews
     * @param db
     * @return
     */
    private List<ScheduleItems> habitScheduleToList(Cursor db){

        List<ScheduleItems> ITEMS = new ArrayList<ScheduleItems>();

        ScheduleItems items;

        try{

            if (db.getCount() != 0){
                db.moveToNext();

                for (int i = 0; i < db.getCount(); i ++){

                    items = new ScheduleItems( db.getInt(0),db.getString(1),db.getString(2));
                    ITEMS.add(items);
                    db.moveToNext();

                }

            }
            else {

                items = new ScheduleItems(1, "No Items Yet", " ");
                ITEMS.add(items);

            }
            return ITEMS;

        }
        catch (Exception ex){
            String er = ex.getMessage();
            return ITEMS;
        }
    }

    private List<String> cursorToSimpleList(Cursor db){
        List<String> items = new ArrayList<String>();

            for (int i = 0; i < db.getCount(); i ++){
                db.moveToNext();
                items.add(db.getString(0));
            }

        return items;
    }

    private List<SupportMessage> cursorSupportMessage(Cursor db){
        List<SupportMessage> items = new ArrayList<SupportMessage>();
        for (int i = 0; i < db.getCount(); i ++){
            db.moveToNext();
            items.add(new SupportMessage(db.getInt(0), db.getString(1)));
        }
        return items;
    }


    //
    // cursor to Received Message
    //
    private List<ReceivedMessage> cursorReceivedMessage(Cursor db){
        List<ReceivedMessage> items = new ArrayList<ReceivedMessage>();
        for (int i = 0; i < db.getCount(); i ++){
            db.moveToNext();
            items.add(new ReceivedMessage(db.getInt(0), db.getString(1), db.getString(2), db.getString(3)));
        }
        return items;
    }


    /***Queries related to the Messages Table ***/

    public Map<Integer, List<String>> messageMap(int habits){
        Map<Integer, List<String>> mMap = new HashMap<Integer, List<String>>();
        List<String> messages = getSupportMessageList();
        List<String>[] lists = new List[messages.size()];
        //For counting loops when messages outnumber habits
        int x = 0;
        //For indexing messages to habits when more than 1
        int y = 1;
        try {
            for (int i = 0; i < messages.size(); i++) {
                lists[i] = new ArrayList<String>();
                if (i < habits) {
                    lists[i].add(0, messages.get(i));
                } else {
                    lists[i].add(y, messages.get(x));

                }

                mMap.put(x, lists[i]);
                x++;
                if (x == habits) {
                    x = 0;
                    y++;
                }
            }
        }
        catch (Exception ex){
            Log.d("map", ex.getMessage());
        }
        return mMap;
    }

    public String getMessage(){

        Cursor db;
        try {

            db = acrDB.getReadableDatabase().rawQuery("Select message From Message", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getString(0);
            } else {
                return "none";
            }
        }
        catch (Exception ex){
            Log.d("getMessage", ex.getMessage());
            return "none";
        }

    }

    public String getMessageByID(int id){

        Cursor db;
        try {

            db = acrDB.getReadableDatabase().rawQuery("Select message From Message Where id = '" + id + "'", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getString(0);
            } else {
                return "none";
            }
        }
        catch (Exception ex){
            Log.d("getMessage", ex.getMessage());
            return "none";
        }

    }

    public void setSupportMessage(int id, String message){

        acrDB.getWritableDatabase().rawQuery(("Insert Into Message (id, message) values('"+ String.valueOf(id) +"', '" + message +"')"),null);

    }

    public Integer getRandomSupportMessageID(){

        Cursor db;
        try {
        db = acrDB.getWritableDatabase().rawQuery("Select Message.id From Message ORDER BY RANDOM() LIMIT 1", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getInt(0);
            } else {
                return -1;
            }
        } catch (Exception ex){
        Log.d("getMessage", ex.getMessage());
        return -1;
        }

    }



    public List<String> getSupportMessageList(){

        Cursor cursor;

        cursor = acrDB.getReadableDatabase().rawQuery("Select message From Message", null);
        List<String> list = cursorToSimpleList(cursor);
        return list;
    }

    //
    // Justin: for Messages Adapter
    //
    public List<SupportMessage> getSupportMessageListItems(){

        Cursor cursor;

        cursor = acrDB.getReadableDatabase().rawQuery("Select id, message From Message", null);
        List<SupportMessage> list = cursorSupportMessage(cursor);
        return list;
    }

    public List<ReceivedMessage> getReceivedMessageListItems(){

        Cursor cursor;

        cursor = acrDB.getReadableDatabase().rawQuery("Select id, email, message From SupportedUsers", null);
        List<ReceivedMessage> list = cursorReceivedMessage(cursor);
        return list;
    }

    public String getSupportMessageDetail(String id){

        Cursor db;
        try {
            db = acrDB.getReadableDatabase().rawQuery("Select message From Message Where id = '" + id + "'", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getString(0);
            } else {
                return "none";
            }
        }
        catch (Exception ex){
            Log.d("getDetail", ex.getMessage());
            return "none";
        }

    }

    /***Queries related to the Scheduled Alarms Table ***/


    public List<ScheduledAlarm> getScheduledAlarmsForHabit(String id){

        Cursor db;

        List<ScheduledAlarm> ITEMS = new ArrayList<ScheduledAlarm>();
        ScheduledAlarm item;

        try {
            db = acrDB.getReadableDatabase().rawQuery("Select id, habit_id, message_id From ScheduledAlarms Where habit_id = '" + id + "'", null);
            int i = 0;
            if (db.getCount() > 0) {
                if  (db.moveToFirst()) {
                    do {
                        item = new ScheduledAlarm(db.getInt(0),db.getInt(1),db.getInt(2));
                        ITEMS.add(item);

                    } while (db.moveToNext());
                }

                return ITEMS;



            } else {
                return new ArrayList<ScheduledAlarm>();
            }
        }
        catch (Exception ex){
            Log.d("getDetail", ex.getMessage());
            return new ArrayList<ScheduledAlarm>();
        }

    }

    //
    // refactoring deleteSupportMessageByID
    //
    public boolean deleteSupportMessageByID(int id){
        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {
            return sqLiteDatabase.delete("Message", "id="+ String.valueOf(id), null) > 0;
        }
        catch (Exception ex){
            Log.d("deleteSupportMessageByID", ex.getMessage());
            return false;
        }
    }

    public int deleteSupportMessageByString(String message){
        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {
            return sqLiteDatabase.delete("Message", "message =  ?", new String[]{message});
        }
        catch (Exception ex){
            Log.d("deleteSupportMessageByID", ex.getMessage());
            return -1;
        }
    }

    public int deleteScheduledAlarmsByHabitID(String id){

        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            return sqLiteDatabase.delete("ScheduledAlarms", "habit_id="+id, null);
        }
        catch (Exception ex){
            Log.d("getHabitRecurrenceDetails", ex.getMessage());
            return -1;
        }

    }

    public int getMaxScheduledAlarm(){

        Cursor db;
        try {
            db = acrDB.getReadableDatabase().rawQuery("Select MAX(id) from ScheduledAlarms", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getInt(0);
            } else {
                return -1;
            }
        }
        catch (Exception ex){
            Log.d("getDetail", ex.getMessage());
            return -1;
        }

    }

    public ScheduledAlarm getScheduledAlarm(String id){

        Cursor db;


        ScheduledAlarm item = new ScheduledAlarm(-1, -1, -1);

        try {
            db = acrDB.getReadableDatabase().rawQuery("Select id, habit_id, message_id From ScheduledAlarms Where id = '" + id + "'", null);
            int i = 0;
            if (db.getCount() > 0) {
                if  (db.moveToFirst()) {

                        item = new ScheduledAlarm(db.getInt(0),db.getInt(1),db.getInt(2));


                }

                return item;


            } else {
                return item;
            }
        }
        catch (Exception ex){
            Log.d("getDetail", ex.getMessage());
            return new ScheduledAlarm(-1, -1, -1);
        }

    }

    /***Queries related to the Schedule Table ***/


    public String getHabitRecurrenceDetails(String id){

        Cursor db;
        try {

            db = acrDB.getReadableDatabase().rawQuery("Select time_of_day, day_of_week, repeating, recurrence From Schedule Where habit_id = '" + id + "'", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getString(0).concat(" ").concat(db.getString(1)).concat(" ").concat(db.getString(2)).concat(" ").concat(db.getString(3));
            } else {
                return "none";
            }
        }
        catch (Exception ex){
            Log.d("getHabitRecurrenceDetails", ex.getMessage());
            return "none";
        }

    }

    public int getScheduleID(int id){

        Cursor db;
        try {

            db = acrDB.getReadableDatabase().rawQuery("Select id From Schedule Where habit_id = '" + id + "'", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getInt(0);
            } else {
                return -1;
            }
        }
        catch (Exception ex){
            Log.d("getScheduleID", ex.getMessage());
            return -1;
        }

    }

    //Query to update Schedule Details

    public int updateScheduleDetails(int scheduleID, int habitID, String scheduleTime, String habitDay, String scheduleRepeating, String habitFrequency){

        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("habit_id", habitID);
            values.put("time_of_day", scheduleTime);
            values.put("day_of_week", habitDay);
            values.put("repeating", scheduleRepeating);
            values.put("recurrence", habitFrequency);
            return sqLiteDatabase.update("Schedule", values, "id =?", new String[]{String.valueOf(scheduleID)});
        }
        catch (Exception ex){
            Log.d("Exception when executing updateHabitDetails", ex.getMessage());
            return -1;
        }


    }

    public String getDate(){

        int mMonth;
        int mDay;
        int mYear;

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        String s = String.valueOf(mMonth + 1) + "-" + mDay + "-" + mYear + " ";
        return s;

    }

    //Query to add Schedule Details

    public int addSchedule(int scheduleID, int habitID, String scheduleTime, String habitDay, String scheduleRepeating, String habitFrequency){

        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("id", scheduleID);
            values.put("habit_id", habitID);
            values.put("time_of_day", scheduleTime);
            values.put("day_of_week", habitDay);
            values.put("repeating", scheduleRepeating);
            values.put("recurrence", habitFrequency);
            return (int) sqLiteDatabase.insert("Schedule", "1", values);
        }
        catch (Exception ex){
            Log.d(LOG_TAG, ex.getMessage());
            return -1;
        }


    }

    public int getMaxScheduleID(){

        Cursor db;
        try {
            db = acrDB.getReadableDatabase().rawQuery("Select MAX(id) from Schedule", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getInt(0);
            } else {
                return -1;
            }
        }
        catch (Exception ex){
            Log.d("getDetail", ex.getMessage());
            return -1;
        }

    }

    //Query to add a message

    public int addMessage(int messageID, String messageDescription){

        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("id", messageID);
            values.put("message", messageDescription);
            return (int) sqLiteDatabase.insert("Message", "1", values);
        }
        catch (Exception ex){
            Log.d("Exception when executing updateMessageDetails", ex.getMessage());
            return -1;
        }
    }

    // Query to add ReceivedMessage values
    public int addReceivedMessage(int anID, String aRegID, String anEmail, String aMessageDescription){

        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("id", anID);
            values.put("reg_id_str", aRegID);
            values.put("email", anEmail);
            values.put("message", aMessageDescription);
            return (int) sqLiteDatabase.insert("SupportedUsers", "1", values);
        }
        catch (Exception ex){
            Log.d("Exception when executing updateReceivedMessage", ex.getMessage());
            return -1;
        }
    }

    public int getMaxReceivedMessageID(){

        Cursor db;
        try {
            db = acrDB.getReadableDatabase().rawQuery("Select MAX(id) from SupportedUsers", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getInt(0);
            } else {
                return -1;
            }
        }
        catch (Exception ex){
            Log.d("getMaxReceivedMessageID", ex.getMessage());
            return -1;
        }
    }



    public int getMaxMessageID(){

        Cursor db;
        try {
            db = acrDB.getReadableDatabase().rawQuery("Select MAX(id) from Message", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getInt(0);
            } else {
                return -1;
            }
        }
        catch (Exception ex){
            Log.d("getMaxMessageID", ex.getMessage());
            return -1;
        }
    }


    public int updateMessageDetails(int aMessageID, String aMessageDescription) {
        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();

            values.put("message", aMessageDescription);
            return sqLiteDatabase.update("Message", values, "id =?", new String[]{String.valueOf(aMessageID)});
        }
        catch (Exception ex){
            Log.d("Exception when executing updateMessageDetails", ex.getMessage());
            return -1;
        }
    }

    public int updateReceivedMessage(int anID, String aRegID, String anEmail, String aReceivedMessage) {
        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();

            values.put("email", anEmail);
            values.put("message", aReceivedMessage);
            values.put("reg_ID_str", aRegID);
            return sqLiteDatabase.update("SupportedUsers", values, "id =?", new String[]{String.valueOf(anID)});
        }
        catch (Exception ex){
            Log.d(LOG_TAG, ex.getMessage());
            return -1;
        }
    }

    //Reminder Queries:

    public int getMaxReminderId(){

        Cursor db;
        try {
            db = acrDB.getReadableDatabase().rawQuery("Select MAX(id) from Reminder", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getInt(0);
            } else {
                return -1;
            }
        }
        catch (Exception ex){
            Log.d(LOG_TAG, ex.getMessage());
            return -1;
        }

    }

    public int getMaxReminderGroupId(){

        Cursor db;
        try {
            db = acrDB.getReadableDatabase().rawQuery("Select MAX(group_id) from Reminder", null);
            if (db.getCount() > 0) {
                db.moveToNext();
                return db.getInt(0);
            } else {
                return -1;
            }
        }
        catch (Exception ex){
            Log.d(LOG_TAG, ex.getMessage());
            return -1;
        }

    }

    //Query to add a reminder

    public int addReminder(int reminderUniqueID, int reminderGroupId, String contactName, String contactNumber, String subject, String message, String habitTime, String habitDate, String frequency){

        SQLiteDatabase sqLiteDatabase = acrDB.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put("id", reminderUniqueID);
            values.put("group_id", reminderGroupId);
            values.put("contact_name", contactName);
            values.put("contact_number", contactNumber);
            values.put("subject", subject);
            values.put("message", message);
            values.put("habit_time", habitTime);
            values.put("habit_date", habitDate);
            values.put("frequency", frequency);


            return (int) sqLiteDatabase.insert("Reminder", null, values);
        }
        catch (Exception ex){
            Log.d(LOG_TAG, ex.getMessage());
            return -1;
        }


    }

    /**
     * A method to turn a cursor into a List object for simple listviews
     * @param acrDB
     * @return List<Reminder>
     */
    public List<Reminder> remindersToList(AcrDB acrDB){

        Cursor db = acrDB.getReadableDatabase().rawQuery("Select * from Reminder", null);

        List<Reminder> Reminders = new ArrayList<Reminder>();

        Reminder reminder;

        try{

            if (db.getCount() != 0){
                db.moveToNext();

                for (int i = 0; i < db.getCount(); i ++){

                    reminder = new Reminder (db.getInt(0), db.getInt(1), db.getString(2), db.getString(3), db.getString(4), db.getString(5), db.getString(6), db.getString(7), db.getString(8));
                    Reminders.add(reminder);
                    db.moveToNext();

                }

            }
            else {

                reminder = new Reminder(0, 0, null, null, null, null, null, null, null);
                Reminders.add(reminder);

            }
            return Reminders;

        }
        catch (Exception ex){
            ex.printStackTrace();
            return Reminders;
        }
    }


}