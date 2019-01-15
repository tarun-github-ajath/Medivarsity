package com.medavarsity.user.medavarsity.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.R;

@SuppressLint("ValidFragment")
public class GiveTestFragment extends Fragment {
    View root;
    RecyclerView recyclerView;
    TextView no_review;
    TopicDetailModel topicDetailModel;
    Button giveReviewBtn;

    @SuppressLint("ValidFragment")
    public GiveTestFragment(TopicDetailModel topicDetailModel) {
        this.topicDetailModel = topicDetailModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.frag_test, container, false);

        return root;

    }


}