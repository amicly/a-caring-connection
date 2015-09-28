package com.a_caring_reminder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.a_caring_reminder.app.createAMessage.SupportedListActivity;
import com.a_caring_reminder.app.pageradapter.AcrPagerAdapter;
import com.a_caring_reminder.app.supportMessages.SupportMessagesActivity;


/**
 * An activity representing a list of habits. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link HabitDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link HabitListFragment} and the item details
 * (if present) is a {@link HabitDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link HabitListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class HabitListActivity extends ActionBarActivity {


    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private ViewPager mViewPager;
    private PagerTabStrip mPagerTabStrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        AcrPagerAdapter adapter = new AcrPagerAdapter();
        mViewPager = (ViewPager) findViewById(R.id.threepanelpager);

        mViewPager.setAdapter(adapter);

        mPagerTabStrip = (PagerTabStrip) findViewById(R.id.view_pager_tab_strip);
        mPagerTabStrip.setTabIndicatorColorResource(R.color.primary_material_light);







    }

    /**
     * Callback method from {@link HabitListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */

    public void onItemSelected(String description) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(HabitDetailFragment.ARG_HABIT_ID, description);
            HabitDetailFragment fragment = new HabitDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager().beginTransaction()
                    .replace(R.id.habit_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, HabitDetailActivity.class);
            detailIntent.putExtra(HabitDetailFragment.ARG_HABIT_ID, description);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        try{

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

            /*
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
                */

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
