package com.medavarsity.user.medavarsity.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.medavarsity.user.medavarsity.R;

public class GiveTest extends AppCompatActivity {

    Toolbar toolbar;
    TextView textView_testname;
    ImageView navigation_back;
    Button btn_give_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_test);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        textView_testname = (TextView) findViewById(R.id.toolbar_textView);
        navigation_back = (ImageView) findViewById(R.id.navigate_back);

        btn_give_test = (Button) findViewById(R.id.btn_give_test);
        btn_give_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GiveTest.this, TestScreen.class));
            }
        });
        navigation_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiveTest.super.onBackPressed();
            }
        });

    }
}
