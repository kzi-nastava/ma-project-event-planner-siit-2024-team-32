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
import com.example.eventplanner.model.EventType;
import com.example.eventplanner.model.enums.EventTypeStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventTypeCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventTypeCreationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventTypeCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventTypeCreationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventTypeCreationFragment newInstance(String param1, String param2) {
        EventTypeCreationFragment fragment = new EventTypeCreationFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_event_type_creation, container, false);

        EditText name = rootView.findViewById(R.id.typeName);
        EditText description = rootView.findViewById(R.id.description);
        EditText status = rootView.findViewById(R.id.status);
        EditText recServiceCategory=rootView.findViewById(R.id.recServiceCategory);

        Button createButton = rootView.findViewById(R.id.createBtn);

        createButton.setOnClickListener(v -> {
            String name1 = name.getText().toString();
            String description1 = description.getText().toString();
            EventTypeStatus status1 = EventTypeStatus.valueOf(status.getText().toString());
            String recServiceCategory1 = recServiceCategory.getText().toString();

            if(!isValidInput(name1)){
                name.setError("This field is required.");
            }
            if (isValidInput(name1)) {
                String jwtToken = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null);
                JWT decodedJWT = new JWT(jwtToken);
                String userId = decodedJWT.getClaim("id").asString();
                Call<EventType> call = ClientUtils.eventService.addEt(Integer.parseInt(userId),new EventType(1,name1,description1,status1,recServiceCategory1));
                call.enqueue(new Callback<EventType>() {
                    @Override
                    public void onResponse(Call<EventType> call, Response<EventType> response) {
                        if(response.code()==201 || response.code()==200){
                            Toast.makeText(requireContext(), "Event type created successfully!", Toast.LENGTH_SHORT).show();
                            Log.i("rez", String.valueOf(response.body()));
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else if(response.code()==409){
                            name.setError("Event type with this name already exists.");
                        }
                        else if(response.code()==400){
                            Toast.makeText(requireContext(), "Wrong input!", Toast.LENGTH_SHORT).show();                        }
                        else{
                            Log.i("rez", String.valueOf(response.code()));
                            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<EventType> call, Throwable t) {
                        Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
                    }

                });
            }
        });
        return rootView;
    }
    private boolean isValidInput(String input) {
        return !input.isEmpty();
    }
}