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

    @SerializedName("college_name")
    String collegeName;

    @SerializedName("mbbs_year")
    String year;

    @SerializedName("image_url")
    String imageUrl;

    @SerializedName("status")
    int status;

    @SerializedName("created_date")
    String date;

    @SerializedName("Authtoken")
    String authtoken;

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

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName;
    }
}
