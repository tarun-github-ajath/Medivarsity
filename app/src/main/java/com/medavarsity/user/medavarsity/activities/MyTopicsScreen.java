package com.medavarsity.user.medavarsity.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Adapters.MyTopicsAdapter;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Model.MyTopicResponse;
import com.medavarsity.user.medavarsity.Model.MyTopicsModel;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyTopicsScreen extends AppCompatActivity {
    ImageView navigate_back;
    TextView toolbar_textView;
    @BindView(R.id.my_topic_recycler)
    RecyclerView recyclerView;
    ApiInterface api;

    @BindView(R.id.notSubscribedTV)
    TextView notSubscribedTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_my_topics);
        initializeIds();
        api = ApiClient.getClient().create(ApiInterface.class);

        getMyTopics(GlobalProps.getInstance().authToken);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Objects.requireNonNull(recyclerView.getLayoutManager()).setMeasurementCacheEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);
        Log.i("token",GlobalProps.getInstance().authToken);
    }

    private void getMyTopics(String authToken) {
        Call<MyTopicResponse> call = api.getMyTopics(authToken);
        call.enqueue(new Callback<MyTopicResponse>() {
            @Override
            public void onResponse(@NonNull Call<MyTopicResponse> call, @NonNull Response<MyTopicResponse> response) {
               if(response.body() != null){

                   if(response.body().getStatus() == 0){
                        notSubscribedTv.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                   } else {
                       notSubscribedTv.setVisibility(View.GONE);
                       recyclerView.setVisibility(View.VISIBLE);
                       ArrayList<MyTopicsModel> myTopicsModels = response.body().getMyTopicsModels();
                       MyTopicsAdapter myTopicsAdapter = new MyTopicsAdapter(getApplicationContext(), myTopicsModels);
                       recyclerView.setAdapter(myTopicsAdapter);
                   }
               }
            }

            @Override
            public void onFailure(@NonNull Call<MyTopicResponse> call, @NonNull Throwable t) {
            }
        });
    }

    private void initializeIds() {
        navigate_back = findViewById(R.id.navigate_back);
        toolbar_textView = findViewById(R.id.toolbar_textView);
        toolbar_textView.setText("My Topics");
        recyclerView = findViewById(R.id.my_topic_recycler);
        navigate_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
