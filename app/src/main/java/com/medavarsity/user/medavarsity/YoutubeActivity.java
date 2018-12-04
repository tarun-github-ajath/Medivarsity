package com.medavarsity.user.medavarsity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Adapters.DailyUpdateAdapter;
import com.medavarsity.user.medavarsity.Constants.CommonMethods;
import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.HomeModel;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.PayloadHome;
import com.medavarsity.user.medavarsity.Model.dailyUpdates;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class YoutubeActivity extends YouTubeBaseActivity /*implements YouTubePlayer.OnInitializedListener*/ {


    YouTubePlayerView youTubePlayerView;

    SharedPreferences sharedPreferences;
    RecyclerView dailyUpdateRecycle;
    Intent intent;
    ApiInterface apiInterface;
    YouTubeThumbnailView youTubeThumbnailView;
    ImageView playBtn;
    RelativeLayout relativeLayoutOverYouTubeThumbnailView;

    RelativeLayout searchLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);
/*
        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(Config.DEVELOPER_KEY, this);*/
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        youTubeThumbnailView = (YouTubeThumbnailView) findViewById(R.id.youtube_player);
        playBtn = (ImageView) findViewById(R.id.btnYoutube_player);
        relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) findViewById(R.id.relative_yotube);

        searchLayout = (RelativeLayout) findViewById(R.id.search_layout);
        dailyUpdateRecycle = (RecyclerView) findViewById(R.id.daily_update_recycl);
        dailyUpdateRecycle.setHasFixedSize(true);
        sharedPreferences = this.getSharedPreferences(ConstantVariabls.SHARED_FILE, MODE_PRIVATE);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        dailyUpdateRecycle.setLayoutManager(layoutManager);


        dailyUpdateRecycle.getLayoutManager().setMeasurementCacheEnabled(false);
        getExtras();
        getDashboardData(studentResponse.getAuth_token());


        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {


                youTubeThumbnailView.setVisibility(View.VISIBLE);
                relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        youTubeThumbnailView.initialize(Config.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                /*String video_id = CommonMethods.extractVideoId(dailyUpdateModelArrayList.get(position).getUrl());*/

                youTubeThumbnailLoader.setVideo(Config.YOUTUBE_VIDEO_CODE);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = YouTubeStandalonePlayer.createVideoIntent(YoutubeActivity.this, Config.DEVELOPER_KEY, Config.YOUTUBE_VIDEO_CODE);
                startActivity(intent);
            }
        });


        searchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(YoutubeActivity.this, SearchScreen.class);
                startActivity(intent);
            }
        });
    }


    LoginStudentResponse studentResponse;

    private void getExtras() {
        intent = getIntent();
        studentResponse = (LoginStudentResponse) intent.getSerializableExtra("student_info");
        Log.e("studentResponse", ">>>>" + studentResponse);
        readFromPref();
    }

    private void readFromPref() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantVariabls.LOGIN_STUDENT_OBJECT, "");
        studentResponse = gson.fromJson(json, LoginStudentResponse.class);

    }


/*
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
       */
/* youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);
*//*

     */

    /**
     * Start buffering
     **//*

        if (!b) {
            // youTubePlayer.cueVideo(Config.YOUTUBE_VIDEO_CODE);
            String videoCode = CommonMethods.extractVideoId("https://www.youtube.com/watch?v=srH-2pQdKhg");
            youTubePlayer.loadVideo(videoCode);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
*/
    private void getDashboardData(String authToken) {
        if (authToken != null && !authToken.equalsIgnoreCase("")) {
            Call<HomeModel> homeModelCall = apiInterface.getHomeData(authToken);
            homeModelCall.enqueue(new Callback<HomeModel>() {
                @Override
                public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                    System.out.println(response);
                    PayloadHome payloadHome = response.body().getPayloadHome();
                    List<dailyUpdates> dailyUpdates = new ArrayList<>();
                    dailyUpdates = payloadHome.getDailyUpdates();

                    saveInPref(payloadHome);
                    setDailyUpdate(dailyUpdates);

                }

                @Override
                public void onFailure(Call<HomeModel> call, Throwable t) {

                }
            });
        }
    }


    private void fullscreen(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.putExtra("force_fullscreen", true);
        startActivity(intent);
    }

    private void setDailyUpdate(List<dailyUpdates> dailyUpdate) {

        DailyUpdateAdapter dailyUpdateAdapter = new DailyUpdateAdapter(this, dailyUpdate, Config.DEVELOPER_KEY);
        dailyUpdateRecycle.setAdapter(dailyUpdateAdapter);
    }


    private void saveInPref(PayloadHome homeModels) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(homeModels);
        prefsEditor.putString(ConstantVariabls.Home_Model, json);
        prefsEditor.commit();
    }
}
