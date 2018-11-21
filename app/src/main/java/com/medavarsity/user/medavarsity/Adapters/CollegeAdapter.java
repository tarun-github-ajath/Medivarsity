package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Model.CollegeModel;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;

public class CollegeAdapter extends BaseAdapter {

    Context context;
    ArrayList<CollegeModel> collegeModelArrayList;
    LayoutInflater inflater;

    public CollegeAdapter(Context context, ArrayList<CollegeModel> collegeModelArrayList) {
        this.context = context;
        this.collegeModelArrayList = collegeModelArrayList;
    }

    @Override
    public int getCount() {
        return collegeModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();

            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.college_list_item, null);

            viewHolder.textView = (TextView) convertView.findViewById(R.id.collegesName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(collegeModelArrayList.get(position).getCollege_name());

        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
