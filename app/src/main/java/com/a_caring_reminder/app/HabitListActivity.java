package com.a_caring_reminder.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;


/**
 * An activity representing a list of habits. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link HabitDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link TextsFragment} and the item details
 * (if present) is a {@link HabitDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link TextsFragment.Callbacks} interface
 * to listen for item selections.
 */
public class HabitListActivity extends FragmentActivity{


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

        AcrPagerAdapter adapter = new AcrPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(mViewPager);



    }

    /**
     * Callback method from {@link TextsFragment.Callbacks}
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//
//            /*
//            case android.R.id.home:
//                // This ID represents the Home or Up button. In the case of this
//                // activity, the Up button is shown. Use NavUtils to allow users
//                // to navigate up one level in the application structure. For
//                // more details, see the Navigation pattern on Android Design:
//                //
//                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
//                //
//                NavUtils.navigateUpTo(this, new Intent(this, TodaysActivity.class));
//                return true;
//                */
//
//            case R.id.messages:
//
//                Intent messageActivityIntent = new Intent(this, SupportMessagesActivity.class);
//                startActivity(messageActivityIntent);
//
//                return true;
//
//            case R.id.habits:
//                Intent habitListIntent = new Intent(this, HabitListActivity.class);
//                startActivity(habitListIntent);
//
//                return true;
//
//            case R.id.support:
//                Intent ListIntent = new Intent(this, SupportedListActivity.class);
//                startActivity(ListIntent);
//
//                return true;
//
//
//
//        }
//        return super.onOptionsItemSelected(item);
//    }

    public static class AcrPagerAdapter extends FragmentPagerAdapter {

        private String tabTitles[] = new String[] { "Upcoming Texts", "Past Texts"};

        public AcrPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        public int getCount() {
            return 2;
        }

        @Override
        public Fragment getItem(int position) {

            int resId = 0;
            switch (position) {
                case 0:
                    return TextsFragment.newInstance();
                case 1:
                    return TextsFragment.newInstance();
                case 2:
                    return TextsFragment.newInstance();
                default:
                    return null;

            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }




}
