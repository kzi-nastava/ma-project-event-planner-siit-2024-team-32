package com.example.eventplanner.fragments;

import android.content.Context;
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

import com.auth0.android.jwt.JWT;
import com.example.eventplanner.R;
import com.example.eventplanner.activities.DisplayEventTypesForSppActivity;
import com.example.eventplanner.activities.DisplayPersonalInfoActivity;
import com.example.eventplanner.activities.EventTypeUpdateActivity;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.activities.StatisticsActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.DisplayEvent;
import com.example.eventplanner.model.DisplayEventType;
import com.example.eventplanner.model.EventOrganizer;
import com.example.eventplanner.model.EventType;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayEventTypesForSppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayEventTypesForSppFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DisplayEventTypesForSppFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayEventTypesForSppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayEventTypesForSppFragment newInstance(String param1, String param2) {
        DisplayEventTypesForSppFragment fragment = new DisplayEventTypesForSppFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_display_event_types_for_spp, container, false);
        TableLayout tableLayout = rootView.findViewById(R.id.tableLayout);

        String jwtToken = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null);
        JWT decodedJWT = new JWT(jwtToken);
        String userId = decodedJWT.getClaim("id").asString();
        Call<Collection<DisplayEventType>> call = ClientUtils.eventService.getEventTypesForSpp(Integer.parseInt(userId));
        call.enqueue(new Callback<Collection<DisplayEventType>>() {
            @Override
            public void onResponse(Call<Collection<DisplayEventType>> call, Response<Collection<DisplayEventType>> response) {
                if(response.code()==201 || response.code()==200){
                    displayEventTypesInTable(response.body(), tableLayout);
                }
                else if(response.code()==404){
                    Toast.makeText(requireContext(), "User with this id does not exist", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load event types", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Collection<DisplayEventType>> call, Throwable t) {
                Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
            }

        });

        return rootView;
    }
    private void displayEventTypesInTable(Collection<DisplayEventType> eventTypes, TableLayout tableLayout) {
        if (eventTypes == null) return;

        // Header
        TableRow rowHeader = new TableRow(requireContext());

        String[] headers = {"Name","Description", "ET Status", "RecommendedServiceAndProductCategories"};
        for (String header : headers) {
            TextView tv = new TextView(requireContext());
            tv.setText(header);
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(15f);
            rowHeader.addView(tv);
        }

        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        for (DisplayEventType et: eventTypes) {
            TableRow row = new TableRow(requireContext());

            addCell(row, et.getName());
            addCell(row, et.getDescription());
            addCell(row, et.getEventTypeStatus());
            addCell(row, et.getRecommendedServiceAndProductCategories());

            Button update= new Button(requireContext());
            update.setText("UPDATE");
            update.setBackgroundColor(Color.parseColor("#EC8305"));
            row.addView(update);

            int eventTypeId = et.getId();
            update.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), EventTypeUpdateActivity.class);
                intent.putExtra("id", eventTypeId);
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