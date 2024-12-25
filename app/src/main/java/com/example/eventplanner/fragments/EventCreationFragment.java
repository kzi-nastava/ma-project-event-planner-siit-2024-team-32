package com.example.eventplanner.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.eventplanner.R;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

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
        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(getContext(), R.array.eventType, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerEventType.setAdapter(adapter1);

        EditText dateTimeEditText = rootView.findViewById(R.id.dateTimeEditText);

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
        EditText participants = rootView.findViewById(R.id.participants);
        EditText city = rootView.findViewById(R.id.city);
        EditText address = rootView.findViewById(R.id.address);
        EditText country = rootView.findViewById(R.id.country);
        Spinner spinnerprivacy = rootView.findViewById(R.id.spinner_privacy);
        Spinner spinnereventtype = rootView.findViewById(R.id.spinner_privacy);



        Button createButton = rootView.findViewById(R.id.btnCreate);

        createButton.setOnClickListener(v -> {
            String name1 = name.getText().toString();
            String participants1=participants.getText().toString();
            String city1=city.getText().toString();
            String address1=address.getText().toString();
            String country1=country.getText().toString();
            String privacy = spinnerprivacy.getSelectedItem().toString();
            String eventType = spinnereventtype.getSelectedItem().toString();
            String dateTime=dateTimeEditText.getText().toString();
            if (isValidInput(name1,participants1,city1,address1,country1,privacy,eventType,dateTime)) {
                Toast.makeText(requireContext(), "Valid input!", Toast.LENGTH_SHORT).show();
            }
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