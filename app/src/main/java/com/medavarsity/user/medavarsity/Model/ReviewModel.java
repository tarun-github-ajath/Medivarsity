package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewModel implements Serializable {
    @SerializedName("review")
    String review;
    @SerializedName("rating")
    int rating;
    @SerializedName("video_title")
    String video_title;
    @SerializedName("reviwername")
    String reviwername;
    @SerializedName("revieweremail")
    String revieweremail;
    @SerializedName("reviwerimageurl")
    String reviwerimageurl;

}
