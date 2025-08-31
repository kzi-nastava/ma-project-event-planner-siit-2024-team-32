package com.example.eventplanner.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;
import com.example.eventplanner.R;
import com.example.eventplanner.activities.DisplayProductsForSppActivity;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.activities.UpdateProductActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.DisplayProduct;
import com.example.eventplanner.model.ServiceAndProductCategory;
import com.example.eventplanner.model.UpdateProduct;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayProductsForSppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayProductsForSppFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DisplayProductsForSppFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayProductsForSppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayProductsForSppFragment newInstance(String param1, String param2) {
        DisplayProductsForSppFragment fragment = new DisplayProductsForSppFragment();
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
    private int i=0;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_display_products_for_spp, container, false);

        String jwtToken = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null);
        JWT decodedJWT = new JWT(jwtToken);
        String userId = decodedJWT.getClaim("id").asString();
        Spinner spinnerFilter=rootView.findViewById(R.id.spinner_filter);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(), R.array.allFilterOptions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerFilter.setAdapter(adapter);

        Spinner spinnerRelation=rootView.findViewById(R.id.spinner_relation);
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(getContext(), R.array.allRelationsOptions, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerRelation.setAdapter(adapter1);

        Button filterButton = rootView.findViewById(R.id.filterButton);
        EditText valueFilter = rootView.findViewById(R.id.value);
        EditText searchInput = rootView.findViewById(R.id.search);
        Button searchButton = rootView.findViewById(R.id.searchButton);
        TableLayout tableLayout = rootView.findViewById(R.id.tableLayout);


        spinnerFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spinnerFilter.getSelectedItem().toString().equals("category") || spinnerFilter.getSelectedItem().toString().equals("status") || spinnerFilter.getSelectedItem().toString().equals("eventType")) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.equalsOnlyOptions, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerRelation.setAdapter(adapter);
                } else {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.allRelationsOptions, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerRelation.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        /*spinnerRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (spinnerRelation.getSelectedItem().toString().equals("lower") || spinnerRelation.getSelectedItem().toString().equals("lowerEquals") || spinnerRelation.getSelectedItem().toString().equals("greater") || spinnerRelation.getSelectedItem().toString().equals("greaterEquals")) {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.notCSETOnlyOptions, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerFilter.setAdapter(adapter);
                } else {
                    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.allFilterOptions, android.R.layout.simple_spinner_item);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                    spinnerFilter.setAdapter(adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });*/


        // Prikaži sve proizvode prilikom učitavanja
        Call<Collection<DisplayProduct>> call = ClientUtils.productService.getProductsForSpp(Integer.parseInt(userId));
        call.enqueue(new Callback<Collection<DisplayProduct>>() {
            @Override
            public void onResponse(Call<Collection<DisplayProduct>> call, Response<Collection<DisplayProduct>> response) {
                if (response.isSuccessful()) {
                    displayProductsInTable(response.body(), tableLayout);
                } else {
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load products", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Collection<DisplayProduct>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
            }
        });


        filterButton.setOnClickListener(v -> {
            String valueFilter1 = valueFilter.getText().toString().trim();
            if (!isValidInput(valueFilter1)) {
                Toast.makeText(requireContext(), "Please enter value for filtering", Toast.LENGTH_SHORT).show();
                return;
            }
            else if((spinnerFilter.getSelectedItem().equals("price") || spinnerFilter.getSelectedItem().equals("discount")) && !isNumeric(valueFilter1)){
                Toast.makeText(requireContext(), "Value for price and discount must be numeric", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pozovi search endpoint
            Call<Collection<DisplayProduct>> filterCall = ClientUtils.productService.getFilteredProducts(Integer.parseInt(userId),spinnerFilter.getSelectedItem().toString(),spinnerRelation.getSelectedItem().toString(),valueFilter1);
            filterCall.enqueue(new Callback<Collection<DisplayProduct>>() {
                @Override
                public void onResponse(Call<Collection<DisplayProduct>> call, Response<Collection<DisplayProduct>> response) {
                    if (response.isSuccessful()) {
                        tableLayout.removeAllViews(); // Očisti prethodni prikaz
                        displayProductsInTable(response.body(), tableLayout);
                    } else {
                        Log.i("rez", String.valueOf(response.code()));
                        Toast.makeText(requireContext(), "Filter failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Collection<DisplayProduct>> call, Throwable t) {
                    Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Search dugme
        searchButton.setOnClickListener(v -> {
            String query = searchInput.getText().toString().trim();
            if (!isValidInput(query)) {
                Toast.makeText(requireContext(), "Please enter search text", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pozovi search endpoint
            Call<Collection<DisplayProduct>> searchCall = ClientUtils.productService.getSearchedProducts(Integer.parseInt(userId), query);
            searchCall.enqueue(new Callback<Collection<DisplayProduct>>() {
                @Override
                public void onResponse(Call<Collection<DisplayProduct>> call, Response<Collection<DisplayProduct>> response) {
                    if (response.isSuccessful()) {
                        tableLayout.removeAllViews(); // Očisti prethodni prikaz
                        displayProductsInTable(response.body(), tableLayout);
                    } else {
                        Log.i("rez", String.valueOf(response.code()));
                        Toast.makeText(requireContext(), "Search failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Collection<DisplayProduct>> call, Throwable t) {
                    Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });


        return rootView;
    }

    private void displayProductsInTable(Collection<DisplayProduct> products, TableLayout tableLayout) {
        if (products == null) return;

        String jwtToken = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null);
        JWT decodedJWT = new JWT(jwtToken);
        String userId = decodedJWT.getClaim("id").asString();
        // Header
        TableRow rowHeader = new TableRow(requireContext());

        String[] headers = {"Name", "Price", "Description", "Category", "Discount", "Status"};
        for (String header : headers) {
            TextView tv = new TextView(requireContext());
            tv.setText(header);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15f);
            rowHeader.addView(tv);
        }

        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        for (DisplayProduct p : products) {
            TableRow row = new TableRow(requireContext());

            addCell(row, p.getName());
            addCell(row, p.getPrice());
            addCell(row, p.getDescription());
            addCell(row, p.getServiceAndProductCategory());
            addCell(row, p.getDiscount());
            addCell(row, p.getStatus());

            Button delete = new Button(requireContext());
            delete.setText("DELETE");
            delete.setBackgroundColor(Color.parseColor("#EC8305"));
            row.addView(delete);

            Button update = new Button(requireContext());
            update.setText("UPDATE");
            update.setBackgroundColor(Color.parseColor("#EC8305"));
            row.addView(update);

            int productId = p.getId();

            delete.setOnClickListener(v -> {
                Call<Collection<DisplayProduct>> deleteCall =
                        ClientUtils.productService.deleteProductLogically(Integer.parseInt(userId), productId, "");

                deleteCall.enqueue(new Callback<Collection<DisplayProduct>>() {
                    @Override
                    public void onResponse(Call<Collection<DisplayProduct>> call, Response<Collection<DisplayProduct>> response) {
                        if (response.isSuccessful()) {
                            tableLayout.removeView(row);
                            Toast.makeText(requireContext(), "Product deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Collection<DisplayProduct>> call, Throwable t) {
                        Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            });

            update.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), UpdateProductActivity.class);
                intent.putExtra("id", productId);
                startActivity(intent);
            });

            tableLayout.addView(row);
        }
    }

    private void addCell(TableRow row, String text) {
        TextView tv = new TextView(requireContext());
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(15f);
        row.addView(tv);
    }

    private boolean isValidInput(String input){
        return !input.isEmpty();
    }

    private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }
}