package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubjectDetail implements Serializable {

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PayloadTopics getPayloadTopics() {
        return payloadTopics;
    }

    public void setPayloadTopics(PayloadTopics payloadTopics) {
        this.payloadTopics = payloadTopics;
    }

    @SerializedName("Error")
    boolean error;
    @SerializedName("Status")
    int status;
    @SerializedName("Message")
    String message;


    @SerializedName("Payload")
    PayloadTopics payloadTopics;

}
