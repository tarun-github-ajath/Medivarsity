package com.medavarsity.user.medavarsity.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.medavarsity.user.medavarsity.Model.OptionsModel;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class QuestionsEndFragment extends Fragment {

    Context context;
    Button buttonSeeResult;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ValidFragment")
    public QuestionsEndFragment(Context context) {
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle data = getArguments();
        assert data != null;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_question_end_fragment, container,false);
        buttonSeeResult = v.findViewById(R.id.seeResult_questionEndFragment);
        buttonSeeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"as",Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}
