package com.medavarsity.user.medavarsity.NetworkCalls;

import com.medavarsity.user.medavarsity.Model.CollegeResponse;
import com.medavarsity.user.medavarsity.Model.FacebookResponse;
import com.medavarsity.user.medavarsity.Model.HomeModel;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.RegisterStudentResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("CollegeList")
    Call<CollegeResponse> doGetCollegeList();

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



}
