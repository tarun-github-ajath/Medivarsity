package com.medavarsity.user.medavarsity.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Payload_TestResults_results implements Serializable {

    @SerializedName("correctanswer")
    int correctAns;

    @SerializedName("totalquestions")
    int totalQues;

    @SerializedName("incorrectanswer")
    int incorrectAnswers;

    public int getCorrectAns() {
        return correctAns;
    }

    public int getTotalQues() {
        return totalQues;
    }

    public int getIncorrectAnswers() {
        return incorrectAnswers;
    }
}