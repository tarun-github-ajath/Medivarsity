package com.medavarsity.user.medavarsity.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Model.OptionsModel;
import com.medavarsity.user.medavarsity.Model.QuestionsModel;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;
import java.util.Objects;

@SuppressLint("ValidFragment")
public class QuestionFrag extends Fragment {

    int index;
    String question;
    ArrayList<OptionsModel> optionsModels;
    Context context;
    @SuppressLint("ValidFragment")
    public QuestionFrag(Context context, int mCurrentPage, String question, ArrayList<OptionsModel> optionsModel) {
        this.index = mCurrentPage;
        this.question = question;
        this.optionsModels = optionsModel;
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle data = getArguments();
        assert data != null;
//      index = data.getInt("current_page", 0);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_question_fragment, container,false);
        TextView tv = v.findViewById(R.id.question);

        RadioGroup radioGroup = v.findViewById(R.id.radioGroup);
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        int quesNo = index + 1;

        tv.setText("Q."+quesNo+") "+question);
        for (int i=0;i<optionsModels.size();i++){
            int j = i+1;
            int id = getResources().getIdentifier("radioButton_"+j,"id", context.getPackageName());
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(optionsModels.get(i).getOptionName());
            radioGroup.addView(radioButton);

        }

        return v;
    }
}
