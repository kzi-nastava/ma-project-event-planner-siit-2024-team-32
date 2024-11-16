package com.example.eventplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.eventplanner.R;

public class RegisterEventOrganizerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_event_organizer);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Log.d("Katenda", "RegisterODActivity onCreate()");
        Toast.makeText(this, "onCreate()", Toast.LENGTH_SHORT).show();

        Button registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterEventOrganizerActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Katenda", "RegisterODActivity onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Katenda", "RegisterODActivity onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Katenda", "RegisterODActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Katenda", "RegisterODActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Katenda", "RegisterODActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Katenda", "RegisterODActivity onDestroy()");
    }
}