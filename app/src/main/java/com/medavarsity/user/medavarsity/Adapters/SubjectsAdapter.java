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
import com.medavarsity.user.medavarsity.activities.YoutubeActivity;
import com.squareup.picasso.Picasso;

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
        String url = getYoutubeId(subjectsVideos.get(position).getVideo_url());
        Picasso.with(context).load(url).into(viewHolder.youtube_thumbnail);

        viewHolder.youtube_thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String video_id = CommonMethods.extractVideoId(subjectsVideos.get(position).getVideo_url());
                Intent i = new Intent((Activity) context,YoutubeActivity.class);
                i.putExtra("videoId",video_id);
                context.startActivity(i);


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
        ImageView youtube_thumbnail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
            youtube_thumbnail = itemView.findViewById(R.id.youtube_thumbnail);
            playButton = itemView.findViewById(R.id.btnYoutube_player);
            subjectTextView = (TextView) itemView.findViewById(R.id.subject_name);
        }
    }

    private String getYoutubeId(String youtubeVideoUrl){
        String videoId = youtubeVideoUrl.substring(youtubeVideoUrl.lastIndexOf("?v="));
        String filter = videoId.replace("?v=","");
        return "https://img.youtube.com/vi/"+filter+"/0.jpg";
    }
}
