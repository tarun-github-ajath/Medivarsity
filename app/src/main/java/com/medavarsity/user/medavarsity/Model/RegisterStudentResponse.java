package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterStudentResponse implements Serializable {


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

    /*@SerializedName("Payload")
    int payload;*/

    public Payload_Register getPayload() {
        return this.payload;
    }

    public void setPayload(Payload_Register payload) {
        this.payload = payload;
    }

    @SerializedName("Payload")
    Payload_Register payload;

}

