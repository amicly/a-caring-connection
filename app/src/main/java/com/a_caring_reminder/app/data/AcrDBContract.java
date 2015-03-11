package com.a_caring_reminder.app.data;

import android.provider.BaseColumns;

/**
 * Created by darrankelinske on 6/20/14.
 */
public final class AcrDBContract {
    public AcrDBContract() {}

    public static abstract class ScheduledAlarmEntry implements BaseColumns {
        public static final String TABLE_NAME = "ScheduledAlarms";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_HABIT_ID = "habit_id";
        public static final String COLUMN_NAME_MESSAGE_ID = "message_id";
        public static final String COLUMN_NAME_TIME_OF_DAY = "time_of_day";
        public static final String COLUMN_NAME_DATE = "date";


    }



}
