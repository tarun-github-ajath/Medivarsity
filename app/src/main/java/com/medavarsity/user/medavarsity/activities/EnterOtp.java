package com.medavarsity.user.medavarsity.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.ResendOtpResponse;
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
           public void onResponse(Call<VerifyOtpResponse> call, Response<VerifyOtpResponse> response) {
               assert response.body() != null;
               int status =  response.body().getStatus();
               if(status == 1){
                   showToast("Registered Successfully");
                   startActivity(new Intent(EnterOtp.this,DashBoard.class));
               } else {
                   showToast("Verification Failed");
               }

               progressBar_verifyOtp.setVisibility(View.VISIBLE);
               button_verifyOtp.setVisibility(View.GONE);
           }

           @Override
           public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {

           }
       });
    }

    @OnClick(R.id.button_submitOtp)
    void submitOtpButtonOnclick(View view) {
        verifyOtp();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        showAlert(EnterOtp.this,"Registration process will be canceled");

    }


    public void showAlert(Activity activity, String message) {

        TextView title = new TextView(activity);
        title.setPadding(10, 10, 10, 10);
        title.setTextSize(20);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // builder.setTitle("Title");
        builder.setCustomTitle(title);
        // builder.setIcon(R.drawable.alert_36);

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
