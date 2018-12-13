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
import com.medavarsity.user.medavarsity.Model.PayloadTopics;
import com.medavarsity.user.medavarsity.Model.SubjectDetails;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.DashBoard;

import org.w3c.dom.Text;

import retrofit2.Callback;
import retrofit2.Response;

public class AboutFragments extends Fragment {

    TextView textView_subname, textView_sub_Desc, textView_buyBook;

    int subject_id;
    SharedPreferences sharedPreferences;
    View root;

    CommonMethods mCommonMethods;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_about, container, false);

        initialize();


        return root;

    }


    private void initialize() {


        textView_subname = (TextView) root.findViewById(R.id.subject_title);
        textView_buyBook = (TextView) root.findViewById(R.id.buy_book);
        /*if (getArguments() != null) {
            subject_id = getArguments().getInt("Key");
            textView_subname.setText(getArguments().getString(ConstantVariabls.SELECTED_SUB_NAME));
            textView_buyBook.setText(getArguments().getString(ConstantVariabls.SELECTED_SUB_NAME));
        }
*/
        textView_sub_Desc = (TextView) root.findViewById(R.id.subject_desc);

        if (getArguments() != null) {
            SubjectDetails subjectDetails = (SubjectDetails) getArguments().getSerializable(ConstantVariabls.SELECTED_SUB_DETAIL);
            String subject = getArguments().getString("Sub");
            textView_sub_Desc.setText(subjectDetails.getSubject_desc());
            textView_subname.setText(subject);
            textView_buyBook.setText("Buy Book on Concept of" + " " + subject);
            System.out.println(subjectDetails);
        }

    }


    private void setSelectedSubDisc(SubjectDetails subjectDetails) {
        textView_sub_Desc.setText(subjectDetails.getSubject_desc());
    }

}
