package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Model.ReviewModel;
import com.medavarsity.user.medavarsity.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {


    Context context;
    List<ReviewModel> reviewModels;
    LayoutInflater inflater;

    public ReviewsAdapter(Context context, List<ReviewModel> reviewModels) {
        this.context = context;
        this.reviewModels = reviewModels;
    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.review_item, null);
        return new ReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewHolder reviewHolder, int position) {

        reviewHolder.ratingBar.setRating(reviewModels.get(position).getRating());
        reviewHolder.textView_review.setText(reviewModels.get(position).getReview());
        reviewHolder.textView_name.setText(reviewModels.get(position).getReviwername());

    }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }

    class ReviewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView textView_name, textView_subject, textView_review;
        RatingBar ratingBar;

        public ReviewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.reviewer_image);
            textView_name = (TextView) itemView.findViewById(R.id.reviewee_name);
            textView_subject = (TextView) itemView.findViewById(R.id.reviewer_subject);
            textView_review = (TextView) itemView.findViewById(R.id.reviewer_reviews);
            ratingBar = (RatingBar) itemView.findViewById(R.id.review_rating);

        }
    }
}
