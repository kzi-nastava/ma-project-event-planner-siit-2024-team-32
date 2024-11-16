package com.example.eventplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;
import com.example.eventplanner.databinding.ActivityLoginBinding;
import com.example.eventplanner.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d("Katenda", "RegisterActivity onCreate()");
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();


    }

    public void onRadioButtonClicked ( View view ) {
        if (((RadioButton) findViewById(R.id.rb1)).isChecked()){
            Intent intent = new Intent(RegisterActivity.this, RegisterEventOrganizerActivity.class);
            startActivity(intent);
            finish();
        }
        else if(((RadioButton) findViewById(R.id.rb2)).isChecked()){
            Intent intent = new Intent(RegisterActivity.this, RegisterServiceAndProductProviderActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Katenda", "RegisterActivity onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Katenda", "RegisterActivity onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Katenda", "RegisterActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Katenda", "RegisterActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Katenda", "RegisterActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Katenda", "RegisterActivity onDestroy()");
    }
}