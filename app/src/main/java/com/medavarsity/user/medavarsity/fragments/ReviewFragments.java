package com.medavarsity.user.medavarsity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Adapters.ReviewsAdapter;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.ReviewModel;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.R;

import java.util.List;

public class ReviewFragments extends Fragment {
    View root;
    RecyclerView recyclerView;
    TextView no_review;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_reviews, container, false);
        recyclerView = (RecyclerView) root.findViewById(R.id.review_recycler);
        no_review = (TextView) root.findViewById(R.id.no_review);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.getLayoutManager().setMeasurementCacheEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);

        if (getArguments() != null) {
            TopicDetailModel topicDetailModel = (TopicDetailModel) getArguments().getSerializable(ConstantVariabls.SELCTED_TOPIC_DETAIL);

            if (topicDetailModel.getPayloadTopics().get(2).getReviewModels() == null || topicDetailModel.getPayloadTopics().get(2).getReviewModels().size() == 0) {
                no_review.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            } else {
                no_review.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getActivity(), topicDetailModel.getPayloadTopics().get(2).getReviewModels());
                recyclerView.setAdapter(reviewsAdapter);
            }

        }
        return root;

    }
}
