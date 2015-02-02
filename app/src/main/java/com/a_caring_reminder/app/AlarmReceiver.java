package com.a_caring_reminder.app;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.a_caring_reminder.app.models.ScheduledAlarm;

public class AlarmReceiver extends BroadcastReceiver {

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
        String habitTitle = query.getHabitTime(String.valueOf(mScheduledAlarm.getHabitID())) + " "  + query.getHabitTitle(String.valueOf(mScheduledAlarm.getHabitID()));
        Log.i("ACR", "Setting Habit Title as " + habitTitle);
        String messageText = query.getSupportMessageDetail(String.valueOf(query.getRandomSupportMessageID()));
        Log.i("ACR", "Setting Message Text as " + messageText);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_action_warning)
                .setContentTitle(habitTitle)
                .setContentText(messageText)
                .setSound(soundURI)
                .setVibrate(mVibratePattern);


        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, mBuilder.build());

    }
}