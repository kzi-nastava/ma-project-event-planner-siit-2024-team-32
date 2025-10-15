package com.example.eventplanner.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.databinding.ActivityLoginBinding;
import com.example.eventplanner.databinding.ActivitySplashScreenBinding;

import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    int SPLASH_TIME_OUT = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ClientUtils.init(getApplicationContext());
        Log.d("Katenda", "SplashScreenActivity onCreate()");
        //Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
        SharedPreferences sharedPreferences = this.getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("JWT_TOKEN");
        editor.apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Katenda", "SplashScreenActivity onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Katenda", "SplashScreenActivity onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Katenda", "SplashScreenActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Katenda", "SplashScreenActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Katenda", "SplashScreenActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Katenda", "SplashScreenActivity onDestroy()");
    }
}