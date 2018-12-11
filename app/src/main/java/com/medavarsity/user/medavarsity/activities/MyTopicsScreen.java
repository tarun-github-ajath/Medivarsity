package com.medavarsity.user.medavarsity.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.medavarsity.user.medavarsity.R;

public class MyTopicsScreen extends AppCompatActivity {

    ImageView navigate_back;
    TextView toolbar_textView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_my_topics);
        initializeIds();
    }

    private void initializeIds() {
        navigate_back = (ImageView) findViewById(R.id.navigate_back);
        toolbar_textView = (TextView) findViewById(R.id.toolbar_textView);
        toolbar_textView.setText("My Topics");
        recyclerView = (RecyclerView) findViewById(R.id.my_topic_recycler);
        navigate_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
