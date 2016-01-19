package com.a_caring_reminder.app.texts;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a_caring_reminder.app.HabitListActivity;
import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.data.AcrQuery;
import com.a_caring_reminder.app.models.Text;
import com.a_caring_reminder.app.models.ScheduleItems;

import java.util.List;


/**
 * Created by dan on 6/26/14.
 */
public class TextListAdapter extends BaseAdapter {

    private itemID mCallbacks = sDummyCallbacks;

    private int resource;
    private static LayoutInflater inflater=null;
    private List<Text> names;
    Activity activity;
    ScheduleItems habits;
    AcrQuery query;
    String[] eq;

    boolean st = false;
    boolean manual = false;

    public interface itemID {
        /**
         * Callback for when an item has been selected.
         */
        public void getID(String data);
    }

    private static itemID sDummyCallbacks = new itemID() {

        public void getID(String data) {
        }
    };

    public TextListAdapter(Activity _activity, List<Text> _items) {

        try{

            activity = _activity;
            names = _items;

            st = true;
        }
        catch(Exception ex){
            String er = ex.getMessage();
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        holder = new ViewHolder();
        try{

            if (convertView == null) {

                LayoutInflater inflater = (activity).getLayoutInflater();
                convertView = inflater.inflate(R.layout.list_row_text, null);

                assert convertView != null;
                holder.txtName = (TextView) convertView.findViewById(R.id.rowTextView);
                holder.txtTime = (TextView) convertView.findViewById(R.id.rowTimeView);
                holder.txtMessage = (TextView) convertView.findViewById(R.id.rowMessageTextView);
                holder.txtDate = (TextView) convertView.findViewById(R.id.rowDateView);

                // holder.txtName.setTextColor(activity.getResources().getColor(R.color.UBJRed));

                convertView.setTag(holder);
            }
            else{
                holder=(ViewHolder)convertView.getTag();
            }

            //holder.txtName.setTag(String.valueOf(position));
            holder.txtName.setTag(names.get(position).getPosition());
            if (position != -1){
                String n = "Subject: " + names.get(position).getHabitSubject();
                String t = "Time: " + names.get(position).getHabitTime();
                String messageSubject = "Message: " + names.get(position).getHabitMessage();
                holder.txtName.setText(names.get(0).getContactName());
                holder.txtTime.setText(t);
                holder.txtMessage.setText(messageSubject);
                holder.txtDate.setText(names.get(position).getHabitDate());


            }



        }
        catch (Exception ex){
            String er = ex.getMessage();
        }
        holder.txtName.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
                try{
                    int title = Integer.valueOf(arg0.getTag().toString());

                    ((HabitListActivity)activity).onItemSelected(String.valueOf(title));
                    arg0.getResources().getColor(R.color.Yellow);
                }
                catch (Exception ex){
                    String er = ex.getMessage();
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView txtName;
        TextView txtTime;
        TextView txtMessage;
        TextView txtDate;

    }



    @Override
    public int getCount() {

        return names.size();
    }

    @Override
    public Object getItem(int arg0) {

        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;

    }


}

