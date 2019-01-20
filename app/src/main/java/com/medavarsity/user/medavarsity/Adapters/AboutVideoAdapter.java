package com.medavarsity.user.medavarsity.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.Videos;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.YoutubeActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AboutVideoAdapter extends RecyclerView.Adapter<AboutVideoAdapter.videoHolder> {

    Context context;
    List<Videos> videosList;
    LayoutInflater inflater;
    String developerKey;

    public AboutVideoAdapter(Context context, List<Videos> videosList,String developerKey) {
        this.context = context;
        this.videosList = videosList;
        this.developerKey = developerKey;
    }

    @NonNull
    @Override
    public videoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.videos_item_list, viewGroup, false);
        return new videoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final videoHolder videoHolder, final int i) {
        videoHolder.videoName.setText(videosList.get(i).getVideo_title());
        String urlThumb = getYoutubeId(i);

        if(!urlThumb.equals("")){

            Picasso.with(context).load(urlThumb).into(videoHolder.playButton);
        }

        videoHolder.relativeLayoutOverYouTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String video_id = CommonMethods.extractVideoId(videosList.get(i).getVideo_url());
                Intent i = new Intent((Activity) context, YoutubeActivity.class);
                i.putExtra("videoId",video_id);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videosList.size();
    }

    class videoHolder extends RecyclerView.ViewHolder {

        LinearLayout relativeLayoutOverYouTubeThumbnailView;
        ImageView playButton;
        YouTubeThumbnailView youtube_thumbnail;
        TextView videoName;

        public videoHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayoutOverYouTubeThumbnailView = itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            playButton = itemView.findViewById(R.id.youtube_image);
            youtube_thumbnail = itemView.findViewById(R.id.youtube_thumbnail);
            videoName = itemView.findViewById(R.id.topic_details_videos_videoTitle);

        }
    }

    private String getYoutubeId(int pos){
        String youtubeVideoUrl = videosList.get(pos).getVideo_url();
        if(youtubeVideoUrl.length() > 0){

            String videoId = youtubeVideoUrl.substring(youtubeVideoUrl.lastIndexOf("?v="));
            String filter = videoId.replace("?v=","");
            return "https://img.youtube.com/vi/"+filter+"/0.jpg";
        } else return "";
    }
}
