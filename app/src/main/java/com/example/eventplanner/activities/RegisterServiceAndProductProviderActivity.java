package com.example.eventplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;

public class RegisterServiceAndProductProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_service_and_product_provider);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button registerButton = findViewById(R.id.RegisterButtonn);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterServiceAndProductProviderActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Katenda", "RegisterPUPActivity onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Katenda", "RegisterPUPActivity onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Katenda", "RegisterPUPActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Katenda", "RegisterPUPActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Katenda", "RegisterPUPActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Katenda", "RegisterPUPActivity onDestroy()");
    }
}