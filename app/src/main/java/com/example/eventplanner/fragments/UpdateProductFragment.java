package com.example.eventplanner.fragments;

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
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.Product;
import com.example.eventplanner.model.UpdateProduct;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateProductFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateProductFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateProductFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateProductFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateProductFragment newInstance(String param1, String param2) {
        UpdateProductFragment fragment = new UpdateProductFragment();
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
        View rootView=  inflater.inflate(R.layout.fragment_update_product, container, false);
        Spinner spinnerStatus=rootView.findViewById(R.id.spinner_status);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(), R.array.statusProductUpdate, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerStatus.setAdapter(adapter);

        EditText name=rootView.findViewById(R.id.name);
        EditText description=rootView.findViewById(R.id.description);
        EditText price=rootView.findViewById(R.id.price);
        EditText discount=rootView.findViewById(R.id.discount);
        EditText eventTypes=rootView.findViewById(R.id.eventTypes);
        Button updateButton = rootView.findViewById(R.id.updateButton);

        updateButton.setOnClickListener(v-> {
            String name1=name.getText().toString();
            String description1=description.getText().toString();
            String price1=price.getText().toString();
            String discount1=discount.getText().toString();
            String eventTypes1=eventTypes.getText().toString();
            String status1=spinnerStatus.getSelectedItem().toString();
            if(isValidInput(name1) || isValidInput(description1) || isValidInput(price1) || isValidInput(discount1) || isValidInput(eventTypes1) || isValidInput(status1)){
                Call<UpdateProduct> updatecall = ClientUtils.productService.updateProduct(requireActivity().getIntent().getIntExtra("id",0),new UpdateProduct(1,price1,name1,description1,discount1,status1,eventTypes1));
                updatecall.enqueue(new Callback<UpdateProduct>() {
                    @Override
                    public void onResponse(Call<UpdateProduct> call, Response<UpdateProduct> response) {
                        if(response.code()==201 || response.code()==200){
                            Toast.makeText(requireContext(), "Successfully updated product!", Toast.LENGTH_SHORT).show();
                            Log.i("rez", String.valueOf(response.body()));
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else if(response.code()==404){
                            Toast.makeText(rootView.getContext(), "Product not found", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.i("rez", String.valueOf(response.code()));
                            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateProduct> call, Throwable t) {
                        Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
                    }

                });
                //Intent intent = new Intent(getActivity(), HomeActivity.class);
                //startActivity(intent);
            }
            else{
                Toast.makeText(rootView.getContext(), "At least one field must be full", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    private boolean isValidInput(String input){
        return !input.isEmpty();
    }

}