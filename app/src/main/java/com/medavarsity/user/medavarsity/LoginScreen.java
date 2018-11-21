package com.medavarsity.user.medavarsity;

import android.content.Intent;
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
import com.facebook.login.Login;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.RegisterStudentResponse;
import com.medavarsity.user.medavarsity.Model.StudentResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class LoginScreen extends AppCompatActivity {
    CallbackManager callbackManager;
    EditText editText_useremail, editText_userpassword;
    Button btn_sign;
    TextView textView_signup;
    LoginButton loginButton;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initializeIds();
        callbackManager = CallbackManager.Factory.create();

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
              /*  Intent intent = new Intent(LoginScreen.this, DashBoard.class);
                startActivity(intent);
                finish();*/
                doLogin();
            }
        });

        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    private void getUserDetails(LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Intent intent = new Intent(LoginScreen.this,
                        DashBoard.class);
                intent.putExtra("userProfile", object.toString());
                startActivity(intent);
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
            Call<LoginStudentResponse> loginStudentResponseCall = apiInterface.getStudentInfo(email, pass, "0", "", "Android", "dhfhjdf");
            loginStudentResponseCall.enqueue(new Callback<LoginStudentResponse>() {
                @Override
                public void onResponse(Call<LoginStudentResponse> call, Response<LoginStudentResponse> response) {

                    System.out.println(response.body());
                    navigateDashboard(response.body().getStudentResponse());
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
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
