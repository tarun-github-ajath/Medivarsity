package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeThumbnailView;
import com.medavarsity.user.medavarsity.Model.Videos;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.GiveTest;

import java.util.List;

public class AboutVideoAdapter extends RecyclerView.Adapter<AboutVideoAdapter.videoHolder> {

    Context context;
    List<Videos> videosList;
    LayoutInflater inflater;

    public AboutVideoAdapter(Context context, List<Videos> videosList) {
        this.context = context;
        this.videosList = videosList;
    }

    @NonNull
    @Override
    public videoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.videos_item_list, viewGroup, false);
        return new videoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull videoHolder videoHolder, int i) {

        videoHolder.give_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, GiveTest.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    class videoHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        ImageView playButton;
        YouTubeThumbnailView youtube_thumbnail;
        TextView give_test;

        public videoHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
            youtube_thumbnail = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
            give_test = (TextView) itemView.findViewById(R.id.give_test);
        }
    }
}
