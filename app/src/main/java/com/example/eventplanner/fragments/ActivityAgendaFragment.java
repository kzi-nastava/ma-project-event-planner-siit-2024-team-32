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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.Activity;
import com.example.eventplanner.model.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActivityAgendaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityAgendaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActivityAgendaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityAgendaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityAgendaFragment newInstance(String param1, String param2) {
        ActivityAgendaFragment fragment = new ActivityAgendaFragment();
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
    private LinearLayout activityContainer;
    private final ArrayList<View> activities = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_activity_agenda, container, false);
        View firstActivityView=rootView.findViewById(R.id.firstActivity);
        EditText timeFrom1=firstActivityView.findViewById(R.id.timeFrom);
        timeFrom1.setText(requireActivity().getIntent().getStringExtra("datetime"));
        timeFrom1.setEnabled(false);
        EditText timeTo1=firstActivityView.findViewById(R.id.timeTo);
        timeTo1.setOnClickListener(new View.OnClickListener() {
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
                                                timeTo1.setText(selectedDateTime);
                                                Toast.makeText(getActivity(), "Date and time choosen!", Toast.LENGTH_SHORT).show();
                                            }
                                        }, hour, minute, true);
                                timePickerDialog.show();
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        activities.add(firstActivityView);

        activityContainer = rootView.findViewById(R.id.activityContainer);

        Button addActivity=rootView.findViewById(R.id.addActivityButton);
        Button finishButton=rootView.findViewById(R.id.finishButton);

        addActivity.setOnClickListener(v -> {
            LayoutInflater layoutInflater  = LayoutInflater.from(getContext());
            View activityView = layoutInflater.inflate(R.layout.activity, activityContainer, false);

            Button deleteButton = activityView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(vv -> {

                activityContainer.removeView(activityView);
                activities.remove(activityView);
            });

            // Dodaj ga u container
            activityContainer.addView(activityView);
            activities.add(activityView);

            EditText timeFrom=activityView.findViewById(R.id.timeFrom);
            timeFrom.setOnClickListener(new View.OnClickListener() {
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
                                                    timeFrom.setText(selectedDateTime);
                                                    Toast.makeText(getActivity(), "Date and time choosen!", Toast.LENGTH_SHORT).show();
                                                }
                                            }, hour, minute, true);
                                    timePickerDialog.show();
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                }
            });

            EditText timeTo=activityView.findViewById(R.id.timeTo);
            timeTo.setOnClickListener(new View.OnClickListener() {
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
                                                    timeTo.setText(selectedDateTime);
                                                    Toast.makeText(getActivity(), "Date and time choosen!", Toast.LENGTH_SHORT).show();
                                                }
                                            }, hour, minute, true);
                                    timePickerDialog.show();
                                }
                            }, year, month, day);
                    datePickerDialog.show();
                }
            });
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        ArrayList<Activity> activities1=new ArrayList<>();
        finishButton.setOnClickListener(v -> {
            final boolean[] p={true};
            try {
                for (int i = 0; i < activities.size(); i++) {
                    if(activities.size()==1){
                        function(i,formatter,activities1,p);
                        break;
                    }
                    else if(activities.size()!=1 && i==activities.size()-1){
                        function(i,formatter,activities1,p);
                        break;
                    }
                    else if (isValidInput(activities.get(i))) {
                        if (LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeFrom)).getText().toString(), formatter).isBefore(LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeTo)).getText().toString(), formatter)) &&
                                (i < activities.size() - 1 && isValidInputTimeFrom(activities.get(i+1)) && LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeTo)).getText().toString(), formatter).isBefore(LocalDateTime.parse(((EditText) activities.get(i + 1).findViewById(R.id.timeFrom)).getText().toString(), formatter)))) {
                            Activity activity = new Activity(1, ((EditText) activities.get(i).findViewById(R.id.name)).getText().toString(), ((EditText) activities.get(i).findViewById(R.id.description)).getText().toString(), ((EditText) activities.get(i).findViewById(R.id.timeFrom)).getText().toString(), ((EditText) activities.get(i).findViewById(R.id.timeTo)).getText().toString(), ((EditText) activities.get(i).findViewById(R.id.description)).getText().toString());
                            activities1.add(activity);
                        }
                        else if(isValidInputTimeFrom(activities.get(i+1))==false){
                            p[0] = false;
                            if (LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeFrom)).getText().toString(), formatter).isAfter(LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeTo)).getText().toString(), formatter))) {
                                p[0] = false;
                                Toast.makeText(requireContext(), "End time must be greater than beginning time", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (i != activities.size() - 1 && LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeTo)).getText().toString(), formatter).isAfter(LocalDateTime.parse(((EditText) activities.get(i + 1).findViewById(R.id.timeFrom)).getText().toString(), formatter))) {
                            p[0] = false;
                            Toast.makeText(requireContext(), "End time of first previous activity must be lower or equal to the beginning time of next activity", Toast.LENGTH_SHORT).show();
                            break;
                        } else if (LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeFrom)).getText().toString(), formatter).isAfter(LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeTo)).getText().toString(), formatter))) {
                            p[0] = false;
                            Toast.makeText(requireContext(), "End time must be greater than beginning time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        p[0] = false;
                        Toast.makeText(requireContext(), "Form inputs are not valid. Please enter valid values.", Toast.LENGTH_SHORT).show();
                    }
                }
              /*  if (activities.size() == 1) {
                    if (isValidInput(activities.get(activities.size() - 1))) {
                        if (LocalDateTime.parse(((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeFrom)).getText().toString(), formatter).isBefore(LocalDateTime.parse(((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeTo)).getText().toString(), formatter))) {
                            Activity activity = new Activity(1, ((EditText) activities.get(activities.size() - 1).findViewById(R.id.name)).getText().toString(), ((EditText) activities.get(activities.size() - 1).findViewById(R.id.description)).getText().toString(), ((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeFrom)).getText().toString(), ((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeTo)).getText().toString(), ((EditText) activities.get(activities.size() - 1).findViewById(R.id.description)).getText().toString());
                            activities1.add(activity);
                        } else if (LocalDateTime.parse(((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeFrom)).getText().toString(), formatter).isAfter(LocalDateTime.parse(((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeTo)).getText().toString(), formatter))) {
                            p[0] = false;
                            Toast.makeText(requireContext(), "End time must be greater than beginning time", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        p[0] = false;
                        Toast.makeText(requireContext(), "Form inputs are not valid. Please enter valid values.", Toast.LENGTH_SHORT).show();
                    }
                }*/

                if (p[0] == true) {
                    for (Activity activity : activities1) {
                        Call<Activity> activityCall = ClientUtils.eventService.createActivity(activity);
                        activityCall.enqueue(new Callback<Activity>() {
                            @Override
                            public void onResponse(Call<Activity> call, Response<Activity> response) {
                                if (response.code() == 201 || response.code() == 200) {
                                    p[0] = true;
                                    Log.i("rez", String.valueOf(response.body()));
                                } else {
                                    p[0] = false;
                                    Log.i("rez", String.valueOf(response.code()));
                                }
                            }

                            @Override
                            public void onFailure(Call<Activity> call, Throwable t) {
                                p[0] = false;
                                Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                            }

                        });
                    }
                    if (p[0] == true) {
                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                        Toast.makeText(requireContext(), "Activities successfully created", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                }
            }
            catch (Exception e) {
                Log.d("GRESKA", "Greska" + ":" + e);
            }
        });

        return rootView;
    }

    private void function(Integer i,DateTimeFormatter formatter,ArrayList<Activity> activities1,final boolean[] p){
        if (isValidInput(activities.get(i))) {
            if (LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeFrom)).getText().toString(), formatter).isBefore(LocalDateTime.parse(((EditText) activities.get(i).findViewById(R.id.timeTo)).getText().toString(), formatter))) {
                Activity activity = new Activity(1, ((EditText) activities.get(activities.size() - 1).findViewById(R.id.name)).getText().toString(), ((EditText) activities.get(activities.size() - 1).findViewById(R.id.description)).getText().toString(), ((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeFrom)).getText().toString(), ((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeTo)).getText().toString(), ((EditText) activities.get(activities.size() - 1).findViewById(R.id.description)).getText().toString());
                activities1.add(activity);
            } else if (LocalDateTime.parse(((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeFrom)).getText().toString(), formatter).isAfter(LocalDateTime.parse(((EditText) activities.get(activities.size() - 1).findViewById(R.id.timeTo)).getText().toString(), formatter))) {
                p[0] = false;
                Toast.makeText(requireContext(), "End time must be greater than beginning time", Toast.LENGTH_SHORT).show();
            }
        } else {
            p[0] = false;
            Toast.makeText(requireContext(), "Form inputs are not valid. Please enter valid values.", Toast.LENGTH_SHORT).show();
        }
    }
    private boolean isValidInputTimeFrom(View view){
        boolean isValid = true;
        EditText timeFrom = view.findViewById(R.id.timeFrom);
        if (timeFrom.getText().toString().trim().isEmpty()) {
            isValid = false;
        }
        return isValid;
    }
    private boolean isValidInput(View view) {
        boolean isValid = true;

        EditText name = view.findViewById(R.id.name);
        EditText place = view.findViewById(R.id.place);
        EditText timeFrom = view.findViewById(R.id.timeFrom);
        EditText timeTo = view.findViewById(R.id.timeTo);
        EditText description = view.findViewById(R.id.description);

        if (name.getText().toString().trim().isEmpty()) {
            name.setError("Name required");
            isValid = false;
        }

        if (place.getText().toString().trim().isEmpty()) {
            place.setError("Place required");
            isValid = false;
        }

        if (timeFrom.getText().toString().trim().isEmpty()) {
            timeFrom.setError("Time From required");
            isValid = false;
        }

        if (timeTo.getText().toString().trim().isEmpty()) {
            timeTo.setError("Time To required");
            isValid = false;
        }

        if (description.getText().toString().trim().isEmpty()) {
            description.setError("Description required");
            isValid = false;
        }

        return isValid;
    }
}