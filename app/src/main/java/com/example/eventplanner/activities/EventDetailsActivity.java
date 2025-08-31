package com.example.eventplanner.activities;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.eventplanner.R;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.databinding.ActivityEventCreationBinding;
import com.example.eventplanner.databinding.ActivityEventDetailsBinding;
import com.example.eventplanner.fragments.EventCreationFragment;
import com.example.eventplanner.fragments.EventDetailsFragment;
import com.example.eventplanner.fragments.EventsFragment;
import com.example.eventplanner.fragments.HomeFragment;
import com.example.eventplanner.fragments.ProductsFragment;
import com.example.eventplanner.fragments.ServiceAndProductProviderHomeFragment;
import com.example.eventplanner.fragments.ServicesFragment;
import com.example.eventplanner.model.DisplayEvent;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventDetailsActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityEventDetailsBinding binding = ActivityEventDetailsBinding.inflate(getLayoutInflater());
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
            Fragment fragment = new EventDetailsFragment();

            // Kreirajte fragment transaction i dodajte fragment u container
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container_event_details, fragment);  // R.id.fragment_container je ID container-a
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
        fragmentTransaction.replace(R.id.fragment_container_event_details, newFragment);
        fragmentTransaction.commit();
    }
}