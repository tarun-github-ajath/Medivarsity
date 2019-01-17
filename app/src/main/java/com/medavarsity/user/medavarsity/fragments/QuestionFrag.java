package com.medavarsity.user.medavarsity.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.R;

@SuppressLint("ValidFragment")
public class QuestionFrag extends Fragment {

    int index;

    @SuppressLint("ValidFragment")
    public QuestionFrag(int mCurrentPage) {
        this.index = mCurrentPage;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle data = getArguments();
        assert data != null;
//        index = data.getInt("current_page", 0);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_question_fragment, container,false);
        TextView tv = v.findViewById(R.id.question);
        tv.setText("safssdf");
        return v;
    }





}
