package com.medavarsity.user.medavarsity.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.medavarsity.user.medavarsity.Model.DataModel;
import com.medavarsity.user.medavarsity.R;

public class BaseActivity extends Activity {


    protected FrameLayout frameLayout;
    protected ListView mDrawerList;
    protected static int position;
    private static boolean isLaunch = true;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);


        DataModel[] drawerItem = new DataModel[5];

        drawerItem[0] = new DataModel(R.drawable.home_icon, "Home");
        drawerItem[1] = new DataModel(R.drawable.home_icon, "My Topics");
        drawerItem[2] = new DataModel(R.drawable.home_icon, "My Profile");
        drawerItem[3] = new DataModel(R.drawable.home_icon, "FAQ");
        drawerItem[4] = new DataModel(R.drawable.home_icon, "About Us");
    }
}
