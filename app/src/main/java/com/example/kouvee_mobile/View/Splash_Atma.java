package com.example.kouvee_mobile.View;


import android.content.Intent;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.kouvee_mobile.R;


public class Splash_Atma extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_atma);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent login = new Intent(Splash_Atma.this, Activity_Login.class);
                startActivity(login);
            }
        }, 1000);

    }
}

