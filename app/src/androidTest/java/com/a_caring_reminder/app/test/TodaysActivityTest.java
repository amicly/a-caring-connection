package com.a_caring_reminder.app.test;

import android.test.ActivityInstrumentationTestCase2;

import com.a_caring_reminder.app.TodaysActivity;


/**
 * Created by Dan on 8/25/2014.
 */
public class TodaysActivityTest extends ActivityInstrumentationTestCase2<TodaysActivity>{

    @SuppressWarnings("deprecation")
    public TodaysActivityTest(){
        super("com.a_caring_reminder.app", TodaysActivity.class);

    }

    @Override
    public void runTestOnUiThread(Runnable r) throws Throwable {
        super.runTestOnUiThread(r);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    /*public void testTodaysList(){
        onData(allOf(is(instanceOf(Map.class))));
    }*/
}
