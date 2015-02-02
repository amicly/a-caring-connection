package com.a_caring_reminder.app.supportMessages;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.AcrDB;
import com.a_caring_reminder.app.AcrQuery;
import com.a_caring_reminder.app.models.SupportMessage;

import java.util.List;

/**
 * Created by justindelta on 7/8/14.
 */
public class MessageListFragment extends Fragment {

    public static final String TAG = "MessageListFragment";

    private List<SupportMessage> supportMessages;
    private AcrDB acrDB;
    private AcrQuery query;
    private ListView listView;
    private View rootView;
    protected SupportMessageArrayAdapter supportMessageArrayAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // preserve fragment state when rotation changes portrait <--> landscape
        setRetainInstance(true);

        try {
            acrDB = new AcrDB(getActivity());
            query = new AcrQuery(acrDB);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.d(TAG," Error getting data: " + ex.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null)
                parent.removeView(rootView);
        }
        try {
            rootView = inflater.inflate(R.layout.fragment_messages, container, false);
            listView = (ListView) rootView.findViewById(R.id.messageListView);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(query != null){
            supportMessages = query.getSupportMessageListItems();
            supportMessageArrayAdapter = new SupportMessageArrayAdapter(getActivity(), R.layout.fragment_messages_rows, supportMessages);
            listView.setAdapter(supportMessageArrayAdapter);
        }
    }

    public void addNewItemBlank(){
        supportMessageArrayAdapter.addNewMessageItem();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MessageListFragment f = (MessageListFragment) getFragmentManager()
                .findFragmentById(R.id.messages_container);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }
}