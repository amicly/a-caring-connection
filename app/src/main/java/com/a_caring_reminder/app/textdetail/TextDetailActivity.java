package com.a_caring_reminder.app.textdetail;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.WindowManager;

import com.a_caring_reminder.app.HabitListActivity;
import com.a_caring_reminder.app.R;


/**
 * An activity representing a single habit detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link HabitListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link TextDetailFragment}.
 */
public class TextDetailActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habit_detail);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        Bundle arguments = new Bundle();

        if (getIntent().getStringExtra(TextDetailFragment.ARG_HABIT_ID) != null) {

            arguments.putString(TextDetailFragment.ARG_HABIT_ID, getIntent().getStringExtra(TextDetailFragment.ARG_HABIT_ID));

        }

            if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.


            TextDetailFragment fragment = new TextDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .add(R.id.habit_detail_container, fragment)
                    .commit();
        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        return super.onCreateOptionsMenu(menu);
    }




}




