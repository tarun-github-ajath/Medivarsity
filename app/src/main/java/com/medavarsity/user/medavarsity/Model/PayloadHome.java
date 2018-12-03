package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PayloadHome implements Serializable {
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
