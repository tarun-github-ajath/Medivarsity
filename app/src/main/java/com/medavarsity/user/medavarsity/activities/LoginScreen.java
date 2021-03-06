package com.medavarsity.user.medavarsity.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.se.omapi.Session;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.medavarsity.user.medavarsity.Constants.ConstantVariables;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.RegisterStudentResponse;
import com.medavarsity.user.medavarsity.Model.StudentResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.SEND_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class LoginScreen extends AppCompatActivity {

    CallbackManager callbackManager;
    public static int PermissionCode = 3;
    EditText editText_useremail, editText_userpassword;
    Button btn_sign;
    TextView textView_signup;
    LoginButton loginButton;
    ApiInterface apiInterface;
    Button fb_login;
    Dialog mDialog;
    boolean is_firsttime = false;
    static SharedPreferences sharedPreferences;
    CommonMethods mCommonMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initializeIds();

        mCommonMethod = new CommonMethods(LoginScreen.this);
        callbackManager = CallbackManager.Factory.create();
        sharedPreferences = getSharedPreferences(ConstantVariables.SHARED_FILE, MODE_PRIVATE);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if (!CheckingPermissionIsEnabledOrNot()) {
            RequestMultiplePermission();
        }

        if (Build.VERSION.SDK_INT == 26) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        textView_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });

        checkAlreadyLogin();
        btn_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CommonMethods.isNetworkAvailable(LoginScreen.this)) {
                    doLogin();
                } else {
                    Toast.makeText(LoginScreen.this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        List<String> permissionNeeds = Arrays.asList(/*"user_photos",*/ "email"
                /*"user_birthday"*/, "public_profile");

        fb_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.performClick();
            }
        });
        loginButton.setReadPermissions(permissionNeeds);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println(loginResult.getAccessToken().getToken());
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
                if (error.getMessage().contains("CONNECTION_FAILURE:")) {
                    Toast.makeText(LoginScreen.this, "Please check your internet connections", Toast.LENGTH_SHORT).show();
                }
                System.out.println(error.toString());
            }
        });
    }

    private void getUserDetails(final LoginResult loginResult) {
        GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                String loginresultResponse = object.toString();
//                System.out.println("facebook object" + " " + loginresultResponse);
                try {
                    String android_id = Settings.Secure.getString(LoginScreen.this.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    JSONObject jsonObject = new JSONObject(loginresultResponse);
                    StudentResponse studentResponse = new StudentResponse();
                    studentResponse.setFacebook_id(jsonObject.has("id") ? jsonObject.getString("id") : "");
                    studentResponse.setEmail(jsonObject.has("email") ? jsonObject.getString("email") : "");
                    studentResponse.setName(jsonObject.has("name") ? jsonObject.getString("name") : "");
                    JSONObject pictureObject = jsonObject.getJSONObject("picture");
                    JSONObject dataObj = pictureObject.getJSONObject("data");
                    String imageUrl = dataObj.has("url") ? dataObj.getString("url") : "";

                    studentResponse.setImage_url(imageUrl);
                    studentResponse.setAuthToken(loginResult.getAccessToken().getToken());
                    Log.i("fbToken",loginResult.getAccessToken().getToken());
                    loginCall("", "", "1", studentResponse.getFacebook_id(), "0", android_id, studentResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(loginresultResponse);
            }
        });

        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(100).height(100)");
        graphRequest.setParameters(permission_param);
        graphRequest.executeAsync();
    }

    private void doLogin() {
        String android_id = Settings.Secure.getString(LoginScreen.this.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        //  apiInterface = ApiClient.getClient().create(ApiInterface.class);
        String email = editText_useremail.getText().toString().trim();
        String pass = editText_userpassword.getText().toString().trim();

        if (email.equalsIgnoreCase("") || pass.equalsIgnoreCase("")) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show();
        } else {
            loginCall(email, pass, "0", "", "0", android_id, null);
        }
    }

    private void initializeIds() {
        editText_useremail = findViewById(R.id.email_address);
        editText_userpassword = findViewById(R.id.password);
        btn_sign = findViewById(R.id.sign_in);
        textView_signup = findViewById(R.id.sign_up);
        loginButton = findViewById(R.id.fb_login);
        fb_login = findViewById(R.id.fb_customlogin);

        editText_useremail.setText("tarun.sni123@yahoo.com");
        editText_userpassword.setText("123456");

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
        prefsEditor.putString(ConstantVariables.LOGIN_STUDENT_OBJECT, json);
        prefsEditor.putString("authToken", studentResponse.getAuthToken());
        prefsEditor.commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PermissionCode) {
            if (grantResults.length > 0) {

                boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean SendSMSPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                //   boolean GetAccountsPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                boolean readExternal = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                boolean writeExtrenl = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                if (/*CameraPermission &&*/ SendSMSPermission /*&& GetAccountsPermission*/ && readExternal && writeExtrenl) {

                } else {
                    Toast.makeText(this, "Permissions denied!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void checkAlreadyLogin() {
        is_firsttime = sharedPreferences.getBoolean(ConstantVariables.IS_FIRST_TIME, false);

        if (is_firsttime) {
            Gson gson = new Gson();
            String studentResponse = sharedPreferences.getString(ConstantVariables.LOGIN_STUDENT_OBJECT,"");
            if(studentResponse != null && !studentResponse.equals("")){

                StudentResponse studentResponseObj = gson.fromJson(studentResponse,StudentResponse.class);
                setGlobalProps(studentResponseObj,sharedPreferences);
                startDashBoard();
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDialog != null)
            if (mDialog.isShowing()) {
                mDialog.cancel();
            }
    }

    public boolean CheckingPermissionIsEnabledOrNot() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), SEND_SMS);
        int ForthPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int fifth = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);


        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ForthPermissionResult == PackageManager.PERMISSION_GRANTED
                && fifth == PackageManager.PERMISSION_GRANTED;
    }

    private void RequestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(LoginScreen.this, new String[]
                {
                        CAMERA,
                        SEND_SMS
                        , READ_EXTERNAL_STORAGE,
                        WRITE_EXTERNAL_STORAGE
                }, PermissionCode);

    }

    private void loginCall(String email, String pass, String logintype, String social_id, String device_type, String device_id, final StudentResponse studentResponse) {

        if (social_id.equalsIgnoreCase("") && logintype.equalsIgnoreCase("0")) {
            mCommonMethod.showCommonDialog(LoginScreen.this, "Please wait...");
            try {
                final Call<LoginStudentResponse> loginStudentResponseCall = apiInterface.
                        getStudentInfo(email, pass, logintype, social_id, device_type, device_id);

                loginStudentResponseCall.enqueue(new Callback<LoginStudentResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginStudentResponse> call, @NonNull Response<LoginStudentResponse> response) {
                        mCommonMethod.cancelDialog();

                        if(!response.isSuccessful()){
                            return;
                        }

                        assert response.body() != null;
                        Log.i("payload", String.valueOf(response.body()));

                        if(response.body().getStatus() == 1){
                            StudentResponse studentResponse1 = response.body().getPayload();
                            studentResponse1.setAuthToken(response.body().getAuth_token());

                            saveInPref(studentResponse1);
                            setGlobalProps(studentResponse1,sharedPreferences);

                            startDashBoard();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginStudentResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        mCommonMethod.cancelDialog();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if(logintype.equals("1")){

            try {
                final Call<LoginStudentResponse> loginStudentResponseCall = apiInterface.
                        getStudentInfo(email, pass, logintype, social_id, device_type, device_id);

                loginStudentResponseCall.enqueue(new Callback<LoginStudentResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<LoginStudentResponse> call, @NonNull Response<LoginStudentResponse> response) {
                        mCommonMethod.cancelDialog();

                        if(!response.isSuccessful()){
                            return;
                        }

                        assert response.body() != null;
                        Log.i("payload", String.valueOf(response.body()));

                        if(response.body().getStatus() == 1){
                            StudentResponse studentResponse1 = response.body().getPayload();
                            studentResponse1.setAuthToken(response.body().getAuth_token());

                            saveInPref(studentResponse1);
                            setGlobalProps(studentResponse1,sharedPreferences);
                            startDashBoard();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<LoginStudentResponse> call, @NonNull Throwable t) {
                        t.printStackTrace();
                        mCommonMethod.cancelDialog();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(getApplicationContext(),RegisterScreen.class);
            intent.putExtra(ConstantVariables.NON_VALID_FB_STUDENT,studentResponse);
            startActivity(intent);
        }
    }

    public static void setGlobalProps(StudentResponse studentResponse,SharedPreferences sharedPreferences){
        GlobalProps.getInstance().userProfile = studentResponse.getImage_url();
        GlobalProps.getInstance().userName = studentResponse.getName();
        GlobalProps.getInstance().userEmail = studentResponse.getEmail();
        GlobalProps.getInstance().userContact = studentResponse.getContact_no();
        GlobalProps.getInstance().authToken = sharedPreferences.getString("authToken","");
        Log.i("tokenLogin",GlobalProps.getInstance().authToken);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    private void startDashBoard(){
        Intent intent = new Intent(LoginScreen.this,DashBoard.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
