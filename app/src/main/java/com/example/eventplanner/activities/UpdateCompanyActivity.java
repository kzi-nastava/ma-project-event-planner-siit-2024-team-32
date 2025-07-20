package com.example.eventplanner.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eventplanner.R;
import com.example.eventplanner.databinding.ActivityUpdateCompanyBinding;
import com.example.eventplanner.databinding.ActivityUpdatePersonalInformationBinding;
import com.example.eventplanner.fragments.EventsFragment;
import com.example.eventplanner.fragments.HomeFragment;
import com.example.eventplanner.fragments.ProductsFragment;
import com.example.eventplanner.fragments.ServiceAndProductProviderHomeFragment;
import com.example.eventplanner.fragments.ServicesFragment;
import com.example.eventplanner.fragments.UpdateCompanyFragment;
import com.example.eventplanner.fragments.UpdatePersonalInformationFragment;

import java.util.Objects;

public class UpdateCompanyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityUpdateCompanyBinding binding = ActivityUpdateCompanyBinding.inflate(getLayoutInflater());
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
            Fragment fragment = new UpdateCompanyFragment();

            // Kreirajte fragment transaction i dodajte fragment u container
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_three, fragment);  // R.id.fragment_container je ID container-a
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
        fragmentTransaction.replace(R.id.fragment_container_three, newFragment);
        fragmentTransaction.commit();
    }
}