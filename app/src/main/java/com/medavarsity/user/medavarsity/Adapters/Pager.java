package com.medavarsity.user.medavarsity.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.medavarsity.user.medavarsity.Model.TopicDetailModel;

import java.util.ArrayList;
import java.util.List;

public class Pager extends FragmentPagerAdapter {

    String subject_name;
    TopicDetailModel topicDetailModel;
    private List<Fragment> FragmentList = new ArrayList<>(0);
    private List<String> FragmentTitleList = new ArrayList<>(0);


    public Pager(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentList.get(position);
        }

    public void addFragment(Fragment fragment, String title) {
        FragmentList.add(fragment);
        FragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentTitleList.get(position);
    }


    @Override
    public int getCount() {
        return FragmentTitleList.size();
    }
}
