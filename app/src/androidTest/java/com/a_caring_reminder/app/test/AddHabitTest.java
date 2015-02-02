package com.a_caring_reminder.app.test;


import android.app.TimePickerDialog;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.TimePicker;

import com.a_caring_reminder.app.HabitDetailActivity;
import com.a_caring_reminder.app.R;
import com.google.android.apps.common.testing.ui.espresso.UiController;
import com.google.android.apps.common.testing.ui.espresso.ViewAction;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.*;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withClassName;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;

/**
 * Created by darrankelinske on 8/25/14.
 */
public class AddHabitTest extends ActivityInstrumentationTestCase2<HabitDetailActivity> {


    public static ViewAction setTime(final int hour, final int minute) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                TimePicker tp = (TimePicker) view;
                tp.setCurrentHour(hour);
                tp.setCurrentMinute(minute);
            }
            @Override
            public String getDescription() {
                return "Set the passed time into the TimePicker";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(TimePicker.class);
            }
        };
    }

    @SuppressWarnings("deprecation")
    public AddHabitTest() {

        super("com.a_caring_reminder.app", HabitDetailActivity.class);

    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }


    public void testAddNewHabit() {


        onView(withId(R.id.edit_text_habit_title)).perform(typeText("A Caring Reminder"));
        onView(withId(R.id.edit_text_habit_description)).perform(typeText("A Caring Reminder Description"));

        //onView(withId(R.id.text_view_habit_time)).perform(click());

        //onView(withClassName(Matchers.equalTo(TimePicker.class.getName()))).perform(setTime(23, 59));

        onView(withId(R.id.habit_details_save))
                .perform(click());


    }

}
