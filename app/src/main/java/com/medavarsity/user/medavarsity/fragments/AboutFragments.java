package com.medavarsity.user.medavarsity.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.share.Share;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.DashBoard;

import org.w3c.dom.Text;

public class AboutFragments extends Fragment {

    TextView textView_subname;

    int subject_id;
    SharedPreferences sharedPreferences;
    View root;

    CommonMethods mCommonMethods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_about, container, false);

        initialize();
        LoginStudentResponse loginStudentResponse = CommonMethods.readLoginUser(sharedPreferences);


        if (mCommonMethods.isNetworkAvailable(getActivity())) {
            try {
                mCommonMethods.showCommonDialog(getActivity(), "Fetching data...");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                getSubjectDeatils(loginStudentResponse.getAuth_token(), subject_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return root;

    }

    private void getSubjectDeatils(String auth_token, int subject_id) {
        if (auth_token != null && !auth_token.equalsIgnoreCase("")) {

        }
    }

    private void initialize() {
        mCommonMethods = new CommonMethods(getActivity());

        textView_subname = (TextView) root.findViewById(R.id.subject_title);
        if (getArguments() != null) {
            subject_id = getArguments().getInt("Key");
        }

        sharedPreferences = getActivity().getSharedPreferences(ConstantVariabls.SHARED_FILE, Context.MODE_PRIVATE);
    }


}
