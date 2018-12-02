package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class HomeModel implements Serializable {

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

    public PayloadHome getPayloadHome() {
        return payloadHome;
    }

    public void setPayloadHome(PayloadHome payloadHome) {
        this.payloadHome = payloadHome;
    }

    @SerializedName("Error")
    @Expose
    boolean error;

    @SerializedName("Status")
    @Expose
    int status;

    @SerializedName("Message")
    @Expose
    String message;


    @SerializedName("Payload")
    @Expose
    PayloadHome payloadHome;


}

class PayloadHome implements Serializable {
    public String getIntrourl() {
        return introurl;
    }

    public void setIntrourl(String introurl) {
        this.introurl = introurl;
    }

    public List<com.medavarsity.user.medavarsity.Model.dailyUpdates> getDailyUpdates() {
        return dailyUpdates;
    }

    public void setDailyUpdates(List<com.medavarsity.user.medavarsity.Model.dailyUpdates> dailyUpdates) {
        this.dailyUpdates = dailyUpdates;
    }

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }

    @SerializedName("introurl")
    @Expose
    String introurl;


    @SerializedName("dailyupdates")
    @Expose
    List<dailyUpdates> dailyUpdates;


    @SerializedName("subjects")
    @Expose
    List<Subjects> subjects;
}

class dailyUpdates implements Serializable {
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @SerializedName("title")
    @Expose
    String title;

    @SerializedName("url")
    @Expose
    String url;


}

class Subjects implements Serializable {
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

class Videos implements Serializable {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("subject_id")
    @Expose
    int subject_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_image_url() {
        return video_image_url;
    }

    public void setVideo_image_url(String video_image_url) {
        this.video_image_url = video_image_url;
    }

    public int getVideo_type() {
        return video_type;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("video_url")
    @Expose
    String video_url;

    @SerializedName("video_image_url")
    @Expose
    String video_image_url;
    @SerializedName("video_type")
    @Expose
    int video_type;
    @SerializedName("status")
    @Expose
    int status;
}