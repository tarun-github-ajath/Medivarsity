package com.medavarsity.user.medavarsity.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Adapters.Pager;
import com.medavarsity.user.medavarsity.Constants.ConstantVariables;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.fragments.AboutFragments;
import com.medavarsity.user.medavarsity.fragments.GiveTestFragment;
import com.medavarsity.user.medavarsity.fragments.ReviewFragments;
import com.medavarsity.user.medavarsity.fragments.VideosFragments;

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
    private int sub_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        tabLayout = findViewById(R.id.tabLayout);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar_topic_details);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.subject_name_topic_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = findViewById(R.id.viewpager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mCommonMethods = new CommonMethods(this);
        textView = findViewById(R.id.selected_subject_topic_details);
        navigation_back = findViewById(R.id.navigate_back);
        sharedPreferences = getSharedPreferences(ConstantVariables.SHARED_FILE, Context.MODE_PRIVATE);

        LoginStudentResponse loginStudentResponse = CommonMethods.readLoginUser(sharedPreferences);


        intent = getIntent();
        selectedSub = intent.getStringExtra(ConstantVariables.SELECTED_SUBJECT_NAME).equalsIgnoreCase("") ? "" : intent.getStringExtra(ConstantVariables.SELECTED_SUBJECT_NAME);
        if(intent.getIntExtra(ConstantVariables.SELECTED_SUB_ID,0) != 0){
            sub_id = intent.getIntExtra(ConstantVariables.SELECTED_SUB_ID, 0);
            textView.setText("Concept of " + " " + selectedSub);
        }



        if (mCommonMethods.isNetworkAvailable(this)) {
            try {
                mCommonMethods.showCommonDialog(this, "Fetching data...");
                getSubjectDetails(GlobalProps.getInstance().authToken, sub_id);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        tabLayout.setOnTabSelectedListener(this);
    }

    private void getSubjectDetails(String auth_token, final int subject_id) {
        Log.i("subjId", String.valueOf(subject_id));
        if (auth_token != null && !auth_token.equalsIgnoreCase("")) {
            retrofit2.Call<TopicDetailModel> homeModelCall = apiInterface.getTopicDetails(auth_token, subject_id);
            homeModelCall.enqueue(new Callback<TopicDetailModel>() {
                @Override
                public void onResponse(@NonNull retrofit2.Call<TopicDetailModel> call, @NonNull Response<TopicDetailModel> response) {

                    System.out.println("Topic re"+response);
                    topicDetailModel = response.body();
                    mCommonMethods.cancelDialog();
                    renderTabs();
                }

                @Override
                public void onFailure(@NonNull retrofit2.Call<TopicDetailModel> call, @NonNull Throwable t) {
                    t.printStackTrace();
                    mCommonMethods.cancelDialog();
                }
            });
        }
    }


    private void renderTabs(){
        Pager adapter = new Pager(getSupportFragmentManager());
        AboutFragments aboutFragments = new AboutFragments();
        Bundle bundle = new Bundle();
        bundle.putString("subject",selectedSub);
        bundle.putString("subject_desc",topicDetailModel.getPayloadTopics().getSubjectDetails().getSubject_desc());
        aboutFragments.setArguments(bundle);


        adapter.addFragment(new GiveTestFragment(topicDetailModel),"Tests");
        adapter.addFragment(aboutFragments,"About");

        adapter.addFragment(new VideosFragments(topicDetailModel.getPayloadTopics().getVideos()),"Videos");
        adapter.addFragment(new ReviewFragments(topicDetailModel),"Reviews");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
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

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {}


}
