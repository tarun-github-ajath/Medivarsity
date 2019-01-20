package com.medavarsity.user.medavarsity.fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Adapters.TestAdapter;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.R;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")

public class GiveTestFragment extends Fragment {
    View root;
    @BindView(R.id.recylerView_giveTest)
    RecyclerView recyclerView;

    TopicDetailModel topicDetailModel;
    private int testId;

    @SuppressLint("ValidFragment")
    public GiveTestFragment(TopicDetailModel topicDetailModel) {
        this.topicDetailModel = topicDetailModel;
    }

    TextView textViewHeader;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.frag_test, container, false);
        textViewHeader = root.findViewById(R.id.testName_fragTest);
        recyclerView = root.findViewById(R.id.recylerView_giveTest);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        Objects.requireNonNull(recyclerView.getLayoutManager()).setMeasurementCacheEnabled(false);
        recyclerView.setNestedScrollingEnabled(false);

        if(topicDetailModel.getPayloadTopics().getTestModels().size() > 0){
            testId = topicDetailModel.getPayloadTopics().getTestModels().get(0).getTest_id();
            renderTests();
        } else {
            textViewHeader.setText("No tests available");
        }

        return root;
    }

    private void renderTests(){
        TestAdapter testAdapter = new TestAdapter(getContext(),topicDetailModel.getPayloadTopics().getTestModels());
        recyclerView.setAdapter(testAdapter);
    }
}
