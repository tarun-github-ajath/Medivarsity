package com.medavarsity.user.medavarsity.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.R;

public class FaQView extends AppCompatActivity {
    ImageView navigate_back;
    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        initializeIds();
    }

    private void initializeIds() {
        navigate_back = (ImageView) findViewById(R.id.navigate_back);
        toolbarText = (TextView) findViewById(R.id.toolbar_textView);
        toolbarText.setText("FAQ");

        navigate_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FaQView.this, DashBoard.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
