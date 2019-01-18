package com.medavarsity.user.medavarsity.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.medavarsity.user.medavarsity.Adapters.QuestionFragmentPagerAdapter;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Interfaces.ScrollTestViewPager_Next;
import com.medavarsity.user.medavarsity.Model.QuestionsResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.fragments.QuestionFrag;
import com.medavarsity.user.medavarsity.fragments.QuestionsEndFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GiveTest extends AppCompatActivity implements ScrollTestViewPager_Next {

    Toolbar toolbar;
    ImageView navigation_back;

    @BindView(R.id.pager_test)
    ViewPager viewPager;
    ApiInterface apiInterface;

    @BindView(R.id.prev_questionFrag)
    Button button_prev;

    @BindView(R.id.next_questionFrag)
    Button button_next;

    @BindView(R.id.seeResult_questionFrag)
    Button button_seeResult;

    private QuestionFragmentPagerAdapter pagerAdapter;
    int questionsListSize = 0;
    int currentItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_test);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar_giveTest);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Test");
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        FragmentManager fm = getSupportFragmentManager();
        pagerAdapter = new QuestionFragmentPagerAdapter(fm);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        int testId = bundle.getInt("testId");
        getQuestions(GlobalProps.getInstance().authToken, testId);
    }

    private void getQuestions(String authToken, int testId) {
        Call<QuestionsResponse> call = apiInterface.getQuestions(authToken, testId);
        call.enqueue(new Callback<QuestionsResponse>() {
            @Override
            public void onResponse(@NonNull Call<QuestionsResponse> call, @NonNull Response<QuestionsResponse> response) {
                assert response.body() != null;
                for (int i = 0; i < response.body().getQuestionsModel().size(); i++) {
                    int j = i + 1;
                    pagerAdapter.addFragment(new QuestionFrag(getApplicationContext(), i, response.body().getQuestionsModel().get(i).getQuestion(), response.body().getQuestionsModel().get(i).getOptionsModel()), "Question " + j);
                }
                questionsListSize = response.body().getQuestionsModel().size();
                if(questionsListSize == 1){
                    button_seeResult.setVisibility(View.VISIBLE);
                    button_next.setVisibility(View.GONE);
                }
                setAdapter();
            }

            @Override
            public void onFailure(@NonNull Call<QuestionsResponse> call, @NonNull Throwable t) {
            }
        });
    }

    private void setAdapter() {

        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                Log.i("pos", String.valueOf(position));
                Log.i("size", String.valueOf(questionsListSize));
                currentItem = position;

                int pagePos = position+1;

                if(pagePos == 1 && questionsListSize > 1){
                    button_prev.setVisibility(View.GONE);
                    button_seeResult.setVisibility(View.GONE);
                    button_next.setVisibility(View.VISIBLE);
                } else if (pagePos < questionsListSize){
                    button_prev.setVisibility(View.VISIBLE);
                    button_next.setVisibility(View.VISIBLE);
                    button_seeResult.setVisibility(View.GONE);
                } else if(pagePos == questionsListSize){
                    button_prev.setVisibility(View.GONE);
                    button_next.setVisibility(View.GONE);
                    button_seeResult.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.prev_questionFrag, R.id.next_questionFrag,R.id.seeResult_questionFrag})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.prev_questionFrag:
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
                break;
            case R.id.next_questionFrag:
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                break;

            case R.id.seeResult_questionFrag:
                Toast.makeText(getApplicationContext(),"as",Toast.LENGTH_SHORT).show();
                break;
        }
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
    public void scrollNext() {
        Runnable runnable = new Runnable() {
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        };
        Handler handler = new android.os.Handler();
        handler.postDelayed(runnable, 500);
    }

    @Override
    public void onBackPressed() {
        if(currentItem == 0) {
            finish();
        } else {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }
}
