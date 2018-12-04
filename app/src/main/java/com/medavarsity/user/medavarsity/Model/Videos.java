package com.medavarsity.user.medavarsity.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Videos implements Serializable {
    @SerializedName("id")
    @Expose
    int id;
    @SerializedName("subject_id")
    @Expose
    int subject_id;

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    @SerializedName("video_title")
    String video_title;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_image_url() {
        return video_image_url;
    }

    public void setVideo_image_url(String video_image_url) {
        this.video_image_url = video_image_url;
    }

    public int getVideo_type() {
        return video_type;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @SerializedName("video_url")
    @Expose
    String video_url;

    @SerializedName("video_image_url")
    @Expose
    String video_image_url;
    @SerializedName("video_type")
    @Expose
    int video_type;
    @SerializedName("status")
    @Expose
    int status;
}
