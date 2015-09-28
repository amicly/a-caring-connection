package com.a_caring_reminder.app.pageradapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;

import com.a_caring_reminder.app.R;

/**
 * Created by darrankelinske on 1/11/15.
 */
public class AcrPagerAdapter extends PagerAdapter {

    public int getCount() {
        return 2;
    }

    private String tabTitles[] = new String[] { "Habits", "Settings"};

    public Object instantiateItem(View collection, int position) {

        LayoutInflater inflater = (LayoutInflater) collection.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int resId = 0;
        switch (position) {
            case 0:
                resId = R.layout.activity_habit_list;
                break;
            case 1:
                resId = R.layout.fragment_message_list;
                break;
            case 2:
                resId = R.layout.activity_supported_list;
                break;

        }

        View view = inflater.inflate(resId, null);

        ((ViewPager) collection).addView(view, 0);



        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);

    }


    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);

    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}