package com.medavarsity.user.medavarsity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class RegisterScreen extends AppCompatActivity {

    EditText editText_useremail, editText_userpassword, editText_username, editText_contactnum;
    Button btn_SignUp;
    Spinner spinner_collegeSelection, spinner_yearSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        initializeIds();
    }

    private void initializeIds() {
        editText_useremail = (EditText) findViewById(R.id.register_email);
        editText_username = (EditText) findViewById(R.id.register_name);
        editText_userpassword = (EditText) findViewById(R.id.register_password);
        editText_contactnum = (EditText) findViewById(R.id.register_contact);
        btn_SignUp = (Button) findViewById(R.id.btn_Signup);
        spinner_collegeSelection = (Spinner) findViewById(R.id.college_selection);
        spinner_yearSelection = (Spinner) findViewById(R.id.year_selection);
    }
}
