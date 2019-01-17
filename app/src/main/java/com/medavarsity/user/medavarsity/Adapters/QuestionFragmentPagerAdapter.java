package com.medavarsity.user.medavarsity.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class QuestionFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> tabTitles = new ArrayList<>();

    public QuestionFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position+1);
    }

    public void addFragment(Fragment fragment, String tabTitle) {
        fragments.add(fragment);
        tabTitles.add(tabTitle);
    }
}
