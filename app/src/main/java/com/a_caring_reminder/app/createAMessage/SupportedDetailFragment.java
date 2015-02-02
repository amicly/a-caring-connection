package com.a_caring_reminder.app.createAMessage;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.AcrDB;
import com.a_caring_reminder.app.AcrQuery;

/**
 * Created by Taylor on 7/31/2014.
 */
public class SupportedDetailFragment extends Fragment {

    //SQLite class
    AcrDB acrDB;

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_HABIT_ID = "item_id";

    /**
     * The id to query for the data the fragment is presenting.
     */

    private String mID;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SupportedDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create the database here so that it gets the Activity Context.
        acrDB = new AcrDB(getActivity());
        AcrQuery query = new AcrQuery(acrDB);

        if (getArguments().containsKey(ARG_HABIT_ID)) {
            // Load the content using a query with an id specified by the fragment
                mID = getArguments().getString(ARG_HABIT_ID);

            getActivity().setTitle("Support " + mID);

        }


    }

    public String[] getMessage(){
        TextView id = (TextView) getView().findViewById(R.id.supported_one);
        TextView m  = (TextView) getView().findViewById(R.id.edit_message);
        String[] s = new String[] {id.getText().toString(),m.getText().toString()};
        id.setText("");
        m.setText("");
        return s;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_support_detail, container, false);

        // Show the content as text in a TextView.
        if (mID != null) {

            ((TextView) rootView.findViewById(R.id.supported_one)).setText(mID);

        } else {

        }

        return rootView;
    }

   /* @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        HabitDetailActivity mActivityReference = (HabitDetailActivity) getActivity();

        mActivityReference.setHabitDetailFields();


    }*/
}
