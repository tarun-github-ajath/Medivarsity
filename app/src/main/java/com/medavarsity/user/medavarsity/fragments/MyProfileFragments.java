package com.medavarsity.user.medavarsity.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Adapters.MySubjectCheckViewAdapter;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.StudentResponse;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;
import java.util.List;


public class MyProfileFragments extends Fragment {
    private Toolbar toolbar;
    private TextView tvTitle;
    EditText searchOption, etName, etContact, etEmail, etCollege, etYear;
    RadioGroup rg;
    String st = "male";
    RecyclerView recyclerView;
    MySubjectCheckViewAdapter adapter;
    List<String> list = new ArrayList();

    public static MyProfileFragments newInstance() {
        return new MyProfileFragments();
    }

    SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = getActivity().getSharedPreferences(ConstantVariabls.SHARED_FILE, Context.MODE_PRIVATE);
        View root = inflater.inflate(R.layout.activity_my_profile, container, false);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        tvTitle = (TextView) toolbar.findViewById(R.id.textview_toolbar);
        tvTitle.setText("My Profile");
        tvTitle.setVisibility(View.VISIBLE);
        searchOption = (EditText) toolbar.findViewById(R.id.search_option);
        searchOption.setVisibility(View.INVISIBLE);
        etName = (EditText) root.findViewById(R.id.f_name);
        etEmail = (EditText) root.findViewById(R.id.et_email);
        etContact = (EditText) root.findViewById(R.id.et_phone);
        rg = (RadioGroup) root.findViewById(R.id.radio);
        etCollege = (EditText) root.findViewById(R.id.et_college);
        etYear = (EditText) root.findViewById(R.id.et_year);
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

        recyclerView = (RecyclerView) root.findViewById(R.id.subject_check_recycler);
        list.add("Pathology");
        list.add("Orthopadic");
        list.add("Skin");

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        adapter = new MySubjectCheckViewAdapter(getActivity(), list);
        recyclerView.setAdapter(adapter);

        return root;

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
