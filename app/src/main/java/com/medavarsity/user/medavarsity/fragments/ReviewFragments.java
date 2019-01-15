package com.medavarsity.user.medavarsity.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Adapters.ReviewsAdapter;
import com.medavarsity.user.medavarsity.Model.ReviewModel;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.AddReview;

import java.util.List;
import java.util.Objects;

@SuppressLint("ValidFragment")
public class ReviewFragments extends Fragment {
    View root;
    RecyclerView recyclerView;
    TextView no_review;
    TopicDetailModel topicDetailModel;
    Button giveReviewBtn;

    @SuppressLint("ValidFragment")
    public ReviewFragments(TopicDetailModel topicDetailModel) {
        this.topicDetailModel = topicDetailModel;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.frag_reviews, container, false);
        recyclerView = root.findViewById(R.id.review_recycler);
        no_review = root.findViewById(R.id.no_review);
        giveReviewBtn = root.findViewById(R.id.fragReviews_giveReviewBtn);

        giveReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AddReview.class);
                intent.putExtra("topicVideos",topicDetailModel.getPayloadTopics().getVideos());

                startActivity(intent);
            }
        });

        if (topicDetailModel.getPayloadTopics().getReviewModels().isEmpty()) showNoReviewModalUI(); else {showReviewsUI();}

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Objects.requireNonNull(recyclerView.getLayoutManager()).setMeasurementCacheEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);

        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getActivity(), topicDetailModel.getPayloadTopics().getReviewModels());
        recyclerView.setAdapter(reviewsAdapter);
        return root;

    }

    private void showNoReviewModalUI(){
        no_review.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void showReviewsUI(){
        no_review.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }
}
