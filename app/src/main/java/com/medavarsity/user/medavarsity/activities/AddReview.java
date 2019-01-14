package com.medavarsity.user.medavarsity.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.medavarsity.user.medavarsity.Constants.ConstantVariables;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.R;
import com.squareup.picasso.Picasso;

public class AddReview extends AppCompatActivity {

    ImageView userProfile;
    RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_review);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_addReview);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Submit Feedback");

        userProfile = findViewById(R.id.userProfileImage_addReview);
        ratingBar = findViewById(R.id.ratingBar_addReview);

        Log.i("imageUrl",GlobalProps.getInstance().userProfile);
        Picasso.with(this).load(GlobalProps.getInstance().userProfile).into(userProfile);

    }

    public void addListenerOnButtonClick() {
        ratingBar = findViewById(R.id.ratingBar_addReview);
//        button = (Button) findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                //Getting the rating and displaying it on the toast
//                String rating = String.valueOf(ratingbar.getRating());
//                Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();
//            }
//
//        });


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




}
