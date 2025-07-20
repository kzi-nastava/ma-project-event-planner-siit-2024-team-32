package com.example.eventplanner.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.DisplayEventTypesForSppActivity;
import com.example.eventplanner.activities.DisplayPersonalInfoActivity;
import com.example.eventplanner.activities.EventTypeUpdateActivity;
import com.example.eventplanner.activities.HomeActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.DisplayEventType;
import com.example.eventplanner.model.EventOrganizer;
import com.example.eventplanner.model.EventType;

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
        Call<List<DisplayEventType>> call = ClientUtils.eventService.getEventTypesForSpp(1);
        call.enqueue(new Callback<List<DisplayEventType>>() {
            @Override
            public void onResponse(Call<List<DisplayEventType>> call, Response<List<DisplayEventType>> response) {
                if(response.code()==201 || response.code()==200){
                    List<DisplayEventType> eventTypes = response.body();

                    for (DisplayEventType et : eventTypes) {
                        TableRow row = new TableRow(requireContext());

                        // Kreiraj ćelije reda
                        TextView name = new TextView(requireContext());
                        name.setText(et.getName());

                        TextView description = new TextView(requireContext());
                        description.setText(et.getDescription());

                        TextView status = new TextView(requireContext());
                        status.setText(et.getEventTypeStatus());

                        TextView categories= new TextView(requireContext());
                        categories.setText(et.getRecommendedServiceAndProductCategories());
                        // Dodaj ćelije u red
                        row.addView(name);
                        row.addView(description);
                        row.addView(status);
                        row.addView(categories);
                        // Dodaj red u tabelu
                        TableLayout tableLayout = rootView.findViewById(R.id.tableLayout);

                        tableLayout.addView(row);
                    }
                }
                else if(response.code()==404){
                    Toast.makeText(requireContext(), "User with this id does not exist", Toast.LENGTH_SHORT).show();
                }
                else{
                    Log.i("rez", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<DisplayEventType>> call, Throwable t) {
                Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
            }

        });
        Button updateButton = rootView.findViewById(R.id.updateBtn);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EventTypeUpdateActivity.class);
                startActivity(intent);

            }
        });
        return rootView;
    }
}