package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Payload_testResults_questions implements Serializable {

    @SerializedName("question_id")
    int quesId;

    @SerializedName("question")
    String quesName;

    @SerializedName("correct")
    String answer;

    public int getQuesId() {
        return quesId;
    }

    public String getQuesName() {
        return quesName;
    }

    public String getAnswer() {
        return answer;
    }
}
