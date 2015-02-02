package com.a_caring_reminder.app.models;

/**
 * Created by Taylor on 6/14/2014.
 */
public class ScheduledAlarm {

    private int mScheduledAlarmID;
    private int mHabitID;
    private int mMessageID;

    public ScheduledAlarm(int scheduledAlarmID, int habitID, int messageID) {

        this.mScheduledAlarmID = scheduledAlarmID;
        this.mHabitID = habitID;
        this.mMessageID = messageID;
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

}