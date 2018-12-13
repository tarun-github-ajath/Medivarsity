package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubjectDetails implements Serializable {
    public String getSubject_desc() {
        return subject_desc;
    }

    public void setSubject_desc(String subject_desc) {
        this.subject_desc = subject_desc;
    }

    public String getSubject_feature() {
        return subject_feature;
    }

    public void setSubject_feature(String subject_feature) {
        this.subject_feature = subject_feature;
    }

    @SerializedName("subject_description")
    String subject_desc;

    @SerializedName("subject_features")
    String subject_feature;
}
