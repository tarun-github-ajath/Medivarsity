package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Model.Payload_testResults_questions;
import com.medavarsity.user.medavarsity.Model.QuestionsModel;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;

public class TestResultAdapter extends RecyclerView.Adapter<TestResultAdapter.TestResultHolder> {


        Context context;
        ArrayList<Payload_testResults_questions> getQuestionsMode;
        LayoutInflater inflater;

        public TestResultAdapter(Context context, ArrayList<Payload_testResults_questions> getQuestionsMode) {
        this.context = context;
        this.getQuestionsMode= getQuestionsMode;
        }

        @NonNull
        @Override
        public TestResultHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.testresult_item, null);
        return new TestResultHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TestResultHolder testResultHolder, int position) {
            int quesNumber = position+1;
            testResultHolder.textView_ques.setText("Q."+quesNumber+") "+ getQuestionsMode.get(position).getQuesName());
            testResultHolder.textView_ans.setText("Ans. "+getQuestionsMode.get(position).getAnswer());

        }


        @Override
        public int getItemCount() {
        return getQuestionsMode.size();
        }


    class TestResultHolder extends RecyclerView.ViewHolder {
    TextView textView_ques, textView_ans;

    public TestResultHolder(@NonNull View itemView) {
        super(itemView);
        textView_ques = itemView.findViewById(R.id.tv_question);
        textView_ans = itemView.findViewById(R.id.tv_question_ans);
    }
        }
}
