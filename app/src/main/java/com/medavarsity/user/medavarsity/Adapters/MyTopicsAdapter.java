package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Model.MyTopicsModel;
import com.medavarsity.user.medavarsity.Model.ReviewModel;
import com.medavarsity.user.medavarsity.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyTopicsAdapter extends RecyclerView.Adapter<MyTopicsAdapter.MyTopicHolder> {


    Context context;
    List<MyTopicsModel> topicsModels;
    LayoutInflater inflater;

    public MyTopicsAdapter(Context context, List<MyTopicsModel> modelList) {
        this.context = context;
        this.topicsModels = modelList;
    }

    @NonNull
    @Override
    public MyTopicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.my_topics_list_item, null);
        return new MyTopicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTopicHolder myTopicHolder, int position) {
        myTopicHolder.textView_subName.setText(topicsModels.get(position).getSubName());
    }

    @Override
    public int getItemCount() {
        return topicsModels.size();
    }

    class MyTopicHolder extends RecyclerView.ViewHolder {
        TextView textView_subName;
        public MyTopicHolder(@NonNull View itemView) {
            super(itemView);
            textView_subName = itemView.findViewById(R.id.textView_myTopics);
        }
    }
}
