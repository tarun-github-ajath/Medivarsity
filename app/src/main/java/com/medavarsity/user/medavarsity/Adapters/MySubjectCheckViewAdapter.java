package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;
import java.util.List;

public class MySubjectCheckViewAdapter extends RecyclerView.Adapter<MySubjectCheckViewAdapter.ViewHolder> {

    private List<String> list;
    private LayoutInflater mInflater;


    public MySubjectCheckViewAdapter(Context context, List<String> list) {
        this.mInflater = LayoutInflater.from(context);
        this.list=list;

    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.subject_checkview_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       // holder.myCheckSubject.setChecked(checkedHolder[position]);
        holder.tvItem.setText(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  {
        CheckBox myCheckSubject;
        TextView tvItem;

        ViewHolder(View itemView) {
            super(itemView);
            myCheckSubject = itemView.findViewById(R.id.check);
            tvItem=itemView.findViewById(R.id.item);
            myCheckSubject.isChecked();

        }


    }




}
