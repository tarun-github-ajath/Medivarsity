package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.PayloadHome;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.TopicDetails;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    PayloadHome payloadHome;
    String from;

    public HomeAdapter(Context context, PayloadHome payloadHome, String from) {
        this.from = from;
        this.context = context;
        this.payloadHome = payloadHome;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout./*daily_update_item*/text_recycle, viewGroup, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder homeViewHolder, final int position) {


        if (payloadHome.getSubjects() != null && payloadHome.getSubjects().size() > 0) {
            /*homeViewHolder.subject_name.setText(payloadHome.getSubjects().get(position).getSubjectname());*/


            if (payloadHome.getSubjects().get(position).getVideos() != null && payloadHome.getSubjects().get(position).getVideos().size() > 0) {
                homeViewHolder.subject_name.setText(payloadHome.getSubjects().get(position).getSubjectname());
                homeViewHolder.subject_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, TopicDetails.class);
                        intent.putExtra(ConstantVariabls.SELECTED_SUBJECT_NAME, payloadHome.getSubjects().get(position).getSubjectname());
                        intent.putExtra(ConstantVariabls.SELECTED_SUB_ID, payloadHome.getSubjects().get(position).getSubjectId());
                        context.startActivity(intent);
                    }
                });
                homeViewHolder.parent_layout.setVisibility(View.VISIBLE);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                homeViewHolder.recyclerView.setLayoutManager(layoutManager);
                homeViewHolder.recyclerView.setHasFixedSize(true);


                homeViewHolder.recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
                homeViewHolder.recyclerView.setNestedScrollingEnabled(false);
                SubjectsAdapter subjectsAdapter = new SubjectsAdapter(context, payloadHome.getSubjects().get(position).getVideos(), Config.DEVELOPER_KEY);
                homeViewHolder.recyclerView.setAdapter(subjectsAdapter);
            } else {
                homeViewHolder.parent_layout.setVisibility(View.INVISIBLE);
            }

        }


    }

    @Override
    public int getItemCount() {
        return payloadHome.getSubjects().size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {
        RecyclerView recyclerView;
        TextView subject_name, subscription;

        LinearLayout parent_layout;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            parent_layout = (LinearLayout) itemView.findViewById(R.id.layout_recycl);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.test_recycl);
            subject_name = (TextView) itemView.findViewById(R.id.dailyUpdate);
            subscription = (TextView) itemView.findViewById(R.id.subscribe);

        }
    }
}
