package com.medavarsity.user.medavarsity.Adapters;

public class SearchedModel {


    String subjectname="";
    int subject_id;
    String subject_video_url="";

    

    public int getSubject_video_id() {
        return subject_video_id;
    }

    public void setSubject_video_id(int subject_video_id) {
        this.subject_video_id = subject_video_id;
    }

    int subject_video_id;
    String subject_video_image_url="";
    int video_id;
    int subject_subscription;

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    String video_title="";

    public String getSubjectname() {
        return subjectname;
    }

    public void setSubjectname(String subjectname) {
        this.subjectname = subjectname;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_video_url() {
        return subject_video_url;
    }

    public void setSubject_video_url(String subject_video_url) {
        this.subject_video_url = subject_video_url;
    }

  /*  public int getSubject_video_id() {
        return subject_video_id;
    }

    public int setSubject_video_id(int subject_video_id) {
        this.subject_video_id = subject_video_id;
    }*/

    public String getSubject_video_image_url() {
        return subject_video_image_url;
    }

    public void setSubject_video_image_url(String subject_video_image_url) {
        this.subject_video_image_url = subject_video_image_url;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public int getSubject_subscription() {
        return subject_subscription;
    }

    public void setSubject_subscription(int subject_subscription) {
        this.subject_subscription = subject_subscription;
    }

    public int getVideo_type() {
        return video_type;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    int video_type;
}
