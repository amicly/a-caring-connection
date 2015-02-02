package com.a_caring_reminder.app.supportMessages;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.a_caring_reminder.app.R;

/**
 * Created by justindelta on 7/8/14.
 */
public class SupportMessagesActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        if (findViewById(R.id.messages_container) != null) {

            MessageListFragment messageListFragment = new MessageListFragment();
            messageListFragment.setArguments(getIntent().getExtras());
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.messages_container, messageListFragment, MessageListFragment.TAG)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.messages_list_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        // Create a new support Message row (addNewItemBlank())
        switch (item.getItemId()) {
            case R.id.message_list_add:
                FragmentManager fragmentManager = getFragmentManager();
                MessageListFragment messageListFragment = (MessageListFragment) fragmentManager.findFragmentByTag(MessageListFragment.TAG);
                messageListFragment.addNewItemBlank();
                break;
            default:
                //nothing shown
        }
        return super.onOptionsItemSelected(item);
    }


}
