package com.medavarsity.user.medavarsity.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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

    @BindView(R.id.profile_image)
    de.hdodenhof.circleimageview.CircleImageView profileImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        ButterKnife.bind(this);
        initializeIds();
        navigate_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getExtras();

        recyclerView = findViewById(R.id.subject_check_recycler);

    }

    private void initializeIds() {
        toolbar_textView = findViewById(R.id.toolbar_textView);
        toolbar_textView.setText("My Profile");
        sharedPreferences = getSharedPreferences(ConstantVariables.SHARED_FILE, Context.MODE_PRIVATE);
        etName = findViewById(R.id.f_name);
        etEmail = findViewById(R.id.et_email);
        etContact = findViewById(R.id.et_phone);
        rg = findViewById(R.id.radio);
        etYear = findViewById(R.id.et_year);
        navigate_back = findViewById(R.id.navigate_back);

        etName.setText(GlobalProps.getInstance().userName);
        etEmail.setText(GlobalProps.getInstance().userEmail);
        etContact.setText(GlobalProps.getInstance().userContact);

        if(GlobalProps.getInstance().year != null){
            etYear.setText(GlobalProps.getInstance().year);
        }

        if(!GlobalProps.getInstance().userProfile.equals("")){
            Picasso.with(getApplicationContext()).load(GlobalProps.getInstance().userProfile).into(profileImage);
        }
    }

    LoginStudentResponse studentResponse;

    public void getExtras() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantVariables.LOGIN_STUDENT_OBJECT, "");
        studentResponse = gson.fromJson(json, LoginStudentResponse.class);
    }
}
