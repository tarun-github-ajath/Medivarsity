package com.medavarsity.user.medavarsity.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.R;

public class MyTopicsFragments extends Fragment {
    Toolbar toolbar;
    TextView toolbar_text;
    LinearLayout searchBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frag_my_topics, container, false);


        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        searchBar = (LinearLayout) toolbar.findViewById(R.id.search_bar);
        searchBar.setVisibility(View.GONE);
        toolbar_text = (TextView) toolbar.findViewById(R.id.textview_toolbar);
        toolbar_text.setVisibility(View.VISIBLE);
        toolbar_text.setText("My Topics");
      /*  toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        tvTitle=(TextView)toolbar.findViewById(R.id.textview_toolbar);
        tvTitle.setText("My Topics");
        tvTitle.setVisibility(View.VISIBLE);
        searchOption=(EditText) toolbar.findViewById(R.id.search_option);
        searchOption.setVisibility(View.INVISIBLE);*/


        return root;

    }
}
