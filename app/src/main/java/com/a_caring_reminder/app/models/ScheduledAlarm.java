package com.a_caring_reminder.app.models;

/**
 * Created by Taylor on 6/14/2014.
 */
public class ScheduledAlarm {

    private int mScheduledAlarmID;
    private int mHabitID;
    private int mMessageID;
    private String mPhoneNumber;
    private String mSubject;
    private String mMessage;

    public ScheduledAlarm(int scheduledAlarmID, int habitID, int messageID, String mPhoneNumber, String mMessage) {

        this.mScheduledAlarmID = scheduledAlarmID;
        this.mHabitID = habitID;
        this.mMessageID = messageID;
        this.mPhoneNumber = mPhoneNumber;
        this.mMessage = mMessage;

    }

    public int getPosition() {

        return mScheduledAlarmID;
    }

    public void setScheduledAlarmID(int position) {

        this.mScheduledAlarmID = position;
    }

    public String getID() {

        return String.valueOf(mScheduledAlarmID);
    }

    public void setID(int id) {

        this.mScheduledAlarmID = id;
    }

    @Override
    public String toString() {

        return String.valueOf(mScheduledAlarmID);
    }

    public int getHabitID() {

        return this.mHabitID;
    }

    public int getMessageID() {

        return this.mMessageID;
    }

    public String getmSubject() {
        return mSubject;
    }

    public void setmSubject(String mSubject) {
        this.mSubject = mSubject;
    }

    public String getmMessage() {
        return mMessage;
    }

    public void setmMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

}