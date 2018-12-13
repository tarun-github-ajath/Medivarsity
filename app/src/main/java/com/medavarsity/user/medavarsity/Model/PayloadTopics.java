package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PayloadTopics implements Serializable {

    public SubjectDetails getSubjectDetails() {
        return subjectDetails;
    }

    public void setSubjectDetails(SubjectDetails subjectDetails) {
        this.subjectDetails = subjectDetails;
    }

    @SerializedName("subjectdetails")
    SubjectDetails subjectDetails;

}

