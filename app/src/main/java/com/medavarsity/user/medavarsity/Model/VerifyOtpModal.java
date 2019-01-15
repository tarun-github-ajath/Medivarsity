package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifyOtpModal implements Serializable {

    @SerializedName("student_id")
    int student_id;

    @SerializedName("name")
    String name;

    @SerializedName("email")
    String email;

    @SerializedName("contact_no")
    String contactNumber;

    @SerializedName("college_id")
    int collegeId;

    @SerializedName("mbbs_year")
    String year;

    @SerializedName("image_url")
    String imageUrl;

    @SerializedName("status")
    int status;

    @SerializedName("created_date")
    String date;

    public int getStudent_id() {
        return student_id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public int getCollegeId() {
        return collegeId;
    }

    public String getYear() {
        return year;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
