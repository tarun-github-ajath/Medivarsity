package com.medavarsity.user.medavarsity.fragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.HomeModel;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class HomeScreen extends Fragment {
    Toolbar toolbar;
    TextView tvTitle;
    EditText searchOption;
    LoginStudentResponse studentResponse;
    FragmentActivity mContext;
    private YouTubePlayer YPlayer;


    ApiInterface apiInterface;
    SharedPreferences sharedPreferences;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (activity instanceof FragmentActivity) {
            mContext = (FragmentActivity) activity;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_home, container, false);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.textview_toolbar);
        tvTitle.setVisibility(View.INVISIBLE);
        searchOption = (EditText) toolbar.findViewById(R.id.search_option);
        searchOption.setVisibility(View.VISIBLE);

        initialize();
        getExtras();

        if (studentResponse != null) {
            getDashboardData(studentResponse.getAuth_token());
        }
        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.youtube_fragment, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(Config.DEVELOPER_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    YPlayer = youTubePlayer;
                    YPlayer.setFullscreen(false);
                    // YPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                    YPlayer.cueVideo("2zNSgSzhBfM");
                    YPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                   /* YPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                    YPlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                        @Override
                        public void onFullscreen(boolean b) {

                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + Config.YOUTUBE_VIDEO_CODE));
                            intent.putExtra("VIDEO_ID", Config.YOUTUBE_VIDEO_CODE);
                            intent.putExtra("force_fullscreen", true);
                            startActivity(intent);
                        }
                    });*/
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });


        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity();

    }


    private void initialize() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sharedPreferences = this.getActivity().getSharedPreferences(ConstantVariabls.SHARED_FILE, MODE_PRIVATE);

    }

    Intent intent;

    private void getExtras() {
        intent = this.getActivity().getIntent();
        studentResponse = (LoginStudentResponse) intent.getSerializableExtra("student_info");
        Log.e("studentResponse", ">>>>" + studentResponse);
        readFromPref();
    }

    private void readFromPref() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantVariabls.LOGIN_STUDENT_OBJECT, "");
        studentResponse = gson.fromJson(json, LoginStudentResponse.class);

    }

    private void getDashboardData(String authToken) {
        if (authToken != null && !authToken.equalsIgnoreCase("")) {
            Call<HomeModel> homeModelCall = apiInterface.getHomeData(authToken);
            homeModelCall.enqueue(new Callback<HomeModel>() {
                @Override
                public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                    System.out.println(response);
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
}


