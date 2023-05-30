package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.test.databinding.SplashBinding;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    SplashBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        SharedPreferences sharedPreferences = getSharedPreferences("GymDuo", Context.MODE_PRIVATE);
        String appKey = sharedPreferences.getString("APPKEY", null);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (appKey == null)
                {
                    Intent i = new Intent(Splash.this, UserInformationScreen.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(Splash.this, ProgramInformation.class);
                    startActivity(i);
                }
            }
        }, 3000);
    }
}
