package com.a_caring_reminder.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.a_caring_reminder.app.models.ScheduleItems;

import java.util.List;
import java.util.Map;

/**
 * Created by Taylor on 7/7/2014.
 */
public class ExpandingAdapter extends BaseExpandableListAdapter {

    private Activity context;
    private Map<Integer, List<String>> itemcollections;
    private List<String> item;
    private TextView childtv;
    private LayoutInflater inflater;

    public void setInflater(LayoutInflater inflater) {

        this.inflater = inflater;

    }

    public ExpandingAdapter(Activity context, List<String> item_names, Map<Integer, List<String>> collections){
        this.context = context;
        this.item = item_names;
        this.itemcollections = collections;
    }

    @Override
    public Object getChild(int groupposition, int childposition) {
        // TODO Auto-generated method stub

        return itemcollections.get(groupposition).get(childposition);

    }

    @Override
    public long getChildId(int groupposition, int childposition) {
        // TODO Auto-generated method stub

        return childposition;

    }

    @Override
    public View getChildView(int groupposition, int childpostion, boolean isLastchild, View convertview,
                             ViewGroup parent) {
        // TODO Auto-generated method
        try {
            final String childitem = (String) getChild(groupposition, childpostion);
            LayoutInflater inflater = context.getLayoutInflater();
            if (convertview == null) {
                convertview = inflater.inflate(R.layout.child_row, null);
            }
            childtv = (TextView) convertview.findViewById(R.id.childname);
            childtv.setText(childitem);
        }
        catch (Exception ex){
            Log.d("expandingChild", ex.getMessage());
        }
        return convertview;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try {

            return this.itemcollections.get(groupPosition).size();
        }
        catch (Exception ex){
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.item.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.item.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded,
                             View convertView, final ViewGroup parent) {
        try {
            ScheduleItems headerTitle = (ScheduleItems) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater infalInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = infalInflater.inflate(R.layout.chk_row, null);
            }

            final TextView txtName = (TextView) convertView
                    .findViewById(R.id.rowTextView);
            txtName.setTypeface(null, Typeface.BOLD);
            txtName.setText(headerTitle.getText());

                    /*txtName.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                              //  txtName.setBackgroundColor(context.getResources().getColor(R.color.Yellow));

                        }
                    });*/


            TextView txtTime = (TextView) convertView
                    .findViewById(R.id.rowTimeView);
            txtTime.setTypeface(null, Typeface.BOLD);
            txtTime.setText(headerTitle.getTime());

        }
        catch(Exception ex){
            Log.d("expandingGrp", ex.getMessage());
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}