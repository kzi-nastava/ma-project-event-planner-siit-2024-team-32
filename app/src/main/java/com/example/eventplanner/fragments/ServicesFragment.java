package com.example.eventplanner.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.activities.LoginActivity;
import com.example.eventplanner.activities.SplashScreenActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.clients.ServiceService;
import com.example.eventplanner.dto.Page;
import com.example.eventplanner.model.Service;
import com.example.eventplanner.model.ServiceAndProductCategory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Path;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ServicesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ServicesFragment extends Fragment {
    ArrayList<ServiceAndProductCategory> categories;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public ServicesFragment() { }

    public static ServicesFragment newInstance() {
        ServicesFragment fragment = new ServicesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_services, container, false);

        Button logInButton = rootView.findViewById(R.id.logInButton);
        Button servicesSearchButton = rootView.findViewById(R.id.servicesSearchButton);

        if (requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).contains("JWT_TOKEN")) {
            if (!requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null).equals("")) {
                logInButton.setText(requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("USERNAME", null));
            } else {
                logInButton.setText("Log in");
            }
        } else {
            logInButton.setText("Log in");
        }

        /*
        Call<Page> findCategories = ClientUtils.serviceService.findAllCategories();
        findCategories.enqueue(new Callback<Page>() {
            @Override
            public void onResponse(Call<Page> call, Response<Page> response) {
                categories = (ArrayList<ServiceAndProductCategory>) response.body().getContent();

                Log.i("falafel", String.valueOf(categories));

                ArrayList<String> categoriesStrings = new ArrayList<>();

                for (ServiceAndProductCategory category : categories) {
                    categoriesStrings.add(category.getName());
                }

                Spinner categorySpinner = rootView.findViewById(R.id.servicesSearchCategory);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categoriesStrings);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<Page> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null ? t.getMessage() : "ERROR");
            }
        });
        */

        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });

        servicesSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText servicesSearchName = rootView.findViewById(R.id.servicesSearchName);
                Spinner servicesSearchCategory = rootView.findViewById(R.id.servicesSearchCategory);
                Spinner servicesSearchStatus = rootView.findViewById(R.id.servicesSearchStatus);
                EditText servicesSearchPriceMin = rootView.findViewById(R.id.servicesSearchPriceMin);
                EditText servicesSearchPriceMax = rootView.findViewById(R.id.servicesSearchPriceMax);

                String name = servicesSearchName.getText().toString();
                //String categoryName = servicesSearchCategory.getSelectedItem().toString();
                //String status = servicesSearchStatus.getSelectedItem().toString();
                String priceLow = servicesSearchPriceMin.getText().toString();
                String priceHigh = servicesSearchPriceMax.getText().toString();

                Map<String, String> filters = new HashMap<String, String>();

                if (!name.isEmpty()) filters.put("name", name);
                // category
                // status
                if (!priceLow.isEmpty()) filters.put("priceLow", priceLow);
                if (!priceHigh.isEmpty()) filters.put("priceHigh", priceHigh);
                filters.put("sort", "id");
                filters.put("page", "0");
                filters.put("size", "10");


                // SEARCH RADI, ALI FINDONE SADA NE RADI ???


                Call<Page> call = ClientUtils.serviceService.search(filters);
                call.enqueue(new Callback<Page>() {
                    @Override
                    public void onResponse(Call<Page> call, Response<Page> response) {
                        String jsonString = gson.toJson(response.body());
                        ObjectMapper mapper = new ObjectMapper();

                        try {
                            Page javaObject = mapper.readValue(jsonString, Page.class);

                            for (Object o : javaObject.getContent()) {
                                Service service = mapper.convertValue(o, Service.class);
                                Log.i(String.format("Service ID=%d", service.getId()), service.toString());
                            }
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onFailure(Call<Page> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null ? t.getMessage() : "ERROR");
                    }
                });
            }
        });

        return rootView;
    }
}