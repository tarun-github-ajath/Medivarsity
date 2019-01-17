package com.medavarsity.user.medavarsity.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Adapters.MySubjectCheckViewAdapter;
import com.medavarsity.user.medavarsity.Constants.ConstantVariables;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;


public class MyProfileFragments extends Fragment {

    EditText etName, etContact, etEmail, etCollege, etYear;
    RadioGroup rg;
    String st = "male";
    RecyclerView recyclerView;
    MySubjectCheckViewAdapter adapter;
    List<String> list = new ArrayList();




    public static MyProfileFragments newInstance() {
        return new MyProfileFragments();
    }

    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        sharedPreferences = Objects.requireNonNull(getActivity()).getSharedPreferences(ConstantVariables.SHARED_FILE, Context.MODE_PRIVATE);
        View root = inflater.inflate(R.layout.activity_my_profile, container, false);
        etName = root.findViewById(R.id.f_name);
        etEmail = root.findViewById(R.id.et_email);
        etContact = root.findViewById(R.id.et_phone);
        rg = root.findViewById(R.id.radio);
        etYear = root.findViewById(R.id.et_year);
//        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.male_check:
//                        st = "male";
//                        break;
//                    case R.id.female_check:
//                        st = "female";
//                        break;
//                }
//            }
//        });
        getExtras();

        recyclerView = root.findViewById(R.id.subject_check_recycler);
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
        String json = sharedPreferences.getString(ConstantVariables.LOGIN_STUDENT_OBJECT, "");
        studentResponse = gson.fromJson(json, LoginStudentResponse.class);
    }
}
