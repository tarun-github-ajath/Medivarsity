package com.medavarsity.user.medavarsity.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.medavarsity.user.medavarsity.Adapters.DailyUpdateAdapter;
import com.medavarsity.user.medavarsity.Adapters.DrawerItemCustomAdapter;
import com.medavarsity.user.medavarsity.Adapters.HomeAdapter;
import com.medavarsity.user.medavarsity.Constants.Config;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.Methods.CommonMethods;
import com.medavarsity.user.medavarsity.Model.DataModel;
import com.medavarsity.user.medavarsity.Model.HomeModel;
import com.medavarsity.user.medavarsity.Model.LoginStudentResponse;
import com.medavarsity.user.medavarsity.Model.PayloadHome;
import com.medavarsity.user.medavarsity.Model.dailyUpdates;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiClient;
import com.medavarsity.user.medavarsity.NetworkCalls.ApiInterface;
import com.medavarsity.user.medavarsity.R;
import com.medavarsity.user.medavarsity.fragments.AboutUsFragment;
import com.medavarsity.user.medavarsity.fragments.FaqFragments;
import com.medavarsity.user.medavarsity.fragments.MyProfileFragments;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity {

    static int PlayerResult = 1003;
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
    RelativeLayout search_layout;
    ApiInterface apiInterface;
    LinearLayout searchBar;
    LinearLayout youtubeScreen;
    /*For youtube */

    RecyclerView subject_recycle, updateRecycle;
    YouTubeThumbnailView youTubeThumbnailView;
    ImageView playBtn;
    RelativeLayout relativeLayoutOverYouTubeThumbnailView;

    CommonMethods mCommonMethods;
    TextView toolbar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        mCommonMethods = new CommonMethods(DashBoard.this);
        sharedPreferences = getSharedPreferences(ConstantVariabls.SHARED_FILE, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(ConstantVariabls.IS_FIRST_TIME, true);
        editor.commit();


        //  getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeScreen()).commit();
        setupToolbar();
        setupDrawerToggle();

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        youTubeThumbnailView = (YouTubeThumbnailView) findViewById(R.id.youtube_player);
        playBtn = (ImageView) findViewById(R.id.btnYoutube_player);
        relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) findViewById(R.id.relative_yotube);

        searchBar = (LinearLayout) findViewById(R.id.search_bar);
        searchBar.setVisibility(View.VISIBLE);
        toolbar_text = (TextView) findViewById(R.id.textview_toolbar);
        toolbar_text.setVisibility(View.GONE);
        subject_recycle = (RecyclerView) findViewById(R.id._recycl);
        updateRecycle = (RecyclerView) findViewById(R.id.daily_update_recycleView);
        // youtubeScreen = (LinearLayout) findViewById(R.id.youtube_screen);
        //youtubeScreen.setVisibility(View.VISIBLE);

        sharedPreferences = this.getSharedPreferences(ConstantVariabls.SHARED_FILE, MODE_PRIVATE);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        subject_recycle.setLayoutManager(layoutManager);
        subject_recycle.setHasFixedSize(true);
        subject_recycle.setNestedScrollingEnabled(false);
        subject_recycle.getLayoutManager().setMeasurementCacheEnabled(false);

        RecyclerView.LayoutManager updateLayoutmanager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        updateRecycle.setLayoutManager(updateLayoutmanager);
        updateRecycle.setHasFixedSize(true);

        updateRecycle.getLayoutManager().setMeasurementCacheEnabled(false);


        final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
            @Override
            public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

            }

            @Override
            public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {


                youTubeThumbnailView.setVisibility(View.VISIBLE);
                relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
            }
        };

        youTubeThumbnailView.initialize(Config.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                youTubeThumbnailLoader.setVideo(Config.YOUTUBE_VIDEO_CODE);
                youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
            }

            @Override
            public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

            }
        });

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = YouTubeStandalonePlayer.createVideoIntent(DashBoard.this, Config.DEVELOPER_KEY, Config.YOUTUBE_VIDEO_CODE);
                startActivityForResult(intent, PlayerResult);
            }
        });
        createDynamicList();
        getExtras();


        if (mCommonMethods.isNetworkAvailable(DashBoard.this)) {
            try {
                mCommonMethods.showCommonDialog(DashBoard.this, "Fetching data...");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                getDashboardData(studentResponse.getAuth_token());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    RecyclerView dailyRecycle;

    private void createDynamicList() {
        LinearLayout viewGroup = (LinearLayout) findViewById(R.id.home_parent);
        dailyRecycle = new RecyclerView(DashBoard.this);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 0, 10, 10);
        dailyRecycle.setLayoutParams(layoutParams);
        viewGroup.addView(dailyRecycle);

    }

    private void getDashboardData(String authToken) {

        if (authToken != null && !authToken.equalsIgnoreCase("")) {
            Call<HomeModel> homeModelCall = apiInterface.getHomeData(authToken);
            homeModelCall.enqueue(new Callback<HomeModel>() {
                @Override
                public void onResponse(Call<HomeModel> call, Response<HomeModel> response) {

                    System.out.println(response);
                    PayloadHome payloadHome = response.body().getPayloadHome();
                    List<dailyUpdates> dailyUpdates = new ArrayList<>();
                    dailyUpdates = payloadHome.getDailyUpdates();

                    saveInPref(payloadHome);
                    setDailyUpdate(/*dailyUpdates*/payloadHome);
                    mCommonMethods.cancelDialog();
                }

                @Override
                public void onFailure(Call<HomeModel> call, Throwable t) {
                    mCommonMethods.cancelDialog();
                }
            });
        }
    }


    private void getExtras() {
        intent = getIntent();
        studentResponse = (LoginStudentResponse) intent.getSerializableExtra("student_info");
        Log.e("studentResponse", ">>>>" + studentResponse);
        readFromPref();
    }

    private void saveInPref(PayloadHome homeModels) {
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(homeModels);
        prefsEditor.putString(ConstantVariabls.Home_Model, json);
        prefsEditor.commit();
    }

    private void setDailyUpdate(/*List<dailyUpdates> dailyUpdate*/ PayloadHome payloadHomes) {
        DailyUpdateAdapter updateAdapter = new DailyUpdateAdapter(this, payloadHomes.getDailyUpdates(), payloadHomes.getSubjects(), Config.DEVELOPER_KEY, "daily");
        updateRecycle.setAdapter(updateAdapter);

        HomeAdapter homeAdapter = new HomeAdapter(DashBoard.this, payloadHomes, "subject");
        subject_recycle.setAdapter(homeAdapter);

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
                //fragment = new HomeScreen();
                intent = new Intent(DashBoard.this, DashBoard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                break;
            case 1:
//                fragment = new MyTopicsFragments();
                intent = new Intent(DashBoard.this, MyTopicsScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                break;
            case 2:
                /*  fragment = new MyProfileFragments();*/
                intent = new Intent(DashBoard.this, ProfileActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                break;
            case 3:
                //fragment = new FaqFragments();

                intent = new Intent(DashBoard.this, FaQView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                break;
            case 4:
                //  fragment = new AboutUsFragment();
                intent = new Intent(DashBoard.this, AboutUsView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(intent);
                break;

            case 5:
                intent = new Intent(DashBoard.this, LoginScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                break;

            default:

                break;
        }
        mDrawerLayout.closeDrawer(rlDrawer);
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
        search_layout = (RelativeLayout) findViewById(R.id.search_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.lv_drawer);
        rlDrawer = (RelativeLayout) findViewById(R.id.rldrawer);
        tvUserName = (TextView) findViewById(R.id.user_name);
        tvUserName.setText(name);
        DataModel[] drawerItem = new DataModel[6];

        drawerItem[0] = new DataModel(R.drawable.home_icon, "Home");
        drawerItem[1] = new DataModel(R.drawable.home_icon, "My Topics");
        drawerItem[2] = new DataModel(R.drawable.home_icon, "My Profile");
        drawerItem[3] = new DataModel(R.drawable.home_icon, "FAQ");
        drawerItem[4] = new DataModel(R.drawable.home_icon, "About Us");
        drawerItem[5] = new DataModel(R.drawable.home_icon, "LogOut");

       /* getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.reveal_icon);
*/

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout.setDrawerListener(mDrawerToggle);


        search_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashBoard.this, SearchScreen.class);
                startActivity(intent);
            }
        });

    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PlayerResult && resultCode == RESULT_OK
                && null != data) {


            data.getDataString();
        }
    }
}
