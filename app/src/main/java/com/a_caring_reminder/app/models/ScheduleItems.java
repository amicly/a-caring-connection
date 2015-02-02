package com.a_caring_reminder.app.models;

/**
 * Created by dan on 6/26/14.
 */
public class ScheduleItems {
    private String habit;
    private String time;
    private int id;

    public ScheduleItems(int id, String habit, String time){
        this.id = id;
        this.habit = habit;
        this.time = time;
    }

    public int getPosition() {

        return id;
    }

    public void setPosition(int position) {

        this.id = position;
    }

    public String getText() {

        return habit;
    }

    public void setText(String text) {

        this.habit = text;
    }

    public String getTime() {

        return time;
    }

    public void setTime(String time) {

        this.time = time;
    }

    @Override
    public String toString() {

        return this.habit;
    }
}
