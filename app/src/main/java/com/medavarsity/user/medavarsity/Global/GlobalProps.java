package com.medavarsity.user.medavarsity.Global;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Constants.ConstantVariables;
import com.medavarsity.user.medavarsity.Model.StudentResponse;

public class GlobalProps {
    private static GlobalProps instance = null;

    public String userProfile = "";
    public String userName;
    public String userEmail;
    public String userContact;
    public String fbId;
    public String authToken;
    public String collegeName;
    public String year;
    public String userState;

    public GlobalProps(){}

    public static synchronized GlobalProps getInstance() {
        if(null == instance){
            instance = new GlobalProps();
        }
        return instance;
    }

    public void saveStudentResponseInPref(StudentResponse studentResponse,SharedPreferences sharedPreferences){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(studentResponse);
        prefsEditor.putString(ConstantVariables.LOGIN_STUDENT_OBJECT, json);
        prefsEditor.apply();
    }




}

