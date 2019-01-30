package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OptionsModel implements Serializable {

    @SerializedName("option")
    String optionName;

    @SerializedName("correct")
    int correct;

    @SerializedName("option_id")
    int option_id;

    public int getOption_id() {
        return option_id;
    }

    public String getOptionName() {
        return optionName;
    }

    public int getCorrect() {
        return correct;
    }
}
