package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class PayloadTopics implements Serializable {

    public SubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(SubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    @SerializedName("subjectdetails")
    SubjectDetails subjectDetails;


    @SerializedName("videos")
    ArrayList<Videos> videos;

    @SerializedName("test")
    ArrayList<TestModel> testModels;

    @SerializedName("reviews")
    ArrayList<ReviewModel> reviewModels;

    public ArrayList<Videos> getVideos() {
        return videos;
    }

    public void setVideos(ArrayList<Videos> videos) {
        this.videos = videos;
    }

    public ArrayList<TestModel> getTestModels() {
        return testModels;
    }

    public void setTestModels(ArrayList<TestModel> testModels) {
        this.testModels = testModels;
    }

    public ArrayList<ReviewModel> getReviewModels() {
        return reviewModels;
    }

    public void setReviewModels(ArrayList<ReviewModel> reviewModels) {
        this.reviewModels = reviewModels;
    }

}

