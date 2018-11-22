package com.medavarsity.user.medavarsity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.StudentResponse;

public class DashBoard extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Intent intent;
    StudentResponse studentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        sharedPreferences = getSharedPreferences(ConstantVariabls.SHARED_FILE, MODE_PRIVATE);
        getExtras();
    }

    private void getExtras() {
        intent = getIntent();
        studentResponse = (StudentResponse) intent.getSerializableExtra("student_info");
        readFromPref();
    }

    /*read student info from shared pref*/
    private void readFromPref() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantVariabls.LOGIN_STUDENT_OBJECT, "");
        studentResponse = gson.fromJson(json, StudentResponse.class);
    }
}
