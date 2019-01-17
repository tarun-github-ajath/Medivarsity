package com.medavarsity.user.medavarsity.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.medavarsity.user.medavarsity.Adapters.QuestionFragmentPagerAdapter;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Model.QuestionsResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.fragments.QuestionFrag;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiveTest extends AppCompatActivity {

    Toolbar toolbar;
    ImageView navigation_back;

    @BindView(R.id.pager_test)
    ViewPager viewPager;
    ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_test);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        navigation_back = findViewById(R.id.navigate_back);

        navigation_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiveTest.super.onBackPressed();
            }
        });

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int testId = bundle.getInt("testId");
        getQuestions(GlobalProps.getInstance().authToken,testId);


    }

    private void getQuestions(String authToken,int testId){
        Call<QuestionsResponse> call = apiInterface.getQuestions(authToken,testId);
        call.enqueue(new Callback<QuestionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuestionsResponse> call, @NonNull Response<QuestionsResponse> response) {
                assert response.body() != null;
                Log.i("data",response.body().getMessage());

                FragmentManager fm = getSupportFragmentManager();
                QuestionFragmentPagerAdapter pagerAdapter = new QuestionFragmentPagerAdapter(fm);
//                Log.i("question", String.valueOf(response.body().getQuestionsModel().get(0).getQuestion()));
                for(int i=0;i<response.body().getQuestionsModel().size();i++){
                    pagerAdapter.addFragment(new QuestionFrag(getApplicationContext(),i,response.body().getQuestionsModel().get(i).getQuestion(),response.body().getQuestionsModel().get(i).getOptionsModel()), "Question "+i);
                }
                viewPager.setAdapter(pagerAdapter);
            }

            @Override
            public void onFailure(@NonNull Call<QuestionsResponse> call, @NonNull Throwable t) {
            }
        });
    }
}
