package com.medavarsity.user.medavarsity.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Adapters.MySubjectCheckViewAdapter;
import com.medavarsity.user.medavarsity.Constants.ConstantVariables;
import com.medavarsity.user.medavarsity.Global.GlobalProps;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    EditText  etName, etContact, etEmail, etCollege, etYear;
    RadioGroup rg;
    String st = "male";
    RecyclerView recyclerView;
    MySubjectCheckViewAdapter adapter;
    List<String> list = new ArrayList();
    SharedPreferences sharedPreferences;
    TextView toolbar_textView;
    ImageView navigate_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        initializeIds();
        navigate_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, DashBoard.class);
                startActivity(intent);
                finish();
            }
        });
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.male_check:
                        st = "male";
                        break;
                    case R.id.female_check:
                        st = "female";
                        break;
                }
            }
        });
        getExtras();

        recyclerView = findViewById(R.id.subject_check_recycler);
        list.add("Pathology");
        list.add("Orthopadic");
        list.add("Skin");

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MySubjectCheckViewAdapter(this, list);
        recyclerView.setAdapter(adapter);

    }

    private void initializeIds() {
        toolbar_textView = findViewById(R.id.toolbar_textView);
        toolbar_textView.setText("My Profile");
        sharedPreferences = getSharedPreferences(ConstantVariables.SHARED_FILE, Context.MODE_PRIVATE);
        etName = findViewById(R.id.f_name);
        etEmail = findViewById(R.id.et_email);
        etContact = findViewById(R.id.et_phone);
        rg = findViewById(R.id.radio);
        etCollege = findViewById(R.id.et_college);
        etYear = findViewById(R.id.et_year);
        navigate_back = findViewById(R.id.navigate_back);

        etName.setText(GlobalProps.getInstance().userName);
        etEmail.setText(GlobalProps.getInstance().userEmail);
        etContact.setText(GlobalProps.getInstance().userContact);
        etYear.setText(GlobalProps.getInstance().collegeName);
    }

    LoginStudentResponse studentResponse;

    public void getExtras() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantVariables.LOGIN_STUDENT_OBJECT, "");
        studentResponse = gson.fromJson(json, LoginStudentResponse.class);
    }
}
