package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

public class RegisterStudentResponse {

    @SerializedName("Error")
    boolean error;

    @SerializedName("Status")
    int status;

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

    @SerializedName("Message")
    String message;

    @SerializedName("Payload")
    int payload;
}
