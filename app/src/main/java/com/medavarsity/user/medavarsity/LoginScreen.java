package com.medavarsity.user.medavarsity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginScreen extends AppCompatActivity {

    EditText editText_useremail, editText_userpassword;
    Button btn_sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        initializeIds();
    }

    private void initializeIds() {
        editText_useremail = (EditText) findViewById(R.id.email_address);
        editText_userpassword = (EditText) findViewById(R.id.password);
        btn_sign = (Button) findViewById(R.id.sign_in);
    }


}
