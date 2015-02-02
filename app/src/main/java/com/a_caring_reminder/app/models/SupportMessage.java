package com.a_caring_reminder.app.models;

/**
 * Created by Taylor on 6/14/2014.
 */
public class SupportMessage {

    private int aUniqueID;
    private String mText;

    public SupportMessage(int aUniqueID, String aText) {
        this.aUniqueID = aUniqueID;
        this.mText = aText;
    }

    public int getUniqueID() {

        return aUniqueID;
    }

    public void setUniqueID(int aUniqueID) {

        this.aUniqueID = aUniqueID;
    }

    public String getText() {

        return mText;
    }

    public void setText(String aText) {

        this.mText = aText;
    }

    @Override
    public String toString() {
        return this.mText;
    }

}