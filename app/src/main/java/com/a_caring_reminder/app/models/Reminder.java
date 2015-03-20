package com.a_caring_reminder.app.models;

/**
 * Created by daz on 3/19/15.
 */
public class Reminder {

    private int mReminderUniqueId;
    private int mReminderGroupId;
    private String mContactName;
    private String mContactNumber;
    private String mSubject;
    private String mMessage;
    private String mHabitTime;
    private String mHabitDate;
    private String mFrequency;


    public Reminder (int reminderUniqueID, int reminderGroupId, String contactName, String contactNumber, String subject, String message, String habitTime, String habitDate, String frequency)
    {

        this.mReminderUniqueId = reminderUniqueID;
        this.mReminderGroupId = reminderGroupId;
        this.mContactName = contactName;
        this.mContactNumber = contactNumber;
        this.mSubject = subject;
        this.mMessage = message;
        this.mHabitTime = habitTime;
        this.mHabitDate = habitDate;
        this.mFrequency = frequency;


    }


    public int getReminderUniqueId(){

        return this.mReminderUniqueId;
    }

    public String getHabitTime(){

        return this.mHabitTime;
    }

    public String getHabitSubject(){

        return this.mSubject;
    }

    public int getPosition() {

        return this.mReminderUniqueId;
    }






}
