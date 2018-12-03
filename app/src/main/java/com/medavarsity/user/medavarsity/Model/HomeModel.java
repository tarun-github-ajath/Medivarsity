package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HomeModel implements Serializable {

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

    public PayloadHome getPayloadHome() {
        return payloadHome;
    }

    public void setPayloadHome(PayloadHome payloadHome) {
        this.payloadHome = payloadHome;
    }

    @SerializedName("Error")
    @Expose
    boolean error;

    @SerializedName("Status")
    @Expose
    int status;

    @SerializedName("Message")
    @Expose
    String message;


    @SerializedName("Payload")
    @Expose
    PayloadHome payloadHome;


}

