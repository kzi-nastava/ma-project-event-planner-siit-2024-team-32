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
import android.widget.Toast;

import com.example.eventplanner.model.EventOrganizer;
import com.example.eventplanner.R;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.clients.ClientUtils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_register, container, false);

        EditText email=rootView.findViewById(R.id.email);
        EditText password=rootView.findViewById(R.id.password);
        EditText passwordRewrite=rootView.findViewById(R.id.passwordRewrite);
        EditText name=rootView.findViewById(R.id.name);
        EditText surname=rootView.findViewById(R.id.surname);
        EditText town=rootView.findViewById(R.id.town);
        EditText country=rootView.findViewById(R.id.country);
        EditText address=rootView.findViewById(R.id.address);
        EditText phone=rootView.findViewById(R.id.phone);

        Button registerButton = rootView.findViewById(R.id.RegisterButton);

        registerButton.setOnClickListener(v-> {
            String email1=email.getText().toString();
            String password1=password.getText().toString();
            String passwordRewrite1=passwordRewrite.getText().toString();
            String name1=name.getText().toString();
            String surname1=surname.getText().toString();
            String town1=town.getText().toString();
            String country1=country.getText().toString();
            String address1=address.getText().toString();
            String phone1=phone.getText().toString();

            if(!isValidInput(email1)){
                email.setError("This field is required");
            }
            if(isValidInput(email1) && !isValidEmail(email1)){
                email.setError("Invalid email");
            }
            if(!isValidInput(password1)){
                password.setError("This field is required");
            }
            if(!isValidInput(passwordRewrite1)){
                passwordRewrite.setError("This field is required");
            }
            if(isValidInput(password1) && isValidInput(passwordRewrite1) && !password1.equals(passwordRewrite1)){
                passwordRewrite.setError("Must be equal to password");
            }
            if(!isValidInput(name1)){
                name.setError("This field is required");
            }
            if(!isValidInput(surname1)){
                surname.setError("This field is required");
            }
            if(!isValidInput(town1)){
                town.setError("This field is required");
            }
            if(!isValidInput(country1)){
                country.setError("This field is required");
            }
            if(!isValidInput(address1)){
                address.setError("This field is required");
            }
            if(!isValidInput(phone1)){
                phone.setError("This field is required");
            }
            if(isValidInput(phone1) && !isValidPhoneNumber(phone1)){
                phone.setError("Invalid phone number.");
            }
            if(isValidInput(email1,password1,passwordRewrite1,name1,surname1,town1,country1,address1,phone1) && isValidEmail(email1) && isValidPhoneNumber(phone1) && password1.equals(passwordRewrite1)){
                Call<EventOrganizer> call = ClientUtils.registeredUserService.add(new EventOrganizer(1,email1,password1,name1,surname1,phone1,town1,address1,country1));
                call.enqueue(new Callback<EventOrganizer>() {
                    @Override
                    public void onResponse(Call<EventOrganizer> call, Response<EventOrganizer> response) {
                        if(response.code()==201){
                            Toast.makeText(requireContext(), "Successfully registered event organizer!", Toast.LENGTH_SHORT).show();
                            Log.i("rez", String.valueOf(response.body()));
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else if(response.code()==409){
                            email.setError("User with this email already exists.");
                        }
                        else{
                            Log.i("rez", String.valueOf(response.code()));
                            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EventOrganizer> call, Throwable t) {
                        Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
                    }

                });
                //Intent intent = new Intent(getActivity(), HomeActivity.class);
                //startActivity(intent);
            }

        });

        return rootView;
    }
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidInput(String input){
        return !input.isEmpty();
    }
    private boolean isValidInput(String input1,String input2,String input3,String input4,String input5,String input6,String input7,String input8,String input9) {
        return !input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty() && !input4.isEmpty() &&
                !input5.isEmpty() && !input6.isEmpty() && !input7.isEmpty() && !input8.isEmpty() && !input9.isEmpty();
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