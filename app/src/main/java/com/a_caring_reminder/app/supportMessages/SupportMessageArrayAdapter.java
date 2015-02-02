package com.a_caring_reminder.app.supportMessages;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.AcrDB;
import com.a_caring_reminder.app.AcrQuery;
import com.a_caring_reminder.app.models.SupportMessage;

import java.util.HashMap;
import java.util.List;

/**
 * Created by justindelta on 7/9/14.
 */
public class SupportMessageArrayAdapter extends ArrayAdapter<SupportMessage> {

    private static final int EDIT_TEXT_KEY = 0;
    private static final int ROW_POSITION_KEY = 1;

    private Context context;
    private int layoutResourceId;
    private List<SupportMessage> supportMessages;
    private AcrDB acrDB;
    private AcrQuery acrQuery;

    public SupportMessageArrayAdapter(Context aContext, int aResource, List<SupportMessage> aSupportMessage) {
        super(aContext, aResource, aSupportMessage);
        this.context = aContext;
        this.layoutResourceId = aResource;
        this.supportMessages = aSupportMessage;

        acrDB = new AcrDB(context);
        acrQuery = new AcrQuery(acrDB);
    }


    @Override
    public int getCount() {
        return supportMessages.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        SupportMessageHolder supportMessageHolder;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        if (row == null) {
            row = layoutInflater.inflate(layoutResourceId, parent, false);
            supportMessageHolder = new SupportMessageHolder();
            supportMessageHolder.messageEditText = (EditText) row.findViewById(R.id.edittext_firstLine);
            supportMessageHolder.imageButtonIcon = (ImageButton) row.findViewById(R.id.icon);
            row.setTag(supportMessageHolder);

        } else {
            supportMessageHolder = (SupportMessageHolder) row.getTag();
        }

        SupportMessage supportMessage = supportMessages.get(position);
        //edit text needs to know its row.
        Integer rowPosition = position;

        // set text to EditText View
        supportMessageHolder.messageEditText.setText(supportMessage.getText());
        //edit text needs to know its row.

        supportMessageHolder.messageEditText.setTag(supportMessageHolder.imageButtonIcon);
        // focusListener for EditText is triggered to change ImageButton
        supportMessageHolder.messageEditText.setOnFocusChangeListener(editTextFocusListener);

        // TouchListener triggers check for current ImageButton resource allow user
        // to either update the current view or delete the row]

        //We want to store two references, so we use a Hashmap
        HashMap<Integer,Object> imageMap = new HashMap<Integer, Object>(3);

        imageMap.put(EDIT_TEXT_KEY, supportMessageHolder.messageEditText);
        imageMap.put(ROW_POSITION_KEY,rowPosition);

        supportMessageHolder.imageButtonIcon.setTag(imageMap);
        supportMessageHolder.imageButtonIcon.setOnTouchListener(imageButtonListener);

        return row;
    }



    public class SupportMessageHolder {
        public EditText messageEditText;
        public ImageButton imageButtonIcon;
    }

    public void addNewMessageItem() {
        addNewSupportMessage("");
    }

    //
    //  Listeners
    //
    View.OnFocusChangeListener editTextFocusListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            ImageButton imageButton = (ImageButton) v.getTag();
            if (hasFocus) {
                imageButton.setImageResource(R.drawable.ic_action_accept);
                // if EditText has focus, change icon from trash can to check mark button
                // no need for notifyDataSetChanged(); will lose focus.
            } else {
                // if EditText loses focus, change icon back to trash can (ic_action_discard)
                imageButton.setImageResource(R.drawable.ic_action_discard);
                notifyDataSetChanged();
            }
        }
    };

    View.OnTouchListener imageButtonListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            // get necessary references
            ImageButton imageButton = (ImageButton) v;
            HashMap<Integer,Object> imageMap = (HashMap<Integer, Object>) v.getTag();
            EditText editText = (EditText) imageMap.get(EDIT_TEXT_KEY);
            Integer rowPosition = (Integer) imageMap.get(ROW_POSITION_KEY);

            //capture user input
            if (event.getAction() == MotionEvent.ACTION_UP) {
                // check if the resource image set on the view is ic_action_discard
                if(isImageTrash(imageButton)) {
                    // remove from database based on position when user
                    removeSupportMessage(supportMessages.get(rowPosition));
                    notifyDataSetChanged();
                }else{ // Save button (ic_action_accept) is touched
                    imageButton.setImageResource(R.drawable.ic_action_discard);
                    // set focus on ImageButton to true
                    imageButton.setFocusable(true);
                    //use passed supportMessage for aligned db and view update
                    SupportMessage supportMessage = supportMessages.get(rowPosition);
                    updateSupportMessage(supportMessage, editText.getText().toString());

                    // Force hiding virtual keyboard
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    notifyDataSetChanged();
                }
            }
            return false;
        }
    };

    private boolean isImageTrash(ImageButton imageButton){
        if(imageButton.getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.ic_action_discard).getConstantState()){
            return true;
        }else{
            return false;
        }
    }

    //
    // Database manipulation methods
    //
    // add a new support message row in view and set to empty string to show hint
    private void addNewSupportMessage(String message) {
        //Add a new blank message to the database
        try {
            if (message != null) {
                message = "";
            }
            // if Table has messages,
            if(acrQuery.getMaxMessageID() >= 0) {
                acrQuery.addMessage((acrQuery.getMaxMessageID() + 1), message);
            }else{
                acrQuery.addMessage(0, message);
            }
        } catch (Exception e) {
            Log.e("EXCEPTION IS : ", String.valueOf(e.getMessage()));
        }
        updateMessagesFromDB();
    }

    // update current EditText View and corresponding database Message table.
    private void updateSupportMessage(SupportMessage aSupportMessage, String message) {
        //update message with inserted message
        try {
            int currentMessageLoc = aSupportMessage.getUniqueID();
            acrQuery.updateMessageDetails(currentMessageLoc, message);

        } catch (Exception e) {
            Log.e("EXCEPTION IS :", String.valueOf(e.getMessage()));
        }
        updateMessagesFromDB();
    }

    // remove message from database
    private void removeSupportMessage(SupportMessage aSupportMessage){
        // remove message from database, based on ID
        try {
            acrQuery.deleteSupportMessageByID(aSupportMessage.getUniqueID());

        } catch (Exception e) {
            Log.e("EXCEPTION IS :", String.valueOf(e.getMessage()));
        }
        updateMessagesFromDB();
    }

    private void updateMessagesFromDB(){
        List<SupportMessage>  updatedListOfMessages = acrQuery.getSupportMessageListItems();
        setSupportMessages(updatedListOfMessages);
    }

    public List<SupportMessage> getSupportMessage() {
        return this.supportMessages;
    }
    public void setSupportMessages(List<SupportMessage> aSupportMessage) {
        this.supportMessages = aSupportMessage;
        notifyDataSetChanged();
    }


}



