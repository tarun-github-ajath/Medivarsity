package com.medavarsity.user.medavarsity.NetworkCalls;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.medavarsity.user.medavarsity.Model.CollegeResponse;
import com.medavarsity.user.medavarsity.Model.HomeModel;
import com.medavarsity.user.medavarsity.Model.LectureModal;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.MyTopicResponse;
import com.medavarsity.user.medavarsity.Model.MyTopicsModel;
import com.medavarsity.user.medavarsity.Model.QuestionsModel;
import com.medavarsity.user.medavarsity.Model.QuestionsResponse;
import com.medavarsity.user.medavarsity.Model.RegisterStudentResponse;
import com.medavarsity.user.medavarsity.Model.ResendOtpResponse;
import com.medavarsity.user.medavarsity.Model.ReviewResponse;
import com.medavarsity.user.medavarsity.Model.StateResponse;
import com.medavarsity.user.medavarsity.Model.TestResultResponse;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.Model.VerifyOtpResponse;

import org.json.JSONArray;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("StateList")
    Call<StateResponse> getStates();

    @FormUrlEncoded
    @POST("CollegeList")
    Call<CollegeResponse> doGetCollegeList(@Field("state_id") int state_id);

    @FormUrlEncoded
    @POST("RegisterStudent")
    Call<RegisterStudentResponse> registerStudent(@Field("name") String name, @Field("email") String email
            , @Field("contact_num") String contact_num, @Field("password") String password, @Field("college") String college
            , @Field("year") String year, @Field("socialid") String social_id, @Field("regtype") String reg_type,
                                                  @Field("imageurl") String imageurl);

    @FormUrlEncoded
    @POST("LoginStudent")
    Call<LoginStudentResponse> getStudentInfo(@Field("username") String username, @Field("password") String password
            , @Field("login_type") String login_type, @Field("social_id") String social_id, @Field("device_type") String device_type
            , @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("Home")
    Call<HomeModel> getHomeData(@Field("authtoken") String authtoken);

    @FormUrlEncoded
    @POST("Subjectdetails")
    Call<TopicDetailModel> getTopicDetails(@Field("authtoken") String authtoken, @Field("subject_id") int topic_id);

    @FormUrlEncoded
    @POST("AddReview")
    Call<ReviewResponse> SubmitFeedback(@Field("authtoken") String authtoken, @Field("video_id") int video_id, @Field("subject_id")
            int topic_id, @Field("review") String review, @Field("rating") float rating);

    @FormUrlEncoded
    @POST("Lectures")
    Call<LectureModal> getLectures_AddReview(@Field("authtoken") String authtoken, @Field("subject_id") int topic_id);

    @FormUrlEncoded
    @POST("Verifyotp")
    Call<VerifyOtpResponse> verifyOtp(@Field("student_id") String student_id, @Field("otp") int otp);

    @FormUrlEncoded
    @POST("Resendotp")
    Call<ResendOtpResponse> resendOtp(@Field("student_id") String student_id);

    @FormUrlEncoded
    @POST("Mytopics")
    Call<MyTopicResponse> getMyTopics(@Field("authtoken") String token);

    @FormUrlEncoded
    @POST("Questionslist")
    Call<QuestionsResponse> getQuestions(@Field("authtoken") String token, @Field("test_id") int testId);

    @FormUrlEncoded
    @POST("Answerlist")
    Call<TestResultResponse> getTestResults(@Field("authtoken") String token, @Field("test_id") int testId, @Field("somejson") ArrayList<JsonArray> questionsAnsArray);

}
