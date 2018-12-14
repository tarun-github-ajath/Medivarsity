package com.medavarsity.user.medavarsity.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Adapters.Pager;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.PayloadTopics;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;

import retrofit2.Callback;
import retrofit2.Response;

public class TopicDetails extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {

    TabLayout tabLayout;
    TextView textView;
    ImageView navigation_back;
    CommonMethods mCommonMethods;
    Intent intent;
    ViewPager viewPager;
    SharedPreferences sharedPreferences;
    ApiInterface apiInterface;
    TopicDetailModel topicDetailModel;
    String selectedSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mCommonMethods = new CommonMethods(this);
        textView = (TextView) findViewById(R.id.selected_subject);
        navigation_back = (ImageView) findViewById(R.id.navigate_back);
        sharedPreferences = getSharedPreferences(ConstantVariabls.SHARED_FILE, Context.MODE_PRIVATE);

        LoginStudentResponse loginStudentResponse = CommonMethods.readLoginUser(sharedPreferences);


        intent = getIntent();
        selectedSub = intent.getStringExtra(ConstantVariabls.SELECTED_SUBJECT_NAME).equalsIgnoreCase("") ? "" :
                intent.getStringExtra(ConstantVariabls.SELECTED_SUBJECT_NAME);
        int sub_id = intent.getIntExtra(ConstantVariabls.SELECTED_SUB_ID, 0);

        textView.setText("Concept of " + " " + selectedSub);


        if (mCommonMethods.isNetworkAvailable(this)) {
            try {
                mCommonMethods.showCommonDialog(this, "Fetching data...");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                getSubjectDeatils(loginStudentResponse.getAuth_token(), sub_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        navigation_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicDetails.this, DashBoard.class);
                startActivity(intent);
                finish();
            }
        });


        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);


        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    private void getSubjectDeatils(String auth_token, final int subject_id) {
        if (auth_token != null && !auth_token.equalsIgnoreCase("")) {
            retrofit2.Call<TopicDetailModel> homeModelCall = apiInterface.getTopicDetails(auth_token, subject_id);
            homeModelCall.enqueue(new Callback<TopicDetailModel>() {
                @Override
                public void onResponse(retrofit2.Call<TopicDetailModel> call, Response<TopicDetailModel> response) {

                    System.out.println("Topic re"+response);
                    topicDetailModel = response.body();

                    mCommonMethods.cancelDialog();

                    Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount(), topicDetailModel, selectedSub);

                    //Adding adapter to pager
                    viewPager.setAdapter(adapter);
                }

                @Override
                public void onFailure(retrofit2.Call<TopicDetailModel> call, Throwable t) {

                    mCommonMethods.cancelDialog();
                }
            });
        }
    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {


    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
