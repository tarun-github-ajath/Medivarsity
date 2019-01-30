package com.medavarsity.user.medavarsity.fragments;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.medavarsity.user.medavarsity.Interfaces.ScrollTestViewPager_Next;
import com.medavarsity.user.medavarsity.Model.OptionsModel;
import com.medavarsity.user.medavarsity.Model.QuestionsModel;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.GiveTest;
import com.medavarsity.user.medavarsity.activities.MainActivity;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class QuestionFrag extends Fragment {

    int index;
    String question;
    ArrayList<OptionsModel> optionsModels;
    ArrayList<QuestionsModel> questionsModels;
    Context context;
    private RadioGroup radioGroup;
    private ScrollTestViewPager_Next scrollTestViewPager_next;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @SuppressLint("ValidFragment")
    public QuestionFrag(Context context, int mCurrentPage, String question,ArrayList<QuestionsModel> questionModel, ArrayList<OptionsModel> optionsModel) {
        this.index = mCurrentPage;
        this.question = question;
        this.optionsModels = optionsModel;
        this.questionsModels = questionModel;
        this.context = context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.optionsModels.clear();
        radioGroup.removeAllViews();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle data = getArguments();
        assert data != null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        scrollTestViewPager_next = (ScrollTestViewPager_Next) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_question_fragment, container,false);
        TextView tv = v.findViewById(R.id.question);
        radioGroup = v.findViewById(R.id.radioGroup);
        radioGroup.removeAllViews();
        radioGroup.setOrientation(LinearLayout.VERTICAL);
        int quesNo = index + 1;

        tv.setText("Q."+quesNo+") "+question);
        for (int i=0;i<optionsModels.size();i++){
            RadioButton radioButton = new RadioButton(context);
            radioButton.setText(optionsModels.get(i).getOptionName());
            radioGroup.addView(radioButton);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = radioGroup.getCheckedRadioButtonId();
                View radioButton = radioGroup.findViewById(id);
                int index = radioGroup.indexOfChild(radioButton);
                scrollTestViewPager_next.scrollNext();
                int questionIndex = GiveTest.currentItem;

                JsonArray jsonArray = new JsonArray();


                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("question_id",questionsModels.get(questionIndex).getQuestion_id());
                jsonObject.addProperty("option_id",questionsModels.get(questionIndex).getOptionsModel().get(index).getOption_id());

                jsonArray.add(jsonObject);
                if(GiveTest.question_answersArrayList.contains(jsonArray))
                GiveTest.question_answersArrayList.remove(questionIndex);

                GiveTest.question_answersArrayList.add(questionIndex,jsonArray);

            }
        });
        return v;
    }
}
