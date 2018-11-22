package com.medavarsity.user.medavarsity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.RegisterStudentResponse;
import com.medavarsity.user.medavarsity.Model.StudentResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginScreen extends AppCompatActivity {
    CallbackManager callbackManager;
    EditText editText_useremail, editText_userpassword;
    Button btn_sign;
    TextView textView_signup;
    LoginButton loginButton;
    ApiInterface apiInterface;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initializeIds();
        callbackManager = CallbackManager.Factory.create();
        sharedPreferences = getSharedPreferences(ConstantVariabls.SHARED_FILE, MODE_PRIVATE);

        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
                finish();
            }
        });

        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });

        List<String> permissionNeeds = Arrays.asList("user_photos", "email",
                "user_birthday", "public_profile");
        loginButton.setReadPermissions(permissionNeeds);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println(loginResult);
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

                System.out.println(error.toString());
            }
        });


    }

    private void getUserDetails(LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                String loginresultResponse = object.toString();
                try {
                    JSONObject jsonObject = new JSONObject(loginresultResponse);
                    StudentResponse studentResponse = new StudentResponse();
                    studentResponse.setEmail(jsonObject.getString("email"));
                    studentResponse.setName(jsonObject.getString("name"));

                    Intent intent = new Intent(LoginScreen.this,
                            DashBoard.class);
                    intent.putExtra("student_info", studentResponse);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                System.out.println(loginresultResponse);
            }
        });

        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(20)");
        graphRequest.setParameters(permission_param);
        graphRequest.executeAsync();

    }

    private void doLogin() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String email = editText_useremail.getText().toString().trim();
        String pass = editText_userpassword.getText().toString().trim();

        if (email.equalsIgnoreCase("") || pass.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
        } else {
            Call<LoginStudentResponse> loginStudentResponseCall = apiInterface.
                    getStudentInfo(email, pass, "0", "", "Android", "dhfhjdf");
            loginStudentResponseCall.enqueue(new Callback<LoginStudentResponse>() {
                @Override
                public void onResponse(Call<LoginStudentResponse> call, Response<LoginStudentResponse> response) {

                    System.out.println(response.body());

                    String message = response.body().getMessage();
                    if (response.body().getStatus() == 1) {
                        emptyVariables();
                        navigateDashboard(response.body().getStudentResponse());
                        saveInPref(response.body().getStudentResponse());
                    } else {
                        Toast.makeText(LoginScreen.this, "Oops!" + " " + message, Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<LoginStudentResponse> call, Throwable t) {

                }
            });
        }
    }

    private void initializeIds() {
        editText_useremail = (EditText) findViewById(R.id.email_address);
        editText_userpassword = (EditText) findViewById(R.id.password);
        btn_sign = (Button) findViewById(R.id.sign_in);
        textView_signup = (TextView) findViewById(R.id.sign_up);
        loginButton = (LoginButton) findViewById(R.id.fb_login);

    }

    private void navigateDashboard(StudentResponse studentResponse) {

        Intent intent = new Intent(LoginScreen.this, DashBoard.class);
        intent.putExtra("student_info", studentResponse);
        startActivity(intent);
        finish();
    }

    private void emptyVariables() {
        editText_useremail.setText("");
        editText_userpassword.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void saveInPref(StudentResponse studentResponse) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(studentResponse);
        prefsEditor.putString(ConstantVariabls.LOGIN_STUDENT_OBJECT, json);
        prefsEditor.commit();
    }
}
