package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResendOtpResponse implements Serializable {

    @SerializedName("Error")
    boolean error;

    @SerializedName("Status")
    int status;

    @SerializedName("Message")
    String message;


    public boolean isError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
