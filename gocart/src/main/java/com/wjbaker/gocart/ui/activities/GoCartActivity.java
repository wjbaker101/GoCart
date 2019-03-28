package com.wjbaker.gocart.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.wjbaker.gocart.GoCartApp;
import com.wjbaker.gocart.R;

public abstract class GoCartActivity extends AppCompatActivity {

    protected GoCartApp goCartApp;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getGoCartApp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.getGoCartApp();
    }

    private void getGoCartApp() {
        this.goCartApp = (GoCartApp)this.getApplication();
    }
}
