package com.a_caring_reminder.app.data;

import android.provider.BaseColumns;

/**
 * Created by darrankelinske on 6/20/14.
 * Attempt at implementing a contract class according to: https://developer.android.com/training/basics/data-storage/databases.html
 * Will need to refactor the arcDB class to use constants
 *
 */
public final class ArcReaderContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public ArcReaderContract() {}

    /* Inner class that defines the table contents */


    public static abstract class ArcHabits implements BaseColumns {
        public static final String TABLE_NAME = "Habit";
        public static final String COLUMN_NAME_ENTRY_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";

    }
}