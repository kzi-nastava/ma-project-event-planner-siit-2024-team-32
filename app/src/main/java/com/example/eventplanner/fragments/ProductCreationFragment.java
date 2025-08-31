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
import android.widget.TextView;
import android.widget.Toast;


import com.auth0.android.jwt.JWT;
import com.example.eventplanner.R;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.EventOrganizer;
import com.example.eventplanner.model.Product;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductCreationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProductCreationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductCreationFragment newInstance(String param1, String param2) {
        ProductCreationFragment fragment = new ProductCreationFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_product_creation, container, false);
        Spinner spinnerStatus=rootView.findViewById(R.id.spinner_status);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(), R.array.statusProduct, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerStatus.setAdapter(adapter);

        Spinner spinnerCategory=rootView.findViewById(R.id.spinner_category);
        Call<ArrayList<String>> call = ClientUtils.productService.getCategories();
        call.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {

                if (response.isSuccessful() && response.body() != null) {
                    ArrayList<String> categories = response.body();

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            rootView.getContext(),
                            android.R.layout.simple_spinner_item,
                            categories
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategory.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(rootView.getContext(), "Error with categories loading.", Toast.LENGTH_SHORT).show();
            }
        });

        EditText name=rootView.findViewById(R.id.name);
        EditText description=rootView.findViewById(R.id.description);
        EditText price=rootView.findViewById(R.id.price);
        EditText discount=rootView.findViewById(R.id.discount);
        EditText eventTypes=rootView.findViewById(R.id.eventTypes);
        EditText categoryRec=rootView.findViewById(R.id.catRec);
        Button createButton = rootView.findViewById(R.id.createButton);
        TextView errorMessage = rootView.findViewById(R.id.error_message);

        createButton.setOnClickListener(v-> {
            String name1=name.getText().toString();
            String description1=description.getText().toString();
            String price1=price.getText().toString();
            String discount1=discount.getText().toString();
            String eventTypes1=eventTypes.getText().toString();
            String categoryRec1=categoryRec.getText().toString();
            String status1=spinnerStatus.getSelectedItem().toString();
            String category1=spinnerCategory.getSelectedItem().toString();

            if(!isValidInput(name1)){
                name.setError("This field is required");
            }
            if(!isValidInput(description1)){
                description.setError("This field is required");
            }
            if(!isValidInput(price1)){
                price.setError("This field is required");
            }
            if(!isValidInput(discount1)){
                discount.setError("This field is required");
            }
            if(!isValidInput(eventTypes1)){
                eventTypes.setError("This field is required");
            }
            if(!isValidInput(status1)){
                errorMessage.setText("This field is required");
                errorMessage.setVisibility(View.VISIBLE);
            }

            if(isValidInput(name1,description1,price1,discount1,eventTypes1,status1)){
                String jwtToken = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null);
                JWT decodedJWT = new JWT(jwtToken);
                String userId = decodedJWT.getClaim("id").asString();
                Call<Product> call1 = ClientUtils.productService.addPro(Integer.parseInt(userId),new Product(1,price1,name1,description1,category1,discount1,status1,eventTypes1,categoryRec1));
                call1.enqueue(new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if(response.code()==201 || response.code()==200){
                            Toast.makeText(requireContext(), "Successfully created product!", Toast.LENGTH_SHORT).show();
                            Log.i("rez", String.valueOf(response.body()));
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else if(response.code()==400){
                            Toast.makeText(rootView.getContext(), "Category and category recommendation fields can not be both empty", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code()==409){
                            Toast.makeText(rootView.getContext(), "Category and category recommendation fields can not be both full", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.i("rez", String.valueOf(response.code()));
                            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
                    }

                });
                //Intent intent = new Intent(getActivity(), HomeActivity.class);
                //startActivity(intent);
            }

        });

        return rootView;
    }

    private boolean isValidInput(String input){
        return !input.isEmpty();
    }

    private boolean isValidInput(String input1,String input2,String input3,String input4,String input5,String input6) {
        return !input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty() && !input4.isEmpty() &&
                !input5.isEmpty() && !input6.isEmpty();
    }
}