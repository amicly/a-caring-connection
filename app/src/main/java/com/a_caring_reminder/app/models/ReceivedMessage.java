package com.a_caring_reminder.app.models;

/**
 * Created by justindelta on 8/5/14.
 */
public class ReceivedMessage {

    private int mUniqueID;
    private String mRegID;
    private String mEmail;
    private String mMsg;

    public ReceivedMessage(int aUniqueID, String aRegID, String aEmail, String aMsg) {
        this.mUniqueID = aUniqueID;
        this.mRegID = aRegID;
        this.mEmail = aEmail;
        this.mMsg = aMsg;
    }

    public int getUniqueID() {
        return mUniqueID;
    }

    public void setUniqueID(int aUniqueID) {
        this.mUniqueID = aUniqueID;
    }

    public String getRegID() {
        return mRegID;
    }

    public void setRegID(String aRegID) {
        this.mRegID = aRegID;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String anEmail) {
        this.mEmail = anEmail;
    }

    public String getText() {
        return mMsg;
    }

    public void setText(String aMsg) {
        this.mMsg = aMsg;
    }
}
