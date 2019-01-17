package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StateModel implements Serializable {

    @SerializedName("state_id")
    int stateId;

    @SerializedName("state_name")
    String stateName;

    public int getStateId() {
        return stateId;
    }

    public String getStateName() {
        return stateName;
    }


    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
