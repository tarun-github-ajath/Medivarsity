package com.medavarsity.user.medavarsity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Adapters.DrawerItemCustomAdapter;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Model.DataModel;
import com.medavarsity.user.medavarsity.Model.HomeModel;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.StudentResponse;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.fragments.AboutUsFragment;
import com.medavarsity.user.medavarsity.fragments.FaqFragments;
import com.medavarsity.user.medavarsity.fragments.HomeScreen;
import com.medavarsity.user.medavarsity.fragments.MyProfileFragments;
import com.medavarsity.user.medavarsity.fragments.MyTopicsFragments;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    Intent intent;
    LoginStudentResponse studentResponse;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private Toolbar toolbar;
    ActionBarDrawerToggle mDrawerToggle;
    private RelativeLayout rlDrawer;
    private TextView tvUserName;
    private String name;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        sharedPreferences = getSharedPreferences(ConstantVariabls.SHARED_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ConstantVariabls.IS_FIRST_TIME, true);
        editor.commit();



        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeScreen()).commit();
        setupToolbar();
        setupDrawerToggle();

    }


    /*read student info from shared pref*/
    private void readFromPref() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString(ConstantVariabls.LOGIN_STUDENT_OBJECT, "");
        studentResponse = gson.fromJson(json, LoginStudentResponse.class);
        name = studentResponse.getStudentResponse().getName();

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {

        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new HomeScreen();
                break;
            case 1:
                fragment = new MyTopicsFragments();
                break;
            case 2:

                fragment = new MyProfileFragments();
                break;
            case 3:
                fragment = new FaqFragments();

                break;
            case 4:
                fragment = new AboutUsFragment();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            mDrawerLayout.closeDrawer(rlDrawer);
        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.lv_drawer);
        rlDrawer = (RelativeLayout) findViewById(R.id.rldrawer);
        tvUserName = (TextView) findViewById(R.id.user_name);
        tvUserName.setText(name);
        DataModel[] drawerItem = new DataModel[5];

        drawerItem[0] = new DataModel(R.drawable.home_icon, "Home");
        drawerItem[1] = new DataModel(R.drawable.home_icon, "My Topics");
        drawerItem[2] = new DataModel(R.drawable.home_icon, "My Profile");
        drawerItem[3] = new DataModel(R.drawable.home_icon, "FAQ");
        drawerItem[4] = new DataModel(R.drawable.home_icon, "About Us");

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
    }




}
