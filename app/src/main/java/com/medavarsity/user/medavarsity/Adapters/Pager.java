package com.medavarsity.user.medavarsity.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.fragments.AboutFragments;
import com.medavarsity.user.medavarsity.fragments.ReviewFragments;
import com.medavarsity.user.medavarsity.fragments.VideosFragments;

public class Pager extends FragmentStatePagerAdapter {

    Context context;
    int tabCount;
    Intent intent;

    String seseleccted;
    int selected_subId;

    public Pager(FragmentManager fm, int tabCount, int selected_subId) {
        super(fm);
        this.tabCount = tabCount;
        //  this.context = context;
        this.selected_subId = selected_subId;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AboutFragments tab1 = new AboutFragments();

                Bundle bundle = new Bundle();
                bundle.putInt("Key", selected_subId);
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                VideosFragments tab2 = new VideosFragments();
                return tab2;
            case 2:
                ReviewFragments tab3 = new ReviewFragments();
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
