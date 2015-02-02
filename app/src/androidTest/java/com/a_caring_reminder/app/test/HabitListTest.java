package com.a_caring_reminder.app.test;


import android.test.ActivityInstrumentationTestCase2;
import android.view.MenuItem;

import com.a_caring_reminder.app.HabitListActivity;
import com.a_caring_reminder.app.R;


import static com.a_caring_reminder.app.test.HabitDetailTest.withHint;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by darrankelinske on 7/8/14.
 */
public class HabitListTest extends ActivityInstrumentationTestCase2<HabitListActivity> {

    @SuppressWarnings("deprecation")
    public HabitListTest() {

        super("com.a_caring_reminder.app", HabitListActivity.class);


    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    public void testHabitItemClick() {
        onView(allOf(withId(R.id.rowTextView), withText("Morning routine")))
                .perform(click());

        onView(withId(R.id.edit_text_habit_title)).check(matches(withText("Morning routine")));

    }




    public void testOptionsMenu() {

        // We make sure the contextual action bar is hidden.
        //onView(withId(R.id.hide_contextual_action_bar))
        //        .perform(click());

        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());

        // Verify that we have really clicked on the icon by checking the TextView content.
        onView(withText("Messages"))
                .perform(click());



    }





}
