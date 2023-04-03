package com.example.zippyrides;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent ihome = new Intent(splash.this, MainActivity.class);
                startActivity(ihome);
                finish();
            }
        }, 4000);

        setContentView(R.layout.activity_splash);
    }
}