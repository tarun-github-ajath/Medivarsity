package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CollegeResponse implements Serializable {

    @SerializedName("Error")
    boolean error;

    @SerializedName("Status")
    int status;
    @SerializedName("Payload")
    ArrayList<CollegeModel> collegeModels;

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

    public ArrayList<CollegeModel> getCollegeModels() {
        return collegeModels;
    }

    public void setCollegeModels(ArrayList<CollegeModel> collegeModels) {
        this.collegeModels = collegeModels;
    }
}
