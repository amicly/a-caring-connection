package com.a_caring_reminder.app;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.a_caring_reminder.app.models.ReceivedMessage;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.List;

/**
 * Created by dan on 7/16/14.
 */
public class GcmMessageHandler extends IntentService {

    String mes;
    String email;
    private Handler handler;
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    AcrDB acrDB;
    AcrQuery acrQuery;
    private List<ReceivedMessage> receivedMessage;

    public GcmMessageHandler() {
        super("GcmMessageHandler");
    }
        @Override
        public void onCreate() {
            // TODO Auto-generated method stub
            super.onCreate();
            handler = new Handler();

        }
        @Override
        protected void onHandleIntent(Intent intent) {
            Bundle extras = intent.getExtras();

            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
            // The getMessageType() intent parameter must be the intent you received
            // in your BroadcastReceiver.
            String messageType = gcm.getMessageType(intent);

            mes = extras.getString("message");
            email = extras.getString("email");



            saveReceivedMessageToDBTables(getApplicationContext(), email, mes);

            //showToast();
            // Log.i("GCM", "Received : (" + messageType + ")  " + extras.getString("message"));
//            sendNotification("Received: " + mes);
            sendNotification("A Caring Reminder, " + mes);


            GcmBroadcastReceiver.completeWakefulIntent(intent);
        }


    public void showToast(){
            handler.post(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(),mes , Toast.LENGTH_LONG).show();
                }
            });
        }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)
                this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, TodaysActivity.class), 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.powered_by_google_light)
                        .setContentTitle("GCM Notification")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setSound(alarmSound)
                        .setAutoCancel(true)
                        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    //
    // Save received message and email etc.
    //
    private void saveReceivedMessageToDBTables(Context aContext, String anEmail, String aMessage){
        acrDB = new AcrDB(aContext);
        acrQuery = new AcrQuery(acrDB);



        if(anEmail == null && aMessage == null){
            Log.e("saveReceivedMessageToDB Error", "email: " + anEmail + "message" + aMessage);
        }else {

            // TODO determine is NEEDS REG ID is necessary here
            //
            // saving to SupportedUsers Table in db
            //
            if (acrQuery.getMaxReceivedMessageID() >= 0) {
                acrQuery.addReceivedMessage((acrQuery.getMaxReceivedMessageID() + 1), "needs_reg_id", anEmail, aMessage);
            } else {
                acrQuery.addReceivedMessage(0, "needs_reg_id", anEmail, aMessage);
            }

            //
            // Saving to Message Table in db
            //
            if (acrQuery.getMaxMessageID() >= 0) {
                acrQuery.addMessage((acrQuery.getMaxMessageID() + 1), aMessage);
            } else {
                acrQuery.addMessage(0, aMessage);
            }
        }

    }

    private void updateMessagesFromDB(){
        List<ReceivedMessage> updatedListOfMessages = acrQuery.getReceivedMessageListItems();
        setReceivedMessage(updatedListOfMessages);
    }

    public List<ReceivedMessage> getReceivedMessage() {
        return this.receivedMessage;
    }
    public void setReceivedMessage(List<ReceivedMessage> aReceivedMessage) {
        this.receivedMessage = aReceivedMessage;
    }
}
