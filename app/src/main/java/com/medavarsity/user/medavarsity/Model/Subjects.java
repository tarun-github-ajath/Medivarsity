package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Subjects implements Serializable {
    @SerializedName("subjectname")
    @Expose
    String subjectname;

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public List<Videos> getVideos() {
        return videos;
    }

    public void setVideos(List<Videos> videos) {
        this.videos = videos;
    }

    @SerializedName("subscription")
    @Expose
    String subscription;

    @SerializedName("videos")
    @Expose
    List<Videos> videos;
}
