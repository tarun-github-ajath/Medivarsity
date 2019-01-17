package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OptionsModel implements Serializable {

    @SerializedName("option")
    String optionName;

    @SerializedName("correct")
    int correct;



}
