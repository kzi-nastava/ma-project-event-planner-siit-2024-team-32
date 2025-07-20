package com.example.eventplanner.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.RegisterActivity;
import com.example.eventplanner.activities.RegisterServiceAndProductProviderActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigateToRegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigateToRegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NavigateToRegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigateToRegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigateToRegisterFragment newInstance(String param1, String param2) {
        NavigateToRegisterFragment fragment = new NavigateToRegisterFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_navigate_to_register, container, false);

        Button registerButton = rootView.findViewById(R.id.RegisterEoButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button registerButton1 = rootView.findViewById(R.id.RegisterSppButton);

        registerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterServiceAndProductProviderActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}