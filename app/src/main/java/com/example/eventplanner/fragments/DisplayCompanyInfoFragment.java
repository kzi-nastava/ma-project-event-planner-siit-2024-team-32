package com.example.eventplanner.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.eventplanner.R;
import com.example.eventplanner.activities.EventDetailsActivity;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.activities.LoginActivity;
import com.example.eventplanner.activities.UpdateCompanyActivity;
import com.example.eventplanner.activities.UpdatePersonalInformationActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.CreatedEvent;
import com.example.eventplanner.model.DisplayEvent;
import com.example.eventplanner.model.EventDecorator;
import com.example.eventplanner.model.EventOrganizer;
import com.example.eventplanner.model.GetProduct;
import com.example.eventplanner.model.GetService;
import com.example.eventplanner.model.ServiceAndProductProvider;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayCompanyInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayCompanyInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DisplayCompanyInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayCompanyInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayCompanyInfoFragment newInstance(String param1, String param2) {
        DisplayCompanyInfoFragment fragment = new DisplayCompanyInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private MaterialCalendarView calendarView;
    private ArrayList<String> reservedEventsDates;
    private LinearLayout eventsContainer;

    private LinearLayout servicesContainer;

    private LinearLayout productsContainer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_display_company_info, container, false);
        String jwtToken = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null);
        JWT decodedJWT = new JWT(jwtToken);
        String userId = decodedJWT.getClaim("id").asString();
        Call<ServiceAndProductProvider> call = ClientUtils.registeredUserService.getSpp(Integer.parseInt(userId));
        call.enqueue(new Callback<ServiceAndProductProvider>() {
            @Override
            public void onResponse(Call<ServiceAndProductProvider> call, Response<ServiceAndProductProvider> response) {
                EditText email= rootView.findViewById(R.id.email_input);
                if (response.isSuccessful()) {
                    email.setText(response.body().getEmail());
                    EditText password=rootView.findViewById(R.id.password_input);
                    password.setText(response.body().getPassword());
                    EditText firstName=rootView.findViewById(R.id.first_name_input);
                    firstName.setText(response.body().getFirstName());
                    EditText surname=rootView.findViewById(R.id.last_name_input);
                    surname.setText(response.body().getLastName());
                    EditText country=rootView.findViewById(R.id.country_input);
                    country.setText(response.body().getLocation().getCountry());
                    EditText city=rootView.findViewById(R.id.city_input);
                    city.setText(response.body().getLocation().getCity());
                    EditText address=rootView.findViewById(R.id.address_input);
                    address.setText(response.body().getLocation().getStreetName());
                    EditText phone=rootView.findViewById(R.id.phone_input);
                    phone.setText(response.body().getPhoneNumber());
                    EditText description=rootView.findViewById(R.id.description_input);
                    description.setText(response.body().getDescription());
                    EditText companyName=rootView.findViewById(R.id.name_input);
                    companyName.setText(response.body().getCompanyName());
                }
                else if(response.code()==404){
                    email.setError("User with given id does not exist.");
                }
                else {
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load service and product provider", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceAndProductProvider> call, Throwable t) {
                Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
            }
        });
        Button updateButton = rootView.findViewById(R.id.updateBtn);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateCompanyActivity.class);
                //intent.putExtra("user",requireActivity().getIntent().getStringExtra("user"));
                startActivity(intent);
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        List<CalendarDay> calendarDays = new ArrayList<>();

        Call<Collection<DisplayEvent>> callReservedEvents = ClientUtils.eventService.getReservedEvent(Integer.parseInt(userId));
        callReservedEvents.enqueue(new Callback<Collection<DisplayEvent>>() {
            @Override
            public void onResponse(Call<Collection<DisplayEvent>> call, Response<Collection<DisplayEvent>> response) {
                if(response.isSuccessful()) {
                    ArrayList<DisplayEvent> reservedEvents = (ArrayList<DisplayEvent>) response.body();
                    reservedEventsDates = new ArrayList<>();
                    for (int i = 0; i < reservedEvents.size(); i++) {
                        reservedEventsDates.add(reservedEvents.get(i).getDateTime().split("T")[0]);
                    }
                    calendarView = rootView.findViewById(R.id.calendarView);
                    displayDatesInRed();
                }
                else{
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load reserved events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Collection<DisplayEvent>> call, Throwable t) {
                Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
            }
        });

        eventsContainer = rootView.findViewById(R.id.favEventsContainer);
        servicesContainer=rootView.findViewById(R.id.favServicesContainer);
        productsContainer=rootView.findViewById(R.id.favProductsContainer);

        Call<ArrayList<CreatedEvent>> callFavEvents = ClientUtils.eventService.getFavEvents(Integer.parseInt(userId));
        callFavEvents.enqueue(new Callback<ArrayList<CreatedEvent>>() {
            @Override
            public void onResponse(Call<ArrayList<CreatedEvent>> call, Response<ArrayList<CreatedEvent>> response) {
                if(response.isSuccessful()){
                    ArrayList<CreatedEvent> favEvents=response.body();
                    for(CreatedEvent favEvent : favEvents){
                        LayoutInflater layoutInflater  = LayoutInflater.from(getContext());
                        View favEventView = layoutInflater.inflate(R.layout.event_card,eventsContainer,false);
                        eventsContainer.addView(favEventView);

                        TextView name=favEventView.findViewById(R.id.event_title);
                        name.setText(favEvent.getName());

                        TextView time=favEventView.findViewById(R.id.time1);
                        time.setText(favEvent.getDateTime());

                        TextView location=favEventView.findViewById(R.id.address);
                        String address=favEvent.getAddress()+", "+favEvent.getCity() + ", "+favEvent.getCountry();
                        location.setText(address);
                    }
                }
                else{
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load favorite events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CreatedEvent>> call, Throwable t) {
                Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
            }
        });

        Call<ArrayList<GetService>> callFavServices = ClientUtils.serviceService.getFavServices(Integer.parseInt(userId));
        callFavServices.enqueue(new Callback<ArrayList<GetService>>() {
            @Override
            public void onResponse(Call<ArrayList<GetService>> call, Response<ArrayList<GetService>> response) {
                if(response.isSuccessful()){
                    ArrayList<GetService> favServices=response.body();
                    for(GetService favService : favServices){
                        LayoutInflater layoutInflater  = LayoutInflater.from(getContext());
                        View favServiceView = layoutInflater.inflate(R.layout.service_and_product_card,servicesContainer,false);
                        servicesContainer.addView(favServiceView);

                        TextView name=favServiceView.findViewById(R.id.serviceOrProductName);
                        name.setText(favService.getName());

                        TextView category=favServiceView.findViewById(R.id.serviceOrProductCategory);
                        category.setText(favService.getServiceAndProductCategory());

                        TextView price=favServiceView.findViewById(R.id.serviceOrProductPrice);
                        String priceString= favService.getPrice() + " EUR";
                        price.setText(priceString);
                    }
                }
                else{
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load favorite services", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetService>> call, Throwable t) {
                Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
            }
        });

        Call<ArrayList<GetProduct>> callFavProducts = ClientUtils.productService.getFavProducts(Integer.parseInt(userId));
        callFavProducts.enqueue(new Callback<ArrayList<GetProduct>>() {
            @Override
            public void onResponse(Call<ArrayList<GetProduct>> call, Response<ArrayList<GetProduct>> response) {
                if(response.isSuccessful()){
                    ArrayList<GetProduct> favProducts=response.body();
                    for(GetProduct favProduct : favProducts){
                        LayoutInflater layoutInflater  = LayoutInflater.from(getContext());
                        View favProductsView = layoutInflater.inflate(R.layout.service_and_product_card,productsContainer,false);
                        productsContainer.addView(favProductsView);

                        TextView name=favProductsView.findViewById(R.id.serviceOrProductName);
                        name.setText(favProduct.getName());

                        TextView category=favProductsView.findViewById(R.id.serviceOrProductCategory);
                        category.setText(favProduct.getServiceAndProductCategory());

                        TextView price=favProductsView.findViewById(R.id.serviceOrProductPrice);
                        String priceString= favProduct.getPrice() + " EUR";
                        price.setText(priceString);
                    }
                }
                else{
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load favorite products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<GetProduct>> call, Throwable t) {
                Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
            }
        });

        Button deactivateButton = rootView.findViewById(R.id.deactivateBtn);

        deactivateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> callDeactivateServiceAndProductProvider = ClientUtils.registeredUserService.deactivateServiceAndProductProvider(Integer.parseInt(userId),true);
                callDeactivateServiceAndProductProvider.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(requireContext(), "Service and product provider successfully deactivated.", Toast.LENGTH_SHORT).show();
                            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("JWT_TOKEN","");
                            editor.apply();
                            Intent intent = new Intent(getActivity(), HomeActivity.class);

                            //intent.putExtra("user", "");
                            startActivity(intent);
                        }
                        else if(response.code()==404){
                            Toast.makeText(requireContext(), "Service and product provider with this id does not exist.", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code()==406){
                            Toast.makeText(requireContext(), "Service and product provider with this id has been already deactivated.", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code()==409){
                            Toast.makeText(requireContext(), "Service and product provider with this id has future reserved services.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.i("rez", String.valueOf(response.code()));
                            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
                    }
                });
            }
        });

        return rootView;
    }

    private void displayDatesInRed() {
        HashSet<CalendarDay> redDates = new HashSet<>();

        for (String dateString : reservedEventsDates) {
            try {
                LocalDate date = LocalDate.parse(dateString);
                redDates.add(CalendarDay.from(date));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                if (redDates.contains(date)) {
                    Intent intent = new Intent(getActivity(), EventDetailsActivity.class);
                    intent.putExtra("year",date.getYear());
                    intent.putExtra("month",date.getMonth());
                    intent.putExtra("day",date.getDay());
                    startActivity(intent);
                    Toast.makeText(getContext(), "Event details loading...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        EventDecorator decorator = new EventDecorator(Color.RED, redDates);
        calendarView.addDecorator(decorator);
    }
}