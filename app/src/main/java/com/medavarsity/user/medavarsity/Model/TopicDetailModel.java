package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TopicDetailModel implements Serializable {

    @SerializedName("Error")
    boolean error;

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




    @SerializedName("Status")
    int status;
    @SerializedName("Message")
    String message;

    public PayloadTopics getPayloadTopics() {
        return payloadTopics;
    }

    public void setPayloadTopics(PayloadTopics payloadTopics) {
        this.payloadTopics = payloadTopics;
    }

    @SerializedName("Payload")
    PayloadTopics payloadTopics;

}

