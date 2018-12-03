package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubePlayer;
import com.medavarsity.user.medavarsity.Model.HomeModel;
import com.medavarsity.user.medavarsity.Model.dailyUpdates;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;

public class DailyUpdateAdapter extends RecyclerView.Adapter<DailyUpdateAdapter.ViewHolder> {


    Context context;

    LayoutInflater mInflater;

    ArrayList<dailyUpdates> dailyUpdateModelArrayList;

    public DailyUpdateAdapter(Context context, ArrayList<dailyUpdates> dailyUpdateModelArrayList) {
        this.context = context;
        this.dailyUpdateModelArrayList = dailyUpdateModelArrayList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.daily_update_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

        viewHolder.subjectTextView.setText(dailyUpdateModelArrayList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return dailyUpdateModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        YouTubePlayer youTubePlayer;
        RatingBar ratingBar;
        TextView subjectTextView, hrtextView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            youTubePlayer = (YouTubePlayer) itemView.findViewById(R.id.youtube_view);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratings);
            subjectTextView = (TextView) itemView.findViewById(R.id.subject_name);
            hrtextView = (TextView) itemView.findViewById(R.id.hr_rate);

        }
    }

}


