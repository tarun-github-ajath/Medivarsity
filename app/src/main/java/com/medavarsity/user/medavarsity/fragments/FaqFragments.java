package com.medavarsity.user.medavarsity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.R;

public class FaqFragments extends Fragment {
    Toolbar toolbar;
    TextView tvTitle;
    EditText searchOption;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_faq, container, false);
        /*toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        tvTitle=(TextView)toolbar.findViewById(R.id.textview_toolbar);
        tvTitle.setText("FAQ");
        tvTitle.setVisibility(View.VISIBLE);
        searchOption=(EditText) toolbar.findViewById(R.id.search_option);
        searchOption.setVisibility(View.INVISIBLE);*/


        return root;

    }
}
