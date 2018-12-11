package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TopicDetailModel implements Serializable {

    @SerializedName("Error")
    boolean error;

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

    public PayloadTopics getPayloadTopics() {
        return payloadTopics;
    }

    public void setPayloadTopics(PayloadTopics payloadTopics) {
        this.payloadTopics = payloadTopics;
    }

    public List<Videos> getVideos() {
        return videos;
    }

    public void setVideos(List<Videos> videos) {
        this.videos = videos;
    }

    public List<TestModel> getTestModels() {
        return testModels;
    }

    public void setTestModels(List<TestModel> testModels) {
        this.testModels = testModels;
    }

    public List<ReviewModel> getReviewModels() {
        return reviewModels;
    }

    public void setReviewModels(List<ReviewModel> reviewModels) {
        this.reviewModels = reviewModels;
    }

    @SerializedName("Status")
    int status;
    @SerializedName("Message")
    String message;

    @SerializedName("Payload")
    PayloadTopics payloadTopics;

    @SerializedName("videos")
    List<Videos> videos;

    @SerializedName("test")
    List<TestModel> testModels;

    @SerializedName("reviews")
    List<ReviewModel> reviewModels;
}

