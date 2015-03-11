package com.a_caring_reminder.app;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.a_caring_reminder.app.createAMessage.SupportedListActivity;
import com.a_caring_reminder.app.data.AcrDB;
import com.a_caring_reminder.app.data.AcrQuery;
import com.a_caring_reminder.app.supportMessages.SupportMessagesActivity;

import java.util.List;

/**
 * Created by Dan on 7/7/2014.
 */
public class TodaysActivity extends ExpandableListActivity {
    private BaseExpandableListAdapter adapter;
    public ExpandableListView elv;
    private int group;
    Button select,api_call;

    AcrQuery query;

    @Override
    protected void onStart(){
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = this.getSharedPreferences(
                "com.a_caring_reminder.app", Context.MODE_PRIVATE);

            //Check the Play Services Token and reg id, if either is not there
            // move to the splash activity to get it
            //
            if (prefs.getString("Access Token", "") == "" || prefs.getString("regid", "") == "") {
                Intent splashIntent = new Intent(this, SplashActivity.class);
                startActivity(splashIntent);
            }
            else{
                AcrDB acrDB = new AcrDB(this);
                query = new AcrQuery(acrDB);
                try {

                    AcrDB db = new AcrDB(this);
                    AcrQuery query = new AcrQuery(db);
                    getActionBar().setTitle(query.getDate());
                    List<String> item_names = query.getTodaysHabitList();

                    adapter = new ExpandingAdapter(this, item_names, query.messageMap(item_names.size()));

                    setListAdapter(adapter);

                    for (int i = 0; i < getExpandableListView().getCount(); i++){
                        getExpandableListView().expandGroup(i);
                    }

                }
                catch (Exception ex){
                    Log.d("todayCreate", ex.getMessage());
                }
            }




    }

    public boolean onCreateOptionsMenu(Menu menu){
        try{
            getMenuInflater().inflate(R.menu.habit_list_activity_actions, menu);
            super.onCreateOptionsMenu(menu);

        }
        catch(Exception ex){
            String d = ex.getMessage();

            Log.d("createMenuError", d);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.messages:
                Intent messageActivityIntent = new Intent(this, SupportMessagesActivity.class);
                startActivity(messageActivityIntent);

                return true;

            case R.id.habits:
                Intent habitListIntent = new Intent(this, HabitListActivity.class);
                startActivity(habitListIntent);

                return true;

            case R.id.support:
                Intent ListIntent = new Intent(this, SupportedListActivity.class);
                startActivity(ListIntent);

                return true;

        }
        return super.onOptionsItemSelected(item);
    }


}
