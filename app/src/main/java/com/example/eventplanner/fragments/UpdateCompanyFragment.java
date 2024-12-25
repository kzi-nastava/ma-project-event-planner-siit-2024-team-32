package com.example.eventplanner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventplanner.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdateCompanyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdateCompanyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdateCompanyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdateCompanyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdateCompanyFragment newInstance(String param1, String param2) {
        UpdateCompanyFragment fragment = new UpdateCompanyFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_update_company, container, false);

        EditText description = rootView.findViewById(R.id.description);
        EditText country= rootView.findViewById(R.id.country);
        EditText city= rootView.findViewById(R.id.city);
        EditText address= rootView.findViewById(R.id.address);
        EditText phone= rootView.findViewById(R.id.phone);

        Button updateButton = rootView.findViewById(R.id.updateBtn);

        updateButton.setOnClickListener(v -> {
            String description1 = description.getText().toString();
            String country1=country.getText().toString();
            String city1=city.getText().toString();
            String address1=address.getText().toString();
            String phone1=phone.getText().toString();
            if ((isNotEmpty(description1,country1,city1,address1)&& !phone1.isEmpty() && isValidPhoneNumber(phone1))||(!isNotEmpty(description1,country1,city1,address1)&&!phone1.isEmpty() && isValidPhoneNumber(phone1))||(isNotEmpty(description1,country1,city1,address1)&& phone1.isEmpty())) {
                Toast.makeText(requireContext(), "Valid input!", Toast.LENGTH_SHORT).show();
            }
            if(!phone1.isEmpty() && !isValidPhoneNumber(phone1))
            {
                phone.setError("Invalid phone number");
            }
            if (!isNotEmpty(description1,country1,city1,address1) && phone1.isEmpty()){
                Toast.makeText(requireContext(), "At least one field must be filled!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
    private boolean isNotEmpty(String input1,String input2,String input3,String input4) {
        return !input1.isEmpty() || !input2.isEmpty() ||
                !input3.isEmpty() ||!input4.isEmpty();
    }
    public static boolean isValidPhoneNumber(String phoneNumber) {
        // Uklanjamo razmake i crtice iz broja telefona
        String cleanedNumber = phoneNumber.replaceAll("[\\s-]", "");

        // Regularni izraz za validaciju srpskog broja telefona
        String regex = "^(\\+3816\\d{7,8}|06\\d{7,8})$";

        // Provera da li broj odgovara regexu
        return cleanedNumber.matches(regex);
    }
}