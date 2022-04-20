package com.example.embeddedprogrammingassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int SPLASH_SCREEN = 3500;

        LottieAnimationView lottie = findViewById(R.id.lottieSplashLogo);

        TextView appName = findViewById(R.id.tvAppName);
        TextView appDesc = findViewById(R.id.tvAppDesc);
        TextView developed = findViewById(R.id.tvDeveloped);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_splash);

        lottie.setAnimation(animation);
        appName.setAnimation(animation);
        appDesc.setAnimation(animation);
        developed.setAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}