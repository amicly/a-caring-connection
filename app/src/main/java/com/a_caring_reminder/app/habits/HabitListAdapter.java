package com.a_caring_reminder.app.habits;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.a_caring_reminder.app.HabitListActivity;
import com.a_caring_reminder.app.R;
import com.a_caring_reminder.app.data.AcrQuery;
import com.a_caring_reminder.app.models.Reminder;
import com.a_caring_reminder.app.models.ScheduleItems;

import java.util.List;


/**
 * Created by dan on 6/26/14.
 */
public class HabitListAdapter extends BaseAdapter {

    private itemID mCallbacks = sDummyCallbacks;

    private int resource;
    private static LayoutInflater inflater=null;
    private List<Reminder> names;
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

    public HabitListAdapter(Activity _activity, List<Reminder> _items) {

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
                convertView = inflater.inflate(R.layout.habit_list_row, null);

                assert convertView != null;
                holder.txtName = (TextView) convertView.findViewById(R.id.rowTextView);
                holder.txtTime = (TextView) convertView.findViewById(R.id.rowTimeView);

                // holder.txtName.setTextColor(activity.getResources().getColor(R.color.UBJRed));

                convertView.setTag(holder);
            }
            else{
                holder=(ViewHolder)convertView.getTag();
            }

            //holder.txtName.setTag(String.valueOf(position));
            holder.txtName.setTag(names.get(position).getPosition());
            if (position != -1){
                String n = names.get(position).getHabitSubject();
                String t = names.get(position).getHabitTime();
                holder.txtName.setText(n);
                holder.txtTime.setText(t);


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

