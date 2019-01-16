package com.medavarsity.user.medavarsity.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.medavarsity.user.medavarsity.Adapters.Spinner_AddReview_Adapter;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.LectureModal;
import com.medavarsity.user.medavarsity.Model.ReviewModel;
import com.medavarsity.user.medavarsity.Model.ReviewResponse;
import com.medavarsity.user.medavarsity.Model.Videos;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddReview extends AppCompatActivity   {

    ApiInterface apiInterface;
    ImageView userProfile;

    @BindView(R.id.fragReviews_giveReviewBtn)
    Button addReviewBtn;
    @BindView(R.id.ratingBar_addReview)
    RatingBar ratingBarAddReview;

    @BindView(R.id.spinner_addReview)
    Spinner spinner;

    @BindView(R.id.editText_review)
    EditText editText_review;

    ArrayList<Videos> arrayList;
   Integer videoId;
    Integer topicId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar_addReview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Submit Feedback");
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        userProfile = findViewById(R.id.userProfileImage_addReview);
        Log.i("userPro",GlobalProps.getInstance().userProfile);
        if(!GlobalProps.getInstance().userProfile.isEmpty()){

            Picasso.with(this).load(GlobalProps.getInstance().userProfile).into(userProfile);
        } else {
            Picasso.with(this).load(R.drawable.ic_user).into(userProfile);
        }

        assert getIntent().getSerializableExtra("topicVideos") != null;
        arrayList = (ArrayList<Videos>) getIntent().getSerializableExtra("topicVideos");
        Videos videos = new Videos();
        videos.setVideo_title("Select Lecture");
        arrayList.add(0,videos);

        Spinner_AddReview_Adapter spinner_addReview_adapter = new Spinner_AddReview_Adapter(this, R.layout.add_review_spinner_item, arrayList);
        spinner.setAdapter(spinner_addReview_adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                // If user change the default selection
                // First item is disable and it is used for hint
                if(position > 0){
                    // Notify the selected item text
                    videoId = arrayList.get(position).getId();
                    topicId = arrayList.get(position).getSubject_id();
                } else {
                    videoId = null;
                    topicId = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ratingBarAddReview.setRating(5);
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
        postReview();
    }

    private void postReview(){
        float rating = ratingBarAddReview.getRating();
        String reviewText = editText_review.getText().toString();



         if (reviewText.length() > 0 && rating > 0 && topicId != null && videoId != null) {
            Call<ReviewResponse> call = apiInterface.SubmitFeedback("5bff93976a7d2",videoId,topicId,reviewText,rating);
            call.enqueue(new Callback<ReviewResponse>() {
                @Override
                public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                    assert response.body() != null;
                    boolean isError =  response.body().isError();

                    if(!isError){
                        CommonMethods.showAlert(AddReview.this,"Thank you for your feedback");
                        endActivity();
                    } else {
                        Toast.makeText(getApplicationContext(),"Some error occured",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReviewResponse> call, Throwable t) {

                }
            });
        } else {
             CommonMethods.showAlert(this,"Please fill all fields");
         }





    }

    private void endActivity(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                finish();
            }
        }, 2000);
    }
}
