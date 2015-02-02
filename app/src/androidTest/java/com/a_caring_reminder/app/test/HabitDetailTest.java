package com.a_caring_reminder.app.test;


import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.EditText;

import com.a_caring_reminder.app.HabitDetailActivity;
import com.a_caring_reminder.app.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;

/**
 * Created by darrankelinske on 7/8/14.
 */
public class HabitDetailTest extends ActivityInstrumentationTestCase2<HabitDetailActivity> {

    public static Matcher<View> withHint(final String expectedHint) {
        return new TypeSafeMatcher<View>() {

            @Override
            public boolean matchesSafely(View view) {
                if (!(view instanceof EditText)) {
                    return false;
                }

                String hint = ((EditText) view).getHint().toString();

                return expectedHint.equals(hint);
            }

            @Override
            public void describeTo(Description description) {
            }
        };
    }

    @SuppressWarnings("deprecation")
    public HabitDetailTest() {

    super("com.a_caring_reminder.app", HabitDetailActivity.class);


    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    public void testNewHabitTitle() {
        onView(withId(R.id.habit_title))
                .perform(click());

        onView(withId(R.id.edit_text_habit_title)).check(matches(withHint("Enter Habit Title")));
        onView(withId(R.id.edit_text_habit_description)).check(matches(withHint("Enter Habit Description")));

    }





}
