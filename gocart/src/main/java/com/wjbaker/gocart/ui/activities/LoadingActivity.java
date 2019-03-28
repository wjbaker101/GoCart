package com.wjbaker.gocart.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.wjbaker.gocart.R;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_loading);

        // Immediately load the first activity
        Intent intent = new Intent(this, ShoppingActivity.class);
        this.startActivity(intent);

        this.finish();
    }
}
