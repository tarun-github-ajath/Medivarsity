package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyTopicsModel implements Serializable {

    @SerializedName("id")
    int id;

    @SerializedName("subject_name")
    String subName;

    public int getId() {
        return id;
    }

    public String getSubName() {
        return subName;
    }
}
