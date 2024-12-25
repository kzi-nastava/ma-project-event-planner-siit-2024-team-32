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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eventplanner.R;
import com.example.eventplanner.databinding.ActivityRegisterBinding;
import com.example.eventplanner.databinding.ActivityRegisterServiceAndProductProviderBinding;
import com.example.eventplanner.fragments.EventsFragment;
import com.example.eventplanner.fragments.HomeFragment;
import com.example.eventplanner.fragments.ProductsFragment;
import com.example.eventplanner.fragments.RegisterFragment;
import com.example.eventplanner.fragments.RegisterServiceAndProductProviderFragment;
import com.example.eventplanner.fragments.ServiceAndProductProviderHomeFragment;
import com.example.eventplanner.fragments.ServicesFragment;

import java.util.Objects;

public class RegisterServiceAndProductProviderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityRegisterServiceAndProductProviderBinding binding = ActivityRegisterServiceAndProductProviderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String status = "default";

        try {
            Bundle bundle = getIntent().getExtras();
            status = bundle.getString("status");
        }
        catch(Exception e) {
            status = "default";
        }

        String finalStatus = status;
        if (savedInstanceState == null) {
            // Kreirajte novi fragment
            Fragment fragment = new RegisterServiceAndProductProviderFragment();

            // Kreirajte fragment transaction i dodajte fragment u container
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_nine, fragment);  // R.id.fragment_container je ID container-a
            transaction.commit();  // Ovaj korak je ključan za primenu izmene
        }
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigationBarHome) {
                try {
                    if (Objects.equals(finalStatus, "loggedIn")) {
                        replaceFragment(new ServiceAndProductProviderHomeFragment());
                    } else {
                        replaceFragment(new HomeFragment());
                    }
                } catch(Exception e) {
                    replaceFragment(new HomeFragment());
                }
            } else if (itemId == R.id.navigationBarEvents) {
                replaceFragment(new EventsFragment());
            } else if (itemId == R.id.navigationBarServices) {
                replaceFragment(new ServicesFragment());
            } else if (itemId == R.id.navigationBarProducts) {
                replaceFragment(new ProductsFragment());
            }

            return true;
        });
    }
    private void replaceFragment(Fragment newFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_nine, newFragment);
        fragmentTransaction.commit();
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