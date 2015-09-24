package com.a_caring_reminder.app.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Dan Bryant on 6/14/2014.
 */

//Access DB using sqlite3 in adb using sqlite3 data/data/com.a_caring_reminder.app/databases/acr.db

public class AcrDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "acr.db";
    private static final int DATABASE_VERSION = 2;
    public boolean cleanup = false;

    //Create a helper object for the acr database
    public AcrDB(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        try{

            //
            // changed starting id to be 0-4 instead of 1-5
            //
            db.execSQL("CREATE TABLE Message " +
                    "(id	int, " +
                    "message    text)");


            //
            // creating users table
            //
            db.execSQL("CREATE TABLE SupportedUsers " +
                    "(id    int, " +
                    "reg_id_str	text, " +
                    "email    text, " +
                    "message    text)");




            db.execSQL("CREATE TABLE Habit " +
                    "(id int, " +
                    "descriptions 	text, " +
                    "title text)");


            db.execSQL("CREATE TABLE Schedule " +
                    "(id int, " +
                    "habit_id int, " +
                    "time_of_day 	text, " +
                    "day_of_week text, " +
                    "repeating		text, " +
                    "recurrence text)");


            db.execSQL("CREATE TABLE ScheduledAlarms " +
                    "(id int, " +
                    "phone_number string," +
                    "habit_id int, " +
                    "message_id string, " +
                    "time_of_day text, " +
                    "date string)");

            db.execSQL("CREATE TABLE Reminder " +
                    "(id int, " +
                    "group_id int, " +
                    "contact_name text, " +
                    "contact_number text, " +
                    "subject text," +
                    "message text," +
                    "habit_time text," +
                    "habit_date text," +
                    "frequency text)");


            /* Create Sample Data /*

            db.execSQL("Insert Into Message (id, message) values('0', 'Please remember your meds')");
            db.execSQL("Insert Into Message (id, message) values('1', 'Take your meds sweetheart')");
            db.execSQL("Insert Into Message (id, message) values('2', 'You got this')");
            db.execSQL("Insert Into Message (id, message) values('3', 'Love you honey bunny')");
            db.execSQL("Insert Into Message (id, message) values('4', 'You be awesome!')");




            db.execSQL("Insert Into SupportedUsers (id, reg_id_str, email, message) values('0', 'asdklajsdlks', 'test@test.com' , 'Hugs and Kisses!')");



            db.execSQL("Insert Into Habit (id, title, descriptions) values('1', 'Morning routine', 'aspirin and coffee')");
            db.execSQL("Insert Into Habit (id, title, descriptions) values('2', 'Afternoon routine', 'aspirin and beer')");
            db.execSQL("Insert Into Habit (id, title, descriptions) values('3', 'Please wake up', 'Get out of bed and have a great day!')");
            db.execSQL("Insert Into Habit (id, title, descriptions) values('4', 'Time to go to bed', 'Get some rest and have a great day tomorrow!')");
            db.execSQL("Insert Into Habit (id, title, descriptions) values('5', 'Eat lunch', 'Time to eat something so you can be healthy')");
            db.execSQL("Insert Into Habit (id, title, descriptions) values('6', 'Breakfast', 'The most important meal of the day!')");



            db.execSQL("Insert Into Schedule (id, habit_id, time_of_day, day_of_week, repeating, recurrence) values('1', '1', '07:00', '06/14/2014', 'yes', 'Daily')");
            db.execSQL("Insert Into Schedule (id, habit_id, time_of_day, day_of_week, repeating, recurrence) values('2', '2', '16:00', '06/14/2014', 'yes', 'Daily')");
            db.execSQL("Insert Into Schedule (id, habit_id, time_of_day, day_of_week, repeating, recurrence) values('3', '3', '08:12', '06/15/2014', 'yes', 'Daily')");
            db.execSQL("Insert Into Schedule (id, habit_id, time_of_day, day_of_week, repeating, recurrence) values('4', '4', '08:30', '06/15/2014', 'yes', 'Daily')");
            db.execSQL("Insert Into Schedule (id, habit_id, time_of_day, day_of_week, repeating, recurrence) values('5', '5', '12:12', '06/15/2014', 'yes', 'Daily')");
            db.execSQL("Insert Into Schedule (id, habit_id, time_of_day, day_of_week, repeating, recurrence) values('6', '6', '07:07', '06/15/2014', 'yes', 'Daily')");

             */


        }
        catch (SQLException ex){
            Log.d("sqldb", ex.getMessage());
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS Message" + DATABASE_NAME);
        db.execSQL("DROP TABLE IF EXISTS Habit" + DATABASE_NAME);
        db.execSQL("DROP TABLE IF EXISTS Schedule" + DATABASE_NAME);
        db.execSQL("DROP TABLE IF EXISTS SupportedUsers" + DATABASE_NAME);
        db.execSQL("DROP TABLE IF EXISTS Reminder" + DATABASE_NAME);


        onCreate(db);
    }

    public boolean onInsert(String insert){


        return true;
    }

    public boolean insertInto(String statement){

        return true;
    }

}
