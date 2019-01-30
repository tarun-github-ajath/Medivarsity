package com.medavarsity.user.medavarsity.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.medavarsity.user.medavarsity.Adapters.ReviewsAdapter;
import com.medavarsity.user.medavarsity.Adapters.TestResultAdapter;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Model.TestResultResponse;
import com.medavarsity.user.medavarsity.Model.TestResult_GiveTestModalClone;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestResult extends AppCompatActivity {

    private ApiInterface apiInterface;

    @BindView(R.id.wrongAnsTV)
    TextView wrongAnsTV;

    @BindView(R.id.correctAnsTV)
    TextView correctAnsTV;

    @BindView(R.id.resultView)
    RelativeLayout relativeLayout_resultView;

    @BindView(R.id.scoreTV)
    TextView textView_scoreTV;

    @BindView(R.id.recycler_testResults)
    RecyclerView recyclerView;

    @BindView(R.id.progressBar_testResult)
    ProgressBar progressBar;


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_results);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar_testResult);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Test Results");

        ArrayList<JsonArray> arrayList = GiveTest.question_answersArrayList;

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        int testId = GiveTest.testId;

        ArrayList<JsonArray> arrays = new ArrayList<>();

        JsonArray jsonArray = new JsonArray();
        JsonArray jsonArray1 = new JsonArray();
        JsonArray jsonArray2 = new JsonArray();


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("question_id",54);
        jsonObject.addProperty("option_id",83);

        jsonArray.add(jsonObject);

        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("question_id",49);
        jsonObject1.addProperty("option_id",10);

        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("question_id",1);
        jsonObject2.addProperty("option_id",10);

        jsonArray1.add(jsonObject1);
        jsonArray2.add(jsonObject2);

        arrays.add(jsonArray);
        arrays.add(jsonArray1);
        arrays.add(jsonArray2);

        getResult("5be9630c64d76",56,arrays);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Objects.requireNonNull(recyclerView.getLayoutManager()).setMeasurementCacheEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);


    }

    private void getResult(String authToken,int testId, ArrayList<JsonArray> jsonArray) {
        progressBar.setVisibility(View.VISIBLE);
        Call<TestResultResponse> call = apiInterface.getTestResults(authToken, testId,jsonArray);
        call.enqueue(new Callback<TestResultResponse>() {
            @Override
            public void onResponse(@NonNull Call<TestResultResponse> call, @NonNull Response<TestResultResponse> response) {
                assert response.body() != null;
                if(response.body().getStatus() == 1){
                    progressBar.setVisibility(View.GONE);

                    int wrongAnswers = response.body().getPayload_testResults().getPayload_testResults_results().getIncorrectAnswers();
                    int correctAns = response.body().getPayload_testResults().getPayload_testResults_results().getCorrectAns();
                    int score = response.body().getPayload_testResults().getPayload_testResults_results().getCorrectAns();

                    wrongAnsTV.setText(wrongAnswers+" Wrong answer"+ (wrongAnswers>1?"s":""));
                    correctAnsTV.setText(correctAns+" Correct answer"+(correctAns>1?"s":""));
                    textView_scoreTV.setText("Score: "+score);

                    TestResultAdapter testResultAdapter= new TestResultAdapter(TestResult.this, response.body().getPayload_testResults().getPayload_testResults_questions() );
                    recyclerView.setAdapter(testResultAdapter);
                } else {
                    relativeLayout_resultView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TestResultResponse> call, @NonNull Throwable t) {
            }
        });
    }
}