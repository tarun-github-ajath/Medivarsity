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

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {
    Context context;
    private List<ReviewModel> reviewModels;
    private LayoutInflater inflater;

    public TestAdapter(Context context, List<ReviewModel> reviewModels) {
        this.context = context;
        this.reviewModels = reviewModels;
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.tests_list_item, null);
        return new TestHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder testHolder, int position) {
        testHolder.ratingBar.setRating(reviewModels.get(position).getRating());
        testHolder.textView_review.setText(reviewModels.get(position).getReview());
    }

    @Override
    public int getItemCount() {
        return reviewModels.size();
    }

    class TestHolder extends RecyclerView.ViewHolder {
        TextView textView_name, textView_subject, textView_review;
        RatingBar ratingBar;
        TestHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.reviewee_name);
            textView_subject = itemView.findViewById(R.id.reviewer_subject);
            textView_review = itemView.findViewById(R.id.reviewer_reviews);
            ratingBar = itemView.findViewById(R.id.review_rating);
        }
    }
}
