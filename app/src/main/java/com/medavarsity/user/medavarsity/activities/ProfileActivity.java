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
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTitle;
    EditText searchOption, etName, etContact, etEmail, etCollege, etYear;
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

        recyclerView = (RecyclerView) findViewById(R.id.subject_check_recycler);
        list.add("Pathology");
        list.add("Orthopadic");
        list.add("Skin");

        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MySubjectCheckViewAdapter(this, list);
        recyclerView.setAdapter(adapter);

    }

    private void initializeIds() {
        toolbar_textView = (TextView) findViewById(R.id.toolbar_textView);
        toolbar_textView.setText("My Profile");
        sharedPreferences = getSharedPreferences(ConstantVariabls.SHARED_FILE, Context.MODE_PRIVATE);
        etName = (EditText) findViewById(R.id.f_name);
        etEmail = (EditText) findViewById(R.id.et_email);
        etContact = (EditText) findViewById(R.id.et_phone);
        rg = (RadioGroup) findViewById(R.id.radio);
        etCollege = (EditText) findViewById(R.id.et_college);
        etYear = (EditText) findViewById(R.id.et_year);
        navigate_back = (ImageView) findViewById(R.id.navigate_back);

    }

    LoginStudentResponse studentResponse;

    public void getExtras() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantVariabls.LOGIN_STUDENT_OBJECT, "");
        studentResponse = gson.fromJson(json, LoginStudentResponse.class);

        // MyPreferences.getActiveInstance(getActivity()).getUserId();
        etName.setText(studentResponse.getStudentResponse().getName());
        etEmail.setText(studentResponse.getStudentResponse().getEmail());
        etContact.setText(studentResponse.getStudentResponse().getContact_no());
    }
}
