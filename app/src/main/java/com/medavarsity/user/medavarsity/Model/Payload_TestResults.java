package com.medavarsity.user.medavarsity.Model;


import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Payload_TestResults implements Serializable {

    @SerializedName("result")
    Payload_TestResults_results payload_testResults_results;

    @SerializedName("questions")
    ArrayList<Payload_testResults_questions> payload_testResults_questions;

    public ArrayList<Payload_testResults_questions> getPayload_testResults_questions() {
        return payload_testResults_questions;
    }

    public Payload_TestResults_results getPayload_testResults_results() {
        return payload_testResults_results;
    }
}