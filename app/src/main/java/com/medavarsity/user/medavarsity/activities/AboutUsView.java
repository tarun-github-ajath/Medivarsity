package com.medavarsity.user.medavarsity.activities;

import android.view.View;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.R;

public class AboutUsView extends AppCompatActivity {

    ImageView navigate_back;
    TextView toolbar_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initialize();
    }

    private void initialize() {
        navigate_back = findViewById(R.id.navigate_back);
        toolbar_text = findViewById(R.id.toolbar_textView);
        toolbar_text.setText("About Us");

        navigate_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
