package com.a_caring_reminder.app.createAMessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.TodaysActivity;

/**
 * Created by Dan Bryant on 7/31/2014.
 */
public class SupportedDetailActivity extends Activity {

    SupportedDetailFragment fragment;
    InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_detail);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);


        // Show the Up button in the action bar.
        getActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();


            if (getIntent().getStringExtra(SupportedDetailFragment.ARG_HABIT_ID) != null) {

                arguments.putString(SupportedDetailFragment.ARG_HABIT_ID,
                        getIntent().getStringExtra(SupportedDetailFragment.ARG_HABIT_ID));



            }else {

            }

            try{

                fragment = new SupportedDetailFragment();
                fragment.setArguments(arguments);
                getFragmentManager().beginTransaction()
                        .add(R.id.support_detail_container, fragment)
                        .commit();
            }
            catch (Exception ex){
                Log.d("sdActivity", ex.getMessage());
            }

        }}


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu items for use in the action bar
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.habit_detail_actions, menu);
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this, TodaysActivity.class));
                return true;
            }

            switch (item.getItemId()) {


                case R.id.habit_details_save:

                    //Send the message
//                        SendMessage send = new SendMessage(getString(R.string.postMessage));
//
//                        send.execute(fragment.getMessage());

                    Intent ActivityIntent = new Intent(this, SupportedListActivity.class);
                    startActivity(ActivityIntent);

                    return true;


            }
            return super.onOptionsItemSelected(item);
        }
}
