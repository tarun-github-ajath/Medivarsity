package com.medavarsity.user.medavarsity.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.medavarsity.user.medavarsity.Constants.CommonMethods;
import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Model.HomeModel;
import com.medavarsity.user.medavarsity.Model.Subjects;
import com.medavarsity.user.medavarsity.Model.dailyUpdates;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;
import java.util.List;

public class DailyUpdateAdapter extends RecyclerView.Adapter<DailyUpdateAdapter.ViewHolder> {


    YouTubePlayer YPlayer;
    Context context;

    LayoutInflater mInflater;

    List<dailyUpdates> dailyUpdateModelArrayList;
    String developerKey;
    FragmentActivity fragmentActivity;

    List<Subjects> subjectsList;
    String from;

    public DailyUpdateAdapter(/*FragmentActivity fragmentActivity,*/ Context context, List<dailyUpdates> dailyUpdateModelArrayList, List<Subjects> subjectsList,
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

            viewHolder.youtube_thumbnail.initialize(developerKey, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    String video_id = CommonMethods.extractVideoId(dailyUpdateModelArrayList.get(position).getUrl());

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
                    String video_id = CommonMethods.extractVideoId(dailyUpdateModelArrayList.get(position).getUrl());
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Config.DEVELOPER_KEY, video_id);
                    context.startActivity(intent);
                }
            });

        } else {
            viewHolder.subjectTextView.setText(subjectsList.get(position).getSubjectname());

            viewHolder.youtube_thumbnail.initialize(developerKey, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    String video_id = CommonMethods.extractVideoId(subjectsList.get(position).getVideos().get(position).getVideo_url());

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
                    String video_id = CommonMethods.extractVideoId(subjectsList.get(position).getVideos().get(position).getVideo_url());
                    Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) context, Config.DEVELOPER_KEY, video_id);
                    context.startActivity(intent);
                }
            });
        }


       /* viewHolder.youTubePlayerFragment.initialize(Config.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(false);
                    // YPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    YPlayer.cueVideo(dailyUpdateModelArrayList.get(position).getUrl());
                    YPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    YPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                    YPlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean b) {

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + Config.YOUTUBE_VIDEO_CODE));
                            intent.putExtra("VIDEO_ID", Config.YOUTUBE_VIDEO_CODE);
                            intent.putExtra("force_fullscreen", true);
                            *//*getstartActivity(intent);*//*
                        }
                    });
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });
*/
        /*viewHolder.youTubePlayerView.initialize(developerKey, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    String videoCode = CommonMethods.extractVideoId(dailyUpdateModelArrayList.get(position).getUrl());
                    youTubePlayer.loadVideo(videoCode);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });*/

        /*final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                youTubeThumbnailView.setVisibility(View.VISIBLE);
                viewHolder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };*/

    }

    @Override
    public int getItemCount() {
        return dailyUpdateModelArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        // RatingBar ratingBar;
        TextView subjectTextView/*, hrtextView*/;
//        YouTubePlayerSupportFragment youTubePlayerFragment;

        //        YouTubePlayerView youTubePlayerView;
        RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        ImageView playButton;
        YouTubeThumbnailView youtube_thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            /*youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
            FragmentTransaction transaction = ((FragmentActivity) fragmentActivity).getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.daily_youtube_fragment, youTubePlayerFragment).commit();
*/
            //  youTubePlayerView = (YouTubePlayerView) itemView.findViewById(R.id.dailyyoutube_player);
            // youTubePlayerView.initialize(Config.DEVELOPER_KEY, this);
            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relative_yotube);
            youtube_thumbnail = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
            playButton = (ImageView) itemView.findViewById(R.id.btnYoutube_player);
            subjectTextView = (TextView) itemView.findViewById(R.id.subject_name);
        }
    }
}


