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
import com.medavarsity.user.medavarsity.Model.Videos;
import com.medavarsity.user.medavarsity.R;

import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<SubjectsAdapter.ViewHolder> {

    Context context;
    LayoutInflater mInflater;
    String developerKey;
    FragmentActivity fragmentActivity;
    List<Videos> subjectsVideos;

    public SubjectsAdapter(Context context, List<Videos> subjectsVideos,
                           String developerKey) {
        this.fragmentActivity = fragmentActivity;
        this.context = context;
        this.developerKey = developerKey;
        this.subjectsVideos = subjectsVideos;
    }


    @NonNull
    @Override
    public SubjectsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout./*daily_update_item*/youtube_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SubjectsAdapter.ViewHolder viewHolder, final int position) {

        viewHolder.subjectTextView.setText(subjectsVideos.get(position).getVideo_title());


       /* viewHolder.subjectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TopicDetails.class);
                context.startActivity(intent);
            }
        });*/
        viewHolder.youtube_thumbnail.initialize(developerKey, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                String video_id = CommonMethods.extractVideoId(subjectsVideos.get(position).getVideo_url());

                youTubeThumbnailLoader.setVideo(video_id);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailLoader.release();
                        youTubeThumbnailView.setVisibility(View.VISIBLE);
                        viewHolder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        viewHolder.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String video_id = CommonMethods.extractVideoId(subjectsVideos.get(position).getVideo_url());
                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Config.DEVELOPER_KEY, video_id);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return subjectsVideos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView subjectTextView;
        RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        ImageView playButton;
        YouTubeThumbnailView youtube_thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relative_yotube);
            youtube_thumbnail = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
            playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
            subjectTextView = (TextView) itemView.findViewById(R.id.subject_name);
        }
    }
}
