package com.example.eventplanner.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.EventTypeCreationActivity;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.activities.NavigateToRegisterActivity;
import com.example.eventplanner.activities.RegisterActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.DisplayEvent;
import com.example.eventplanner.model.LogIn;
import com.example.eventplanner.model.Token;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_login, container, false);

        EditText email = rootView.findViewById(R.id.loginEmailEditText);
        EditText password = rootView.findViewById(R.id.loginPasswordEditText);

        Button loginButton = rootView.findViewById(R.id.loginLogInButton);
        loginButton.setOnClickListener(v-> {
            String email1=email.getText().toString();
            String password1=password.getText().toString();
            if (isValidInput(email1,password1) && isValidEmail(email1)) {
                Call<Token> callLogIn = ClientUtils.registeredUserService.logIn(new LogIn(email1,password1));
                callLogIn.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if(response.isSuccessful()) {
                            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("JWT_TOKEN", response.body().getToken());
                            editor.putString("USERNAME", email1);
                            editor.apply();
                            Toast.makeText(requireContext(), "Successfully log in!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            //intent.putExtra("user", response.body().getToken());
                            //intent.putExtra("user",email1);
                            startActivity(intent);
                        }
                        else if(response.code()==409){
                            Toast.makeText(requireContext(), "User with this credentials is deactivated!", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
                    }
                });
            }
            if(!isValidInput(email1)){
                email.setError("This field is required");
            }
            if(!isValidInput(password1)){
                password.setError("This field is required");
            }
            if(isValidInput(email1) && !isValidEmail(email1)){
                email.setError("Invalid email.");
            }
        });

        Button registerButton = rootView.findViewById(R.id.loginRegisterButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NavigateToRegisterActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
    private boolean isValidEmail(String email) {
       return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private boolean isValidInput(String input1,String input2) {
        return !input1.isEmpty() && !input2.isEmpty();
    }
    private boolean isValidInput(String input){
        return !input.isEmpty();
    }
}