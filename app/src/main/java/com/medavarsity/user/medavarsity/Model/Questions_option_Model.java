package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Questions_option_Model implements Serializable {

    @SerializedName("option")
    String option;

    @SerializedName("correct")
    int correct;


}
