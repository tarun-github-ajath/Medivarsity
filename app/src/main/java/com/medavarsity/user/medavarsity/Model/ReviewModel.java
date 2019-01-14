package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReviewModel implements Serializable {
    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getReviwername() {
        return reviwername;
    }

    public void setReviwername(String reviwername) {
        this.reviwername = reviwername;
    }

    public String getRevieweremail() {
        return revieweremail;
    }

    public void setRevieweremail(String revieweremail) {
        this.revieweremail = revieweremail;
    }

    public String getReviwerimageurl() {
        return reviwerimageurl;
    }

    public void setReviwerimageurl(String reviwerimageurl) {
        this.reviwerimageurl = reviwerimageurl;
    }

    @SerializedName("review")
    String review;
    @SerializedName("rating")
    float rating;
    @SerializedName("video_title")
    String video_title;
    @SerializedName("reviwername")
    String reviwername;
    @SerializedName("revieweremail")
    String revieweremail;
    @SerializedName("reviwerimageurl")
    String reviwerimageurl;

}
