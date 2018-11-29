package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

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

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @SerializedName("Payload")
    Payload payload;

}

class Payload implements Serializable {
    @SerializedName("student_id")
    public int studentId;
    @SerializedName("contact_no")
    public String contactNo;
    @SerializedName("otp")
    public String otp;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
