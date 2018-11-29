package com.medavarsity.user.medavarsity.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.medavarsity.user.medavarsity.R;
/*import com.medavarsity.user.medavarsity.utils.MyPreferences;*/


public class MyProfileFragments extends Fragment {
   private Toolbar toolbar;
   private TextView tvTitle;
   EditText searchOption,etName,etContact,etEmail;
   RadioGroup rg;
   String st="male";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_my_profile, container, false);
        toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        tvTitle=(TextView)toolbar.findViewById(R.id.textview_toolbar);
        tvTitle.setText("My Profile");
        tvTitle.setVisibility(View.VISIBLE);
        searchOption=(EditText) toolbar.findViewById(R.id.search_option);
        searchOption.setVisibility(View.INVISIBLE);
        etName=(EditText)root.findViewById(R.id.f_name);
        etEmail=(EditText)root.findViewById(R.id.et_email);
        etContact=(EditText)root.findViewById(R.id.et_phone);
        rg = (RadioGroup)root. findViewById(R.id.radio);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.male_check:
                        st="male";
                        break;
                    case R.id.female_check:
                        st="female";
                        break;
                }
            }
        });
        getExtras();

        return root;

    }

    public void getExtras() {
       /*     MyPreferences.getActiveInstance(getActivity()).getUserId();
            etName.setText(MyPreferences.getActiveInstance(getActivity()).getuserName());
            etEmail.setText(MyPreferences.getActiveInstance(getActivity()).getEmailId());
            etContact.setText( MyPreferences.getActiveInstance(getActivity()).getMobileNo());*/

    }
}
