package com.example.eventplanner.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.UpdatePersonalInformationActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.EventOrganizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayPersonalInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayPersonalInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DisplayPersonalInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayPersonalInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayPersonalInfoFragment newInstance(String param1, String param2) {
        DisplayPersonalInfoFragment fragment = new DisplayPersonalInfoFragment();
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
        View rootView =inflater.inflate(R.layout.fragment_display_personal_info, container, false);
        Call<EventOrganizer> call = ClientUtils.registeredUserService.getEo(2);
        call.enqueue(new Callback<EventOrganizer>() {
            @Override
            public void onResponse(Call<EventOrganizer> call, Response<EventOrganizer> response) {
                EditText email= rootView.findViewById(R.id.email_input);
                if (response.isSuccessful()) {
                    email.setText(response.body().getEmail());
                    EditText password=rootView.findViewById(R.id.password_input);
                    password.setText(response.body().getPassword());
                    EditText name=rootView.findViewById(R.id.name_input);
                    name.setText(response.body().getFirstName());
                    EditText surname=rootView.findViewById(R.id.surname_input);
                    surname.setText(response.body().getLastName());
                    EditText country=rootView.findViewById(R.id.country_input);
                    country.setText(response.body().getCountry());
                    EditText city=rootView.findViewById(R.id.city_input);
                    city.setText(response.body().getCity());
                    EditText address=rootView.findViewById(R.id.address_input);
                    address.setText(response.body().getAddress());
                    EditText phone=rootView.findViewById(R.id.phone_input);
                    phone.setText(response.body().getPhoneNumber());
                }
                else if(response.code()==404){
                    email.setError("User with given id does not exist.");
                }
                else {
                    Log.i("rez", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<EventOrganizer> call, Throwable t) {
                Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
            }
        });

        Button updateButton = rootView.findViewById(R.id.updateBtn);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdatePersonalInformationActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}