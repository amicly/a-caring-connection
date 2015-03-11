package com.a_caring_reminder.app;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.a_caring_reminder.app.data.AcrQuery;
import com.a_caring_reminder.app.models.ScheduleItems;

import java.util.List;

/**
 * Created by dan on 6/26/14.
 */
public class CustomChkAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener {

    private itemID mCallbacks = sDummyCallbacks;

    private int resource;
    private static LayoutInflater inflater=null;
    private List<ScheduleItems> names;
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

    public CustomChkAdapter(Activity _activity, List<ScheduleItems> _items) {

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
                convertView = inflater.inflate(R.layout.chk_row, null);

                assert convertView != null;
                holder.txtName = (TextView) convertView.findViewById(R.id.rowTextView);
                holder.txtTime = (TextView) convertView.findViewById(R.id.rowTimeView);
                holder.chkRow = (CheckBox) convertView.findViewById(R.id.rowCheck);
                // holder.txtName.setTextColor(activity.getResources().getColor(R.color.UBJRed));

                convertView.setTag(holder);
            }
            else{
                holder=(ViewHolder)convertView.getTag();
            }

            //holder.txtName.setTag(String.valueOf(position));
            holder.txtName.setTag(names.get(position).getPosition());
            if (position != -1){
                String n = names.get(position).getText();
                String t = names.get(position).getTime();
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
        CheckBox chkRow;

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked){
            buttonView.setBackgroundColor(activity.getResources().getColor(R.color.Blue));
        }
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
