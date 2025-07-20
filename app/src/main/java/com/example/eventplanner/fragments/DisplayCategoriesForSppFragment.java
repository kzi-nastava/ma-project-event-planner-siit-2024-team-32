package com.example.eventplanner.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.EventType;
import com.example.eventplanner.model.ServiceAndProductCategory;

import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DisplayCategoriesForSppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayCategoriesForSppFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DisplayCategoriesForSppFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DisplayCategoriesForSppFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DisplayCategoriesForSppFragment newInstance(String param1, String param2) {
        DisplayCategoriesForSppFragment fragment = new DisplayCategoriesForSppFragment();
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
        View rootView= inflater.inflate(R.layout.fragment_display_categories_for_spp, container, false);
        Call<Collection<ServiceAndProductCategory>> call = ClientUtils.eventService.getCategoriesForSpp(1);
        call.enqueue(new Callback<Collection<ServiceAndProductCategory>>() {
            @Override
            public void onResponse(Call<Collection<ServiceAndProductCategory>> call, Response<Collection<ServiceAndProductCategory>> response) {
                if(response.code()==201 || response.code()==200){
                    Collection<ServiceAndProductCategory> categories = response.body();

                    for (ServiceAndProductCategory c : categories) {
                        TableRow row = new TableRow(requireContext());

                        // Kreiraj ćelije reda
                        TextView name = new TextView(requireContext());
                        name.setText(c.getName());

                        TextView description = new TextView(requireContext());
                        description.setText(c.getDescription());

                        TextView status = new TextView(requireContext());
                        status.setText(c.getServiceAndProductCategoryStatus());

                        // Dodaj ćelije u red
                        row.addView(name);
                        row.addView(description);
                        row.addView(status);

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
            public void onFailure(Call<Collection<ServiceAndProductCategory>> call, Throwable t) {
                Log.d("REZ",t.getMessage()!=null?t.getMessage():"error");
            }

        });
        return rootView;
    }
}