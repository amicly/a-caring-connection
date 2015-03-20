package com.a_caring_reminder.app.Util;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.a_caring_reminder.app.data.AcrDB;

/**
 * Created by daz on 3/19/15.
 */
public class ReminderUtils {



        public static void dumpReminders (Context context, AcrDB acrDB)

        {



            String LOG_TAG = context.getClass().getSimpleName();

            Log.i(LOG_TAG, "Attempting to dump Reminder table");


            Cursor db;
            try {
                db = acrDB.getReadableDatabase().rawQuery("Select * from Reminder", null);
                if (db.moveToFirst()) {
                    Log.i(LOG_TAG, "Dumping database cursor");
                    DatabaseUtils.dumpCursor(db);
                }

            }
            catch (Exception ex){
                Log.d(LOG_TAG, ex.getMessage());

            }


        }


}
