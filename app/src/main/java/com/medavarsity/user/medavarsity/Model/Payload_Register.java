package com.medavarsity.user.medavarsity.Model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Payload_Register implements Serializable {
    @SerializedName("student_id")
    public int studentId;
    @SerializedName("contact_no")
    public String contactNo;
    @SerializedName("otp")
    public int otp;

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

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}