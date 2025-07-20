package com.example.eventplanner.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.Event;
import com.example.eventplanner.model.EventType;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventCreationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventCreationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventCreationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventCreationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventCreationFragment newInstance(String param1, String param2) {
        EventCreationFragment fragment = new EventCreationFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_event_creation, container, false);
        Spinner spinnerPrivacy=rootView.findViewById(R.id.spinner_privacy);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getContext(), R.array.privacy, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPrivacy.setAdapter(adapter);

        Spinner spinnerEventType=rootView.findViewById(R.id.spinner_event_type);
        ClientUtils.eventService.getEventTypes().enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call,Response<ArrayList<String>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Kreiranje adaptera sa dobijenim podacima
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<>(
                            requireContext(),
                            android.R.layout.simple_spinner_item,
                            response.body()
                    );
                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);


                    // Postavljanje adaptera na Spinner
                    spinnerEventType.setAdapter(adapter1);
                } else {
                    Toast.makeText(getContext(), "Failed to load event types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        TextView errorMessage = rootView.findViewById(R.id.error_message);
        TextView errorMessage1 = rootView.findViewById(R.id.error_message1);

        EditText dateTimeEditText = rootView.findViewById(R.id.dateTimeEditText);

        spinnerPrivacy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Ovdje reagujemo na selektovanu stavku
                    errorMessage.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        spinnerEventType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // Ovdje reagujemo na selektovanu stavku
                errorMessage1.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
// Set onClickListener for EditText
        dateTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current date and time
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                // Create DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                // After date is selected, show TimePickerDialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(),
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                                // Combine selected date and time
                                                String selectedDateTime = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear +
                                                        " " + selectedHour + ":" + selectedMinute;
                                                // Display selected date and time in EditText
                                                dateTimeEditText.setText(selectedDateTime);
                                                Toast.makeText(getActivity(), "Date and time choosen!", Toast.LENGTH_SHORT).show();
                                            }
                                        }, hour, minute, true);
                                timePickerDialog.show();
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        EditText name = rootView.findViewById(R.id.name);
        EditText description=rootView.findViewById(R.id.description);
        EditText participants = rootView.findViewById(R.id.participants);
        EditText city = rootView.findViewById(R.id.city);
        EditText address = rootView.findViewById(R.id.address);
        EditText country = rootView.findViewById(R.id.country);
        Button createButton = rootView.findViewById(R.id.btnCreate);

        createButton.setOnClickListener(v -> {
            String name1 = name.getText().toString();
            String description1=description.getText().toString();
            String participants1=participants.getText().toString();
            String city1=city.getText().toString();
            String address1=address.getText().toString();
            String country1=country.getText().toString();
            String privacy = spinnerPrivacy.getSelectedItem().toString();
            String eventType = spinnerEventType.getSelectedItem().toString();
            String dateTime=dateTimeEditText.getText().toString();
            String recEvType="";
            if (!isValidInput(name1)) {
                name.setError("This field is required.");
            }
            if (!isValidInput(participants1)) {
                participants.setError("This field is required.");
            }
            if (!isValidInput(city1)) {
                city.setError("This field is required.");
            }
            if (!isValidInput(address1)) {
                address.setError("This field is required.");
            }
            if (!isValidInput(country1)) {
                country.setError("This field is required.");
            }
            if (!isValidInput(dateTime)) {
                dateTimeEditText.setError("This field is required.");
            }
            if(!isValidInput(privacy)){
                errorMessage.setText("This field is required");
                errorMessage.setVisibility(View.VISIBLE);
            }
            if(!isValidInput(eventType)){
                errorMessage1.setText("This field is required");
                errorMessage1.setVisibility(View.VISIBLE);
            }
            if (isValidInput(name1,participants1,city1,address1,country1,privacy,eventType,dateTime)) {
                Call<Event> call = ClientUtils.eventService.addEv(new Event(1,name1,privacy,description1,country1,city1,address1,participants1,dateTime,eventType,recEvType));
                call.enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        if(response.code()==201){
                            Toast.makeText(requireContext(), "Valid input!", Toast.LENGTH_SHORT).show();
                            Log.i("rez", String.valueOf(response.body()));
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            startActivity(intent);
                        }
                        else if(response.code()==409){
                            name.setError("Event with this name already exists.");
                        }
                        else{
                            Log.i("rez", String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                        Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
                    }

                });
            }
        });

        return rootView;
    }
    private boolean isValidInput(String input1,String input2,String input3,String input4,String input5,String input6,String input7,String input8) {
        return !input1.isEmpty() && !input2.isEmpty() && !input3.isEmpty() && !input4.isEmpty() &&
                !input5.isEmpty() && !input6.isEmpty() && !input7.isEmpty() && !input8.isEmpty();
    }
   private boolean isValidInput(String input){
        return !input.isEmpty();
    }
}