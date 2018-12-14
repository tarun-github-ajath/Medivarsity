package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewResponse implements Serializable {

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

    @SerializedName("Error")
    boolean error;

    @SerializedName("Status")
    int status;
    @SerializedName("Message")
    String message;

}
