package com.a_caring_reminder.app.texts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.data.AcrDB;
import com.a_caring_reminder.app.data.AcrQuery;
import com.a_caring_reminder.app.models.Text;
import com.a_caring_reminder.app.textdetail.TextDetailActivity;
import com.a_caring_reminder.app.textdetail.TextDetailFragment;

import java.util.List;

/**
 * A list fragment representing a list of habits. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link TextDetailFragment}.
 * <p/>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class TextsFragment extends ListFragment {


    private static final String LOG_TAG = TextsFragment.class.getSimpleName();
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";
    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String description) {
        }
    };
    //SQLite class
    com.a_caring_reminder.app.data.AcrDB AcrDB;
    //listItem type in models for listview
    private List<Text> ITEMS;
    private TextListAdapter adapter;
    private View rootView;
    private ListView listView;
    private RecyclerView mTextRecyclerView;
    private TextAdapter mAdapter;
    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;
    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TextsFragment() {
    }

    public static TextsFragment newInstance() {
        TextsFragment f = new TextsFragment();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_habit_list, container, false);

        mTextRecyclerView = (RecyclerView) root.findViewById(R.id.text_recycler_view);
        mTextRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create Blank Detail Intent
                Intent detailIntent = new Intent(getActivity(), TextDetailActivity.class);
                //Start Detail Activity with Blank Intent
                startActivity(detailIntent);
            }


        });

        updateUI();

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }

    }


    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }


    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        Log.i("ACR", "The id for the habit at this is" + ITEMS.get(position).getPosition());


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);

    }

    private class TextHolder extends RecyclerView.ViewHolder {

        public TextView textRecipient;
        public TextView textTime;
        public TextView textMessage;
        public TextView textDate;

        public TextHolder(View itemView) {
            super(itemView);
            textRecipient = (TextView) itemView.findViewById(R.id.rowTextView);
            textTime = (TextView) itemView.findViewById(R.id.rowTimeView);
            textMessage = (TextView) itemView.findViewById(R.id.rowMessageTextView);
            textDate = (TextView) itemView.findViewById(R.id.rowDateView);
        }

    }

    private class TextAdapter extends RecyclerView.Adapter<TextHolder> {
        private List<Text> mTexts;

        public TextAdapter(List<Text> texts) {
            mTexts = texts;
        }

        @Override
        public TextHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_row_text, parent, false);
            return new TextHolder(view);
        }

        @Override
        public void onBindViewHolder(TextHolder holder, int position) {
            Text text = mTexts.get(position);
            holder.textRecipient.setText(text.getRecipientName());
            holder.textTime.setText(text.getTextTime());
            holder.textMessage.setText(text.getTextMessage());
            holder.textDate.setText(text.getTextDate());
        }

        @Override
        public int getItemCount() {
            return mTexts.size();
        }
    }

    private void updateUI() {
        AcrDB = new AcrDB(getActivity());
        AcrQuery query = new AcrQuery(AcrDB);
        ITEMS = query.upcomingTextList(AcrDB);
        mAdapter = new TextAdapter(ITEMS);
        mTextRecyclerView.setAdapter(mAdapter);
    }


}
