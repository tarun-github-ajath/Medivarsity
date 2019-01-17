package com.medavarsity.user.medavarsity.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Constants.ConstantVariables;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.ResendOtpResponse;
import com.medavarsity.user.medavarsity.Model.StudentResponse;
import com.medavarsity.user.medavarsity.Model.VerifyOtpModal;
import com.medavarsity.user.medavarsity.Model.VerifyOtpResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterOtp extends AppCompatActivity {

    @BindView(R.id.button_resendOtp)
    Button button_resendOtp;

    @BindView(R.id.button_submitOtp)
    Button button_verifyOtp;

    @BindView(R.id.progressBar_resendOtp)
    ProgressBar progressBar_resendOtp;

    @BindView(R.id.progressBar_verifyOtp)
            ProgressBar progressBar_verifyOtp;

    int otp;
    String contactNumber;
    String studentId;
    ApiInterface api;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_otp);
        ButterKnife.bind(this);

        api = ApiClient.getClient().create(ApiInterface.class);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Enter Otp");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Bundle obj = i.getExtras();
        assert obj != null;
        otp = obj.getInt("otp");
        studentId = obj.getString("studentId");
        contactNumber = obj.getString("contactNo");

        sharedPreferences = getSharedPreferences(ConstantVariables.SHARED_FILE, MODE_PRIVATE);


    }
    @OnClick(R.id.button_resendOtp)
    void resendButtonOnclick(View view) {
       sendOtpAgain();
    }

    private void sendOtpAgain(){
        progressBar_resendOtp.setVisibility(View.VISIBLE);
        button_resendOtp.setVisibility(View.GONE);
        Call<ResendOtpResponse> call = api.resendOtp(studentId);
        call.enqueue(new Callback<ResendOtpResponse>() {
            @Override
            public void onResponse(Call<ResendOtpResponse> call, Response<ResendOtpResponse> response) {
                assert response.body() != null;
                Log.i("resendOtp",response.body().getMessage());
                int status = response.body().getStatus();
                if(status == 1){
                    showToast("Otp Resent");
                } else {
                    showToast("Some error occurred");
                }
                progressBar_resendOtp.setVisibility(View.GONE);
                button_resendOtp.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFailure(Call<ResendOtpResponse> call, Throwable t) {

            }
        });
    }
 private void showToast(String text){
     Toast.makeText(getApplicationContext(),text,Toast.LENGTH_SHORT).show();
 }
   private void verifyOtp(){
        progressBar_verifyOtp.setVisibility(View.VISIBLE);
        button_verifyOtp.setVisibility(View.GONE);
       Call<VerifyOtpResponse> call = api.verifyOtp(studentId,otp);
       call.enqueue(new Callback<VerifyOtpResponse>() {
           @Override
           public void onResponse(@NonNull Call<VerifyOtpResponse> call, @NonNull Response<VerifyOtpResponse> response) {
               assert response.body() != null;
               int status =  response.body().getStatus();
               if(status == 1){
                   showToast("Registered Successfully");
                   Log.i("token",response.body().getToken());

                   GlobalProps.getInstance().authToken = response.body().getToken();
                   setGlobalProps(response.body().getVerifyOtpModal());
                   saveInPref(response.body().getVerifyOtpModal());
                   Intent intent = new Intent(EnterOtp.this,DashBoard.class);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                   startActivity(intent);
               } else {
                   showToast("Verification Failed");
               }
               progressBar_verifyOtp.setVisibility(View.VISIBLE);
               button_verifyOtp.setVisibility(View.GONE);
           }

           @Override
           public void onFailure(@NonNull Call<VerifyOtpResponse> call, @NonNull Throwable t) {
           }
       });
    }

    @OnClick(R.id.button_submitOtp)
    void submitOtpButtonOnclick(View view) {
        verifyOtp();
    }


    @Override
    public void onBackPressed() {
        showAlert(EnterOtp.this,"Registration process will be canceled");

    }

    private void setGlobalProps(VerifyOtpModal verifyOtpModal){
        GlobalProps.getInstance().userProfile = verifyOtpModal.getImageUrl();
        GlobalProps.getInstance().userName = verifyOtpModal.getName();
        GlobalProps.getInstance().userEmail = verifyOtpModal.getEmail();
        GlobalProps.getInstance().userContact = verifyOtpModal.getContactNumber();
        GlobalProps.getInstance().year = verifyOtpModal.getYear();
        Log.i("year",GlobalProps.getInstance().year);
    }

    private void saveInPref(VerifyOtpModal verifyOtpModal) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(verifyOtpModal);
        prefsEditor.putString(ConstantVariables.LOGIN_STUDENT_OBJECT, json);
        prefsEditor.commit();
    }


    public void showAlert(Activity activity, String message) {

        TextView title = new TextView(activity);
        title.setPadding(10, 10, 10, 10);
        title.setTextSize(20);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCustomTitle(title);

        builder.setMessage(message);

        builder.setCancelable(false);
        builder.setNegativeButton("Cancel Registration", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }

        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }




}
