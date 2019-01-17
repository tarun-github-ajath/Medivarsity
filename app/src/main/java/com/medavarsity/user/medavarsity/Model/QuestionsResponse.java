package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class QuestionsResponse implements Serializable {

    @SerializedName("Error")
    boolean error;

    @SerializedName("Status")
    int status;

    @SerializedName("Message")
    String message;

    @SerializedName("Authtoken")
    String token;

    @SerializedName("Payload")
    ArrayList<QuestionsModel> questionsModel;

    public String getToken() {
        return token;
    }

    public boolean isError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<QuestionsModel> getQuestionsModel() {
        return questionsModel;
    }
}
