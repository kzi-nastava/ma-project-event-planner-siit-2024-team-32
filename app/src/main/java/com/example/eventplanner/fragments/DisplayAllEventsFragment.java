package com.example.eventplanner.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.StatisticsActivity;
import com.example.eventplanner.activities.UpdateProductActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.DisplayEvent;
import com.example.eventplanner.model.DisplayProduct;

import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayAllEventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayAllEventsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DisplayAllEventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayAllEventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayAllEventsFragment newInstance(String param1, String param2) {
        DisplayAllEventsFragment fragment = new DisplayAllEventsFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_display_all_events, container, false);
        TableLayout tableLayout = rootView.findViewById(R.id.tableLayout);

        Call<Collection<DisplayEvent>> call = ClientUtils.eventService.getAllEvents();
        call.enqueue(new Callback<Collection<DisplayEvent>>() {
            @Override
            public void onResponse(Call<Collection<DisplayEvent>> call, Response<Collection<DisplayEvent>> response) {
                if (response.isSuccessful()) {
                    displayEventsInTable(response.body(), tableLayout);
                } else {
                    Toast.makeText(requireContext(), "Failed to load events", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Collection<DisplayEvent>> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
            }
        });
        return rootView;
    }

    private void displayEventsInTable(Collection<DisplayEvent> events, TableLayout tableLayout) {
        if (events == null) return;

        // Header
        TableRow rowHeader = new TableRow(requireContext());

        String[] headers = {"Name", "Privacy", "Description", "Country", "City", "Address","Participants","DateTime","EventType"};
        for (String header : headers) {
            TextView tv = new TextView(requireContext());
            tv.setText(header);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15f);
            rowHeader.addView(tv);
        }

        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        for (DisplayEvent ev: events) {
            TableRow row = new TableRow(requireContext());

            addCell(row, ev.getName());
            addCell(row, ev.getPrivacy());
            addCell(row, ev.getDescription());
            addCell(row, ev.getCountry());
            addCell(row, ev.getCity());
            addCell(row, ev.getAddress());
            addCell(row, ev.getParticipants());
            addCell(row, ev.getDateTime());
            addCell(row, ev.getEventType());


            Button statistics= new Button(requireContext());
            statistics.setText("STATISTICS");
            statistics.setBackgroundColor(Color.parseColor("#EC8305"));
            row.addView(statistics);

            int eventId = ev.getId();
            statistics.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), StatisticsActivity.class);
                intent.putExtra("id", eventId);
                startActivity(intent);
            });

            tableLayout.addView(row);
        }
    }

    private void addCell(TableRow row, String text) {
        TextView tv = new TextView(requireContext());
        tv.setText(text);
        tv.setGravity(Gravity.CENTER);
        tv.setTextSize(15f);
        row.addView(tv);
    }
}