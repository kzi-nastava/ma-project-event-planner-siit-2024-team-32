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
import com.example.eventplanner.model.Event;
import com.example.eventplanner.model.EventType;
import com.example.eventplanner.model.UpdateEventType;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventTypeUpdateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventTypeUpdateFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventTypeUpdateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventTypeUpdateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventTypeUpdateFragment newInstance(String param1, String param2) {
        EventTypeUpdateFragment fragment = new EventTypeUpdateFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_event_type_update, container, false);

        Spinner spinner=rootView.findViewById(R.id.spinner_status);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(), R.array.status, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        EditText description = rootView.findViewById(R.id.description);
        EditText  category= rootView.findViewById(R.id.recServiceCategory);
        Button updateButton = rootView.findViewById(R.id.updateBtn);

        updateButton.setOnClickListener(v -> {
            String status=spinner.getSelectedItem().toString();
            String description1 = description.getText().toString();
            String categories=category.getText().toString();
            if (isNotEmpty(description1,categories)) {
                Call<UpdateEventType> call = ClientUtils.eventService.updateEt(requireActivity().getIntent().getIntExtra("id",0),new UpdateEventType(1,description1,status,categories));
                call.enqueue(new Callback<UpdateEventType>() {
                    @Override
                    public void onResponse(Call<UpdateEventType> call, Response<UpdateEventType> response) {
                        if(response.code()==201){
                            Toast.makeText(requireContext(), "Event type updated successfully!", Toast.LENGTH_SHORT).show();
                            Log.i("rez", String.valueOf(response.body()));
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else if(response.code()==404){
                            Toast.makeText(requireContext(), "User with this id does not exist", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Log.i("rez", String.valueOf(response.code()));
                            Toast.makeText(requireContext(), "Error!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateEventType> call, Throwable t) {
                        Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
                    }

                });

            } else {
                Toast.makeText(requireContext(), "At least one field must be filled!", Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
    private boolean isNotEmpty(String input1,String input2) {
        return !input1.isEmpty() || !input2.isEmpty();
    }
}