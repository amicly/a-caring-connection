package com.a_caring_reminder.app.models;

/**
 * Created by daz on 3/19/15.
 */
public class Text {

    private int mReminderUniqueId;
    private int mReminderGroupId;

    private String mContactName;
    private String mContactNumber;
    private String mSubject;
    private String mMessage;
    private String mHabitTime;
    private String mHabitDate;
    private String mFrequency;

    public Text(int reminderUniqueID, int reminderGroupId, String contactName, String contactNumber, String subject, String message, String habitTime, String habitDate, String frequency) {

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


    public int getReminderUniqueId() {

        return this.mReminderUniqueId;
    }

    public String getTextTime() {

        return this.mHabitTime;
    }

    public String getHabitSubject() {

        return this.mSubject;
    }

    public String getTextMessage() {

        return this.mMessage;
    }

    public int getPosition() {

        return this.mReminderUniqueId;
    }

    public String getTextDate() {
        return mHabitDate;
    }

    public String getRecipientName() {
        return mContactName;
    }

}
