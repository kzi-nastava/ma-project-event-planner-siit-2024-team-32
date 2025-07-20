package com.example.eventplanner.activities;

import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.eventplanner.R;
import com.example.eventplanner.databinding.ActivityEventTypeUpdateBinding;
import com.example.eventplanner.databinding.ActivityLoginBinding;
import com.example.eventplanner.fragments.EventTypeUpdateFragment;
import com.example.eventplanner.fragments.EventsFragment;
import com.example.eventplanner.fragments.HomeFragment;
import com.example.eventplanner.fragments.LoginFragment;
import com.example.eventplanner.fragments.ProductsFragment;
import com.example.eventplanner.fragments.ServiceAndProductProviderHomeFragment;
import com.example.eventplanner.fragments.ServicesFragment;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
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
            Fragment fragment = new LoginFragment();

            // Kreirajte fragment transaction i dodajte fragment u container
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_seven, fragment);  // R.id.fragment_container je ID container-a
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
        fragmentTransaction.replace(R.id.fragment_container_seven, newFragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Katenda", "LoginActivity onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("Katenda", "LoginActivity onRestart()");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("Katenda", "LoginActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Katenda", "LoginActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Katenda", "LoginActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Katenda", "LoginActivity onDestroy()");
    }
}