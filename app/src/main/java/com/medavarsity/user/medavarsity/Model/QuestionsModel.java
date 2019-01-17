package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionsModel implements Serializable {

    @SerializedName("question_id")
    int question_id;

    @SerializedName("question")
    String question;

    @SerializedName("answer_type")
    String answerType;

    @SerializedName("options")
    ArrayList<OptionsModel> optionsModel;


    public int getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public ArrayList<OptionsModel> getOptionsModel() {
        return optionsModel;
    }
}
