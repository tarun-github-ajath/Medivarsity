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
import com.medavarsity.user.medavarsity.Model.dailyUpdates;

import java.util.ArrayList;

public class SubjectAdapter  extends RecyclerView.Adapter<SubjectAdapter.ViewHolder> {


    Context context;

    LayoutInflater mInflater;

    ArrayList<dailyUpdates> dailyUpdateModelArrayList;

    public SubjectAdapter(Context context, ArrayList<dailyUpdates> dailyUpdateModelArrayList) {
        this.context = context;
        this.dailyUpdateModelArrayList = dailyUpdateModelArrayList;
    }


    @NonNull
    @Override
    public SubjectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(com.medavarsity.user.medavarsity.R.layout.daily_update_item, viewGroup, false);
        return new SubjectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubjectAdapter.ViewHolder viewHolder, int position) {

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

         //   youTubePlayer = (YouTubePlayer) itemView.findViewById(com.medavarsity.user.medavarsity.R.id.youtube_view);
            ratingBar = (RatingBar) itemView.findViewById(com.medavarsity.user.medavarsity.R.id.ratings);
            subjectTextView = (TextView) itemView.findViewById(com.medavarsity.user.medavarsity.R.id.subject_name);
            hrtextView = (TextView) itemView.findViewById(com.medavarsity.user.medavarsity.R.id.hr_rate);

        }
    }

}
