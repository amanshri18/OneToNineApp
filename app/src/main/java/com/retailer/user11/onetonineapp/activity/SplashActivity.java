package com.retailer.user11.onetonineapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Handler;

import com.retailer.user11.onetonineapp.R;
import com.retailer.user11.onetonineapp.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sessionManager = new SessionManager(this);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                sessionManager.checkLogin();
            }
        }, SPLASH_TIME_OUT);
    }

}
