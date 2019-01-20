package com.medavarsity.user.medavarsity.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Adapters.AboutVideoAdapter;
import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Model.Videos;
import com.medavarsity.user.medavarsity.R;

import java.util.List;

@SuppressLint("ValidFragment")
public class VideosFragments extends Fragment {
    TextView no_video;
    View root;
    RecyclerView recyclerView;
    List<Videos> videosList;

    @SuppressLint("ValidFragment")
    public VideosFragments(List<Videos> videosList) {
        this.videosList = videosList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_vedios, container, false);
        no_video = root.findViewById(R.id.no_video);
        recyclerView = root.findViewById(R.id.recyle_topic_videos);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);


        if (videosList.size() > 0){
            if(!videosList.get(0).getVideo_url().equals("")){
                AboutVideoAdapter aboutVideoAdapter = new AboutVideoAdapter(getActivity(), videosList,Config.DEVELOPER_KEY);
                recyclerView.setAdapter(aboutVideoAdapter);
                showVideosUI();
            } else {
                showNoVideosUI();
            }

        } else {
            showNoVideosUI();
        }


        return root;
    }

    private void showNoVideosUI(){
        no_video.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showVideosUI(){
        no_video.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }


}
