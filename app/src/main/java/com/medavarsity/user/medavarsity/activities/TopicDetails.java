package com.medavarsity.user.medavarsity.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.Adapters.Pager;
import com.medavarsity.user.medavarsity.Constants.ConstantVariabls;
import com.medavarsity.user.medavarsity.R;

public class TopicDetails extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {

    TabLayout tabLayout;
    TextView textView;
    ImageView navigation_back;

    Intent intent;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_details);


        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("About"));
        tabLayout.addTab(tabLayout.newTab().setText("Videos"));
        tabLayout.addTab(tabLayout.newTab().setText("Reviews"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        textView = (TextView) findViewById(R.id.selected_subject);
        navigation_back = (ImageView) findViewById(R.id.navigate_back);

        intent = getIntent();
        String selectedSub = intent.getStringExtra(ConstantVariabls.SELECTED_SUBJECT_NAME).equalsIgnoreCase("") ? "" :
                intent.getStringExtra(ConstantVariabls.SELECTED_SUBJECT_NAME);
        int sub_id = intent.getIntExtra(ConstantVariabls.SELECTED_SUB_ID, 0);

        textView.setText("Concept of " + " " + selectedSub);


        navigation_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TopicDetails.this, DashBoard.class);
                startActivity(intent);
                finish();
            }
        });
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount(), /*selectedSub*/ sub_id);

        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        //Adding onTabSelectedListener to swipe views
        tabLayout.setOnTabSelectedListener(this);

    }


    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {


    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
