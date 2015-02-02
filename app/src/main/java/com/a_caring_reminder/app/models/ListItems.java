package com.a_caring_reminder.app.models;

/**
 * Created by Taylor on 6/14/2014.
 */
public class ListItems {

    private String mText;
    private int mPosition;

    public ListItems(int position, String text) {
        this.mPosition = position;
        this.mText = text;

    }

    public int getPosition() {

        return mPosition;
    }

    public void setPosition(int position) {

        this.mPosition = position;
    }

    public String getText() {

        return mText;
    }

    public void setText(String text) {

        this.mText = text;
    }

    @Override
    public String toString() {

        return this.mText;
    }
}