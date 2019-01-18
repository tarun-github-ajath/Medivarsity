package com.medavarsity.user.medavarsity.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Model.Subjects;
import com.medavarsity.user.medavarsity.Model.dailyUpdates;
import com.medavarsity.user.medavarsity.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DailyUpdateAdapter extends RecyclerView.Adapter<DailyUpdateAdapter.ViewHolder> {

    Context context;
    LayoutInflater mInflater;
    List<dailyUpdates> dailyUpdateModelArrayList;
    String developerKey;
    FragmentActivity fragmentActivity;
    List<Subjects> subjectsList;
    String from;

    public DailyUpdateAdapter(Context context, List<dailyUpdates> dailyUpdateModelArrayList, List<Subjects> subjectsList,
                                                                     String developerKey, String from) {
        this.fragmentActivity = fragmentActivity;
        this.context = context;
        this.dailyUpdateModelArrayList = dailyUpdateModelArrayList;
        this.developerKey = developerKey;
        this.subjectsList = subjectsList;
        this.from = from;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout./*daily_update_item*/youtube_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {

        if (from.equalsIgnoreCase("daily")) {
            viewHolder.subjectTextView.setText(dailyUpdateModelArrayList.get(position).getTitle());

            String url = getYoutubeId(dailyUpdateModelArrayList.get(position).getUrl());
            Picasso.with(context).load(url).into(viewHolder.youtube_thumbnail);

            viewHolder.youtube_thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String video_id = CommonMethods.extractVideoId(dailyUpdateModelArrayList.get(position).getUrl());
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Config.DEVELOPER_KEY, video_id);
                    context.startActivity(intent);
                }
            });

        } else {
            viewHolder.subjectTextView.setText(subjectsList.get(position).getSubjectname());

            String url = getYoutubeId(subjectsList.get(position).getVideos().get(position).getVideo_url());
            Picasso.with(context).load(url).into(viewHolder.youtube_thumbnail);

            viewHolder.relativeLayoutOverYouTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String video_id = CommonMethods.extractVideoId(subjectsList.get(position).getVideos().get(position).getVideo_url());
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Config.DEVELOPER_KEY, video_id);
                    context.startActivity(intent);
                }
            });
        }


          }

    @Override
    public int getItemCount() {
        return dailyUpdateModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView subjectTextView;
        RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        ImageView playButton;
        ImageView youtube_thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relative_yotube);
            youtube_thumbnail = itemView.findViewById(R.id.youtube_thumbnail);
            playButton = itemView.findViewById(R.id.btnYoutube_player);
            subjectTextView = itemView.findViewById(R.id.subject_name);
        }
    }

    private String getYoutubeId(String youtubeVideoUrl){
        String videoId = youtubeVideoUrl.substring(youtubeVideoUrl.lastIndexOf("?v="));
        String filter = videoId.replace("?v=","");
        return "https://img.youtube.com/vi/"+filter+"/0.jpg";
    }

}


