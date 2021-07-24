package com.learntodroid.androidqrcodescanner.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.learntodroid.androidqrcodescanner.MainActivity;
import com.learntodroid.androidqrcodescanner.R;
import com.learntodroid.androidqrcodescanner.Util.Util;

public class SplashActivity extends AppCompatActivity {

    Handler startHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            Intent intent = new Intent(SplashActivity.this, Util.loadSetting(SplashActivity.this)?OnboardActivity.class:MainActivity.class);
            startActivity(intent);
            finish();
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startHandler.sendEmptyMessageDelayed(0,4000);
    }
}