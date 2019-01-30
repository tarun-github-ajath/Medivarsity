package com.medavarsity.user.medavarsity.Model;
import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

public class TestResultResponse {

    @SerializedName("Error")
    boolean error;

    @SerializedName("Status")
    int status;

    @SerializedName("Message")
    String message;


    @SerializedName("Activeongoogleplay")
    int Activeongoogleplay;


    @SerializedName("Payload")
    Payload_TestResults payload_testResults;


    public boolean isError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public int getActiveongoogleplay() {
        return Activeongoogleplay;
    }

    public Payload_TestResults getPayload_testResults() {
        return payload_testResults;
    }
}
