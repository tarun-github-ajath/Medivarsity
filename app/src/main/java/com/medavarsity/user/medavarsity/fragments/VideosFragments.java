package com.medavarsity.user.medavarsity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Adapters.AboutVideoAdapter;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.Model.Videos;
import com.medavarsity.user.medavarsity.R;

import java.util.List;

public class VideosFragments extends Fragment {

    View root;
    RecyclerView recyclerView;
    AboutVideoAdapter aboutVideoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_vedios, container, false);

        initialize();
        getArgs();

        return root;

    }

    private void getArgs() {
        if (getArguments() != null) {
            TopicDetailModel topicDetailModel = (TopicDetailModel) getArguments().getSerializable("Topic_detail");
            System.out.println(topicDetailModel);
            if (topicDetailModel.getVideos() == null || topicDetailModel.getVideos().size() == 0) {
                no_video.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
            /*if (topicDetailModel.getVideos() != null && topicDetailModel.getVideos().size() > 0) */
            else {
                no_video.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                aboutVideoAdapter = new AboutVideoAdapter(getActivity(), topicDetailModel.getVideos());
                recyclerView.setAdapter(aboutVideoAdapter);
            }

        }

    }

    TextView no_video;

    private void initialize() {
        recyclerView = (RecyclerView) root.findViewById(R.id.recyle_topic_videos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        no_video = (TextView) root.findViewById(R.id.no_video);


    }
}
