package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Model.StateModel;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;

public class StateAdapter extends BaseAdapter {

    Context context;
    private ArrayList<StateModel> stateModelArrayList;
    private LayoutInflater inflater;

    public StateAdapter(Context context, ArrayList<StateModel> stateModelArrayList) {
        this.context = context;
        this.stateModelArrayList = stateModelArrayList;
    }

    @Override
    public int getCount() {
        return stateModelArrayList.size();
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
            convertView = inflater.inflate(R.layout.register_spinner_itemview, null);

            viewHolder.textView = convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(stateModelArrayList.get(position).getStateName());

        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
