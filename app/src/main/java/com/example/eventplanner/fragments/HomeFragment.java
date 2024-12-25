package com.example.eventplanner.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.DisplayCompanyInfoActivity;
import com.example.eventplanner.activities.DisplayPersonalInfoActivity;
import com.example.eventplanner.activities.EventCreationActivity;
import com.example.eventplanner.activities.EventTypeCreationActivity;
import com.example.eventplanner.activities.EventTypeUpdateActivity;
import com.example.eventplanner.activities.UpdateCompanyActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_home, container, false);

        Button etCreationButton = rootView.findViewById(R.id.etCreation);

        etCreationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventTypeCreationActivity.class);
                startActivity(intent);
            }
        });

        Button etUpdateButton = rootView.findViewById(R.id.etUpdate);

        etUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventTypeUpdateActivity.class);
                startActivity(intent);
            }
        });

        Button eCreationButton = rootView.findViewById(R.id.eCreation);

        eCreationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventCreationActivity.class);
                startActivity(intent);
            }
        });

        Button displayPersonalButton = rootView.findViewById(R.id.displayPersonal);

        displayPersonalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DisplayPersonalInfoActivity.class);
                startActivity(intent);
            }
        });

        Button displayCompanyButton = rootView.findViewById(R.id.displayCompany);

        displayCompanyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DisplayCompanyInfoActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}