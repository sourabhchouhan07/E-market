package com.coolascode.emarket.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.coolascode.emarket.R;

public class SplashScreenActivity extends AppCompatActivity {
    private final static int SPLASH_SCREEN_TIME_OUT=2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {



            @Override

            public void run() {

                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                startActivity(intent);

                // close this activity

                finishAffinity();

            }

        }, SPLASH_SCREEN_TIME_OUT);
    }
}