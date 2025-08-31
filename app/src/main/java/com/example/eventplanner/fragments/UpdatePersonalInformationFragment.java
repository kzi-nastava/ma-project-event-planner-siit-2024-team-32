package com.example.eventplanner.fragments;

import android.content.Context;
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

import com.auth0.android.jwt.JWT;
import com.example.eventplanner.R;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.EventOrganizer;
import com.example.eventplanner.model.UpdateEventOrganizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UpdatePersonalInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpdatePersonalInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UpdatePersonalInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpdatePersonalInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpdatePersonalInformationFragment newInstance(String param1, String param2) {
        UpdatePersonalInformationFragment fragment = new UpdatePersonalInformationFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_update_personal_information, container, false);

        EditText curPassword=rootView.findViewById(R.id.curPassword);
        EditText newPassword=rootView.findViewById(R.id.newPassword);
        EditText newPasswordConf=rootView.findViewById(R.id.newPasswordConf);
        EditText name = rootView.findViewById(R.id.firstName);
        EditText surname = rootView.findViewById(R.id.lastName);
        EditText country= rootView.findViewById(R.id.country);
        EditText city= rootView.findViewById(R.id.city);
        EditText address= rootView.findViewById(R.id.address);
        EditText phone= rootView.findViewById(R.id.phone);

        Button updateButton = rootView.findViewById(R.id.updateBtn);

        updateButton.setOnClickListener(v -> {
            String curPassword1=curPassword.getText().toString();
            String newPassword1=newPassword.getText().toString();
            String newPasswordConf1=newPasswordConf.getText().toString();
            String name1=name.getText().toString();
            String surname1 = surname.getText().toString();
            String country1=country.getText().toString();
            String city1=city.getText().toString();
            String address1=address.getText().toString();
            String phone1=phone.getText().toString();

            if (!phone1.isEmpty() && !isValidPhoneNumber(phone1)) {
                phone.setError("Invalid phone number");
            }
            if((curPassword1.isEmpty() || newPassword1.isEmpty() || newPasswordConf1.isEmpty()) && !isEmpty(curPassword1,newPassword1,newPasswordConf1)){
                Toast.makeText(requireContext(), "If one password field is filled, all three must be filled!", Toast.LENGTH_SHORT).show();
            }
            if(!newPassword1.isEmpty() && !newPasswordConf1.isEmpty() && !newPassword1.equals(newPasswordConf1)){
                newPasswordConf.setError("Must be equal to new password field.");
            }
            if(!isNotEmpty(curPassword1,newPassword1,newPasswordConf1,name1,surname1,country1,city1,address1,phone1)){
                Toast.makeText(requireContext(), "At least one field must be filled", Toast.LENGTH_SHORT).show();
            }
            if((isNotEmpty(curPassword1,newPassword1,newPasswordConf1,name1,surname1,country1,city1,address1,phone1) && phone1.isEmpty() && isEmpty(curPassword1,newPassword1,newPasswordConf1))||
               (isNotEmpty(curPassword1,newPassword1,newPasswordConf1,name1,surname1,country1,city1,address1,phone1) && !phone1.isEmpty() && isValidPhoneNumber(phone1) && isEmpty(curPassword1,newPassword1,newPasswordConf1))||
               (isNotEmpty(curPassword1,newPassword1,newPasswordConf1,name1,surname1,country1,city1,address1,phone1) && phone1.isEmpty() && isNotEmpty(curPassword1,newPassword1,newPasswordConf1) && newPassword1.equals(newPasswordConf1))||
               (isNotEmpty(curPassword1,newPassword1,newPasswordConf1,name1,surname1,country1,city1,address1,phone1) && !phone1.isEmpty() && isValidPhoneNumber(phone1) && isNotEmpty(curPassword1,newPassword1,newPasswordConf1) && newPassword1.equals(newPasswordConf1))){
                String jwtToken = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null);
                JWT decodedJWT = new JWT(jwtToken);
                String userId = decodedJWT.getClaim("id").asString();
                Call<UpdateEventOrganizer> call = ClientUtils.registeredUserService.updateEo(Integer.parseInt(userId),new UpdateEventOrganizer(1,newPassword1,name1,surname1,phone1,city1,address1,country1));
                call.enqueue(new Callback<UpdateEventOrganizer>() {
                    @Override
                    public void onResponse(Call<UpdateEventOrganizer> call, Response<UpdateEventOrganizer> response) {
                        if(response.code()==201 || response.code()==200){
                            Toast.makeText(requireContext(), "Successfully updated event organizer!", Toast.LENGTH_SHORT).show();
                            Log.i("rez", String.valueOf(response.body()));
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else if(response.code()==404){
                            Toast.makeText(requireContext(), "User with this id does not exist.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.i("rez", String.valueOf(response.code()));
                            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateEventOrganizer> call, Throwable t) {
                        Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
                    }

                });
            }
        });
        return rootView;
    }
    private boolean isNotEmpty(String input1,String input2, String input3){
        return !input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty();
    }
    private boolean isEmpty(String input1,String input2,String input3) {
        return input1.isEmpty() && input2.isEmpty() &&
                input3.isEmpty();
    }
    private boolean isNotEmpty(String input1,String input2,String input3,String input4,
                               String input5,String input6,String input7,String input8,
                               String input9) {
        return !input1.isEmpty() || !input2.isEmpty() || !input3.isEmpty() ||
                !input4.isEmpty() || !input5.isEmpty() || !input6.isEmpty() ||
                !input7.isEmpty() || !input8.isEmpty() || !input9.isEmpty();
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