package com.medavarsity.user.medavarsity.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.share.Share;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.HomeModel;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.DashBoard;

import org.w3c.dom.Text;

import retrofit2.Callback;
import retrofit2.Response;

public class AboutFragments extends Fragment {

    TextView textView_subname;

    int subject_id;
    SharedPreferences sharedPreferences;
    View root;

    CommonMethods mCommonMethods;
    ApiInterface apiInterface;

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
            retrofit2.Call<TopicDetailModel> homeModelCall = apiInterface.getTopicDetails(auth_token, subject_id);
            homeModelCall.enqueue(new Callback<TopicDetailModel>() {
                @Override
                public void onResponse(retrofit2.Call<TopicDetailModel> call, Response<TopicDetailModel> response) {

                    System.out.println(response);

                    mCommonMethods.cancelDialog();
                }

                @Override
                public void onFailure(retrofit2.Call<TopicDetailModel> call, Throwable t) {

                    mCommonMethods.cancelDialog();
                }
            });
        }
    }

    private void initialize() {
        mCommonMethods = new CommonMethods(getActivity());
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        textView_subname = (TextView) root.findViewById(R.id.subject_title);
        if (getArguments() != null) {
            subject_id = getArguments().getInt("Key");
        }

        sharedPreferences = getActivity().getSharedPreferences(ConstantVariabls.SHARED_FILE, Context.MODE_PRIVATE);
    }


}
