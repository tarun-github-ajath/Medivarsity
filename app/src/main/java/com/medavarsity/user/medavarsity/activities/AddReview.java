package com.medavarsity.user.medavarsity.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddReview extends AppCompatActivity {

    ImageView userProfile;

    @BindView(R.id.fragReviews_giveReviewBtn)
    Button addReviewBtn;
    @BindView(R.id.ratingBar_addReview)
    RatingBar ratingBarAddReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar_addReview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Submit Feedback");

        userProfile = findViewById(R.id.userProfileImage_addReview);

        Picasso.with(this).load(GlobalProps.getInstance().userProfile).into(userProfile);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @OnClick(R.id.fragReviews_giveReviewBtn)
    public void onViewClicked() {
        String rating = String.valueOf(ratingBarAddReview.getRating());
        Toast.makeText(getApplicationContext(),rating,Toast.LENGTH_LONG).show();
    }
}
