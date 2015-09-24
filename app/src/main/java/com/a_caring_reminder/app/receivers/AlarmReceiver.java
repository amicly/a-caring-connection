package com.a_caring_reminder.app.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsManager;
import android.util.Log;

import com.a_caring_reminder.app.data.AcrDB;
import com.a_caring_reminder.app.data.AcrQuery;
import com.a_caring_reminder.app.HabitListActivity;
import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.models.ScheduledAlarm;

public class AlarmReceiver extends BroadcastReceiver {


    String LOG_TAG = getClass().getSimpleName();

    private int NOTIFICATION_ID = 1;
    private Uri soundURI = Uri
            .parse("android.resource://com.a_caring_reminder.app/"
                    + R.raw.alarm_rooster);
    private long[] mVibratePattern = { 0, 200, 200, 300 };
    private int mScheduledAlarmID;

    @Override
    public void onReceive(Context context, Intent intent) {

        mScheduledAlarmID =  intent.getIntExtra("com.a_caring_reminder.app.alarm_id", 2);
        //Toast.makeText(context, String.valueOf(intent.getIntExtra(HabitDetailActivity.ALARM_ID, 1)), Toast.LENGTH_SHORT).show();



        Intent resultIntent = new Intent(context, HabitListActivity.class);

        PendingIntent resultPendingIntent =
                TaskStackBuilder.create(context).addNextIntent(resultIntent).addParentStack(HabitListActivity.class).getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        AcrDB acrDB = new AcrDB(context);
        AcrQuery query = new AcrQuery(acrDB);
        Log.i("ACR", "Scheduled Alarm ID is "+ String.valueOf(mScheduledAlarmID));
        ScheduledAlarm mScheduledAlarm = query.getScheduledAlarm(String.valueOf(mScheduledAlarmID));
        Log.i("ACR", "The Habit ID for the alarm is  " + String.valueOf(mScheduledAlarm.getHabitID()));
        //String habitTitle = query.getHabitTime(String.valueOf(mScheduledAlarm.getHabitID())) + " "  + query.getHabitTitle(String.valueOf(mScheduledAlarm.getHabitID()));

        String habitNotificationTitle = "Sending message to Darran";

        Log.i("ACR", "Setting Habit Title as " + habitNotificationTitle);
        String messageText = query.getSupportMessageDetail(String.valueOf(query.getRandomSupportMessageID()));

        String habitNotificationText = "Time: " + query.getHabitTime(String.valueOf(mScheduledAlarm.getHabitID())) + " Subject: " + query.getHabitDetail(String.valueOf(mScheduledAlarm.getHabitID()));
        Log.i("ACR", "Setting Message Text as " + messageText);




        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.acc_heart)
                .setContentTitle(habitNotificationTitle)
                .setContentText(messageText)
                .setVibrate(mVibratePattern);


        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());



        Log.i(LOG_TAG, "Sending SMS for reminder");

        SmsManager smsManager = SmsManager.getDefault();

        Log.i(LOG_TAG, "Sending SMS message to 512-693-7499");

        smsManager.sendTextMessage(query.getScheduledAlarmPhoneNumber(mScheduledAlarmID), null, "Hi! " + habitNotificationText, null, null);








    }
}