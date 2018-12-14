package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.TopicDetailModel;
import com.medavarsity.user.medavarsity.fragments.AboutFragments;
import com.medavarsity.user.medavarsity.fragments.ReviewFragments;
import com.medavarsity.user.medavarsity.fragments.VideosFragments;

import java.io.Serializable;

public class Pager extends FragmentStatePagerAdapter {

    Context context;
    int tabCount;
    String subject_name;
    TopicDetailModel topicDetailModel;

    public Pager(FragmentManager fm, int tabCount, TopicDetailModel topicDetailModel, String subject_name) {
        super(fm);
        this.tabCount = tabCount;
        this.subject_name = subject_name;
        this.topicDetailModel = topicDetailModel;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AboutFragments tab1 = new AboutFragments();

                Bundle bundle = new Bundle();
                bundle.putSerializable(ConstantVariabls.SELECTED_SUB_DETAIL, topicDetailModel.getPayloadTopics().get(0).getSubjectDetails());
                bundle.putString("Sub", subject_name);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                VideosFragments tab2 = new VideosFragments();
                Bundle bundle1 = new Bundle();
                bundle1.putSerializable("Topic_detail", topicDetailModel);
                tab2.setArguments(bundle1);
                return tab2;
            case 2:
                ReviewFragments tab3 = new ReviewFragments();
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable(ConstantVariabls.SELCTED_TOPIC_DETAIL, topicDetailModel);
                tab3.setArguments(bundle2);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
