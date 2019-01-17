package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TestModel implements Serializable {


    @SerializedName("test_id")
    int test_id;

    @SerializedName("test_name")
    String test_name;

    public int getTest_id() {
        return test_id;
    }

    public String getTest_name() {
        return test_name;
    }
}
