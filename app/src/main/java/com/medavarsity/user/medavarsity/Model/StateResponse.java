package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StateResponse {

    @SerializedName("Error")
    boolean error;

    @SerializedName("Status")
    int status;
    @SerializedName("Payload")
    ArrayList<StateModel> stateModels;

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

    public ArrayList<StateModel> getStateModels() {
        return stateModels;
    }

    public void setStateModels(ArrayList<StateModel> stateModels) {
        this.stateModels = stateModels;
    }


}
