package com.a_caring_reminder.app.test;

import android.test.ActivityInstrumentationTestCase2;

import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.createAMessage.SupportedListActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onData;
import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;

import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.click;
import static com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions.matches;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.text.StringStartsWith.startsWith;


/**
 * Created by Dan on 8/25/2014.
 */
public class CreateMessageTest extends ActivityInstrumentationTestCase2<SupportedListActivity> {

    @SuppressWarnings("deprecation")
    public CreateMessageTest(){
        super("com.a_caring_reminder.app", SupportedListActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        // Espresso will not launch our activity for us, we must launch it via getActivity().
        getActivity();
    }

    public void testSupportedClick() {
        //onView((withId(android.R.id.text1))).perform(click());

        /*onView((withId(android.R.id.text1)))
                .perform(click());*/

        onData(hasToString(startsWith("darran"))).perform(click());

        onView(withId(R.id.supported_one)).check(matches(withText("darran.kelinske@gmail.com")));

    }
}
