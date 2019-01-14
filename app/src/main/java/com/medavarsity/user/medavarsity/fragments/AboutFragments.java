package com.medavarsity.user.medavarsity.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.activities.TopicDetails_FeaturesList;

public class AboutFragments extends Fragment {

    TextView textView_subname, textView_sub_Desc, textView_buyBook;
    RelativeLayout featuresListView;
    View root;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.frag_about, container, false);
        featuresListView = root.findViewById(R.id.topicDetails_features_list_button);

        featuresListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),TopicDetails_FeaturesList.class));
            }
        });

        Bundle bundle = this.getArguments();
        assert bundle != null;

        String subject = bundle.getString("subject");
        String subject_desc = bundle.getString("subject_desc");
        initialize(subject,subject_desc);

        return root;

    }


    private void initialize(String subject,String subject_desc) {


        textView_subname = root.findViewById(R.id.subject_title);
        textView_buyBook = root.findViewById(R.id.buy_book);
        textView_subname.setText("Concept of " + subject);
        textView_buyBook.setText("Buy Book on "+ subject);
        textView_sub_Desc = root.findViewById(R.id.subject_desc);
        textView_sub_Desc.setText(subject_desc);

    }
}
