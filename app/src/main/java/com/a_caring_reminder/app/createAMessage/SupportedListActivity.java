package com.a_caring_reminder.app.createAMessage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.a_caring_reminder.app.HabitListActivity;
import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.TodaysActivity;
import com.a_caring_reminder.app.services.SendMessage;
import com.a_caring_reminder.app.supportMessages.SupportMessagesActivity;

/**
 * Created by Dan Bryant on 7/31/2014.
 */
public class SupportedListActivity extends Activity
        implements SupportedListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    TextView mOne;
    EditText mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supported_list);
        getActionBar().setHomeButtonEnabled(true);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);


        if (findViewById(R.id.habit_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((SupportedListFragment) getFragmentManager()
                    .findFragmentById(R.id.supported_list))
                    .setActivateOnItemClick(true);
        }

        mOne = (TextView) findViewById(R.id.supported_one);
        mMessage = (EditText) findViewById(R.id.edit_message);
    }

    /**
     * Callback method from {@link SupportedListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String description) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(SupportedDetailFragment.ARG_HABIT_ID, description);
            SupportedDetailFragment fragment = new SupportedDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.supported_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, SupportedDetailActivity.class);
            detailIntent.putExtra(SupportedDetailFragment.ARG_HABIT_ID, description);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        try{
            getMenuInflater().inflate(R.menu.menu, menu);
            super.onCreateOptionsMenu(menu);

        }
        catch(Exception ex){
            //example
            Log.d("createMenuError", ex.getMessage());
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpTo(this, new Intent(this, TodaysActivity.class));
                return true;

            case R.id.messages:

                Intent messageActivityIntent = new Intent(this, SupportMessagesActivity.class);
                startActivity(messageActivityIntent);

                return true;

            case R.id.habits:
                Intent habitListIntent = new Intent(this, HabitListActivity.class);
                startActivity(habitListIntent);

                return true;

            case R.id.a1_button:
                //fire up the SendMessage asyncTask and execute with url and array
                SendMessage send = new SendMessage(getString(R.string.postMessage));

                String one =  mOne.getText().toString();
                String message = mMessage.getText().toString();

                send.execute(new String[]{one, message});

                mOne.setText("");
                mMessage.setText("");

                Toast.makeText(this, "message queued", Toast.LENGTH_LONG).show();

                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
