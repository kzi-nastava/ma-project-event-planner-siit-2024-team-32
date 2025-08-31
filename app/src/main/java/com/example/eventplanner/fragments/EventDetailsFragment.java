package com.example.eventplanner.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Environment;
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
import com.example.eventplanner.activities.StatisticsActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.DisplayEvent;
import com.example.eventplanner.model.DisplayProduct;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.Address;
import android.location.Geocoder;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDetailsFragment extends Fragment implements OnMapReadyCallback {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventDetailsFragment newInstance(String param1, String param2) {
        EventDetailsFragment fragment = new EventDetailsFragment();
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
    private LatLng location;
    public void getLatLngFromAddress(String street, String city, String country,GoogleMap googleMap) {
        Geocoder geocoder = new Geocoder(requireContext(), Locale.getDefault()); // 'this' refers to the Context
        String addressString = street + ", " + city + ", " + country;
        try {
            List<Address> addresses=geocoder.getFromLocationName(addressString, 1);
            if (addresses != null && !addresses.isEmpty()) {
                Address returnedAddress = addresses.get(0);
                double latitude = returnedAddress.getLatitude();
                double longitude = returnedAddress.getLongitude();
                location=new LatLng(latitude, longitude);
            }
            googleMap.addMarker(new MarkerOptions().position(location).title("Marker in" + street));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10));
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception (e.g., network error, service not available)
        }
    }

    Geocoder.GeocodeListener geocodeListener = new Geocoder.GeocodeListener() {
        @Override
        public void onGeocode(@NonNull List<Address> addresses) {
            // Handle the list of Address objects here
            if (!addresses.isEmpty()) {
                Address firstAddress = addresses.get(0);
                double latitude = firstAddress.getLatitude();
                double longitude = firstAddress.getLongitude();
                location=new LatLng(latitude,longitude);
                Log.d("REZULTAT","Latitude: "+latitude + ", longitude: "+ longitude);

                // Do something with the coordinates
            } else {
                // No address found for the given location name
            }
        }

        @Override
        public void onError(String errorMessage) {
            // Handle any errors during geocoding
            Log.e("Geocoder", "Geocoding error: " + errorMessage);
        }
    };
    private View rootView;
    private TableLayout tableLayout1;
    private GoogleMap googleMap;
    private TableLayout tableLayout;
    private String street="";
    private String city="";
    private String country="";

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        String jwtToken = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null);
        JWT decodedJWT = new JWT(jwtToken);
        String userId = decodedJWT.getClaim("id").asString();
        Call<DisplayEvent> call = ClientUtils.eventService.getEvent(requireActivity().getIntent().getIntExtra("year", 0),requireActivity().getIntent().getIntExtra("month", 0),requireActivity().getIntent().getIntExtra("day", 0),Integer.parseInt(userId));
        call.enqueue(new Callback<DisplayEvent>() {
            @Override
            public void onResponse(Call<DisplayEvent> call, Response<DisplayEvent> response) {
                if (response.isSuccessful()) {
                    Log.d("REZULTAT","Street: "+response.body().getAddress());
                    getLatLngFromAddress(response.body().getAddress(),response.body().getCity(),response.body().getCountry(),googleMap);
                } else {
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DisplayEvent> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
            }
        });

        // Example: Display a specific location (e.g., Sydney)

    }
    int idEv=0;
    private TextView title;
    private TextView title1;
    private SupportMapFragment mapFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView= inflater.inflate(R.layout.fragment_event_details, container, false);
        String jwtToken = requireContext().getSharedPreferences("Preferences", Context.MODE_PRIVATE).getString("JWT_TOKEN", null);
        JWT decodedJWT = new JWT(jwtToken);
        String userId = decodedJWT.getClaim("id").asString();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        tableLayout = rootView.findViewById(R.id.tableLayout);
        tableLayout1 = rootView.findViewById(R.id.tableLayout1);
        title=rootView.findViewById(R.id.guests);
        title1=rootView.findViewById(R.id.eventDetailsTitle);

        Call<DisplayEvent> call = ClientUtils.eventService.getEvent(requireActivity().getIntent().getIntExtra("year", 0),requireActivity().getIntent().getIntExtra("month", 0),requireActivity().getIntent().getIntExtra("day", 0),Integer.parseInt(userId));
        call.enqueue(new Callback<DisplayEvent>() {
            @Override
            public void onResponse(Call<DisplayEvent> call, Response<DisplayEvent> response) {
                if (response.isSuccessful()) {
                    idEv=response.body().getId();
                    displayEventsInTable(response.body(), tableLayout);
                    Call<ArrayList<String>> callParticipants = ClientUtils.eventService.getParticipantsNames(idEv);
                    callParticipants.enqueue(new Callback<ArrayList<String>>() {
                        @Override
                        public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                            if (response.isSuccessful()) {
                                displayParticipantsInTable(response.body(),tableLayout1);
                            }
                            else{
                                Log.i("rez", String.valueOf(response.code()));
                                Toast.makeText(requireContext(), "Failed to load participants", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                            Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                        }
                    });
                } else {
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DisplayEvent> call, Throwable t) {
                Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
            }
        });



        Button addToFavouritesButton = rootView.findViewById(R.id.addToFavourites);

        addToFavouritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Integer> callAddToFavourite = ClientUtils.eventService.addToFavourite(Integer.parseInt(userId),idEv);
                callAddToFavourite.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(requireContext(), "Event successfully added to favourites", Toast.LENGTH_SHORT).show();
                        }
                        else if(response.code()==409){
                            Toast.makeText(requireContext(), "Event has already added to favourites", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(requireContext(), "Failed to add to favourites", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Log.d("REZ", t.getMessage() != null ? t.getMessage() : "error");
                    }
                });
            }
        });

        Button generateParticipantsPdfButton = rootView.findViewById(R.id.btnGeneratePdfParticipants);

        generateParticipantsPdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdfParticipants(rootView);
            }
        });

        Button generateEventDetailsPdf = rootView.findViewById(R.id.eventDetailsPdf);
        generateEventDetailsPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdfEventDetails(rootView);
            }
        });
        return rootView;
    }

    private void displayEventsInTable(DisplayEvent event, TableLayout tableLayout) {
        if (event == null) return;

        TableRow rowHeader = new TableRow(requireContext());
        TextView tv = new TextView(requireContext());
        tv.setText("Name");
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        rowHeader.addView(tv);
        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        TableRow row = new TableRow(requireContext());
        addCell(row, event.getName());
        tableLayout.addView(row);

        rowHeader = new TableRow(requireContext());
        tv = new TextView(requireContext());
        tv.setGravity(Gravity.CENTER);
        tv.setText("Privacy");
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        rowHeader.addView(tv);
        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        row = new TableRow(requireContext());
        addCell(row, event.getPrivacy());
        tableLayout.addView(row);

        rowHeader = new TableRow(requireContext());
        tv = new TextView(requireContext());
        tv.setText("Description");
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        tv.setGravity(Gravity.CENTER);
        rowHeader.addView(tv);
        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        row = new TableRow(requireContext());
        addCell(row, event.getDescription());
        tableLayout.addView(row);

        rowHeader = new TableRow(requireContext());
        tv = new TextView(requireContext());
        tv.setText("Country");
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        tv.setGravity(Gravity.CENTER);
        rowHeader.addView(tv);
        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        row = new TableRow(requireContext());
        addCell(row, event.getCountry());
        tableLayout.addView(row);

        rowHeader = new TableRow(requireContext());
        tv = new TextView(requireContext());
        tv.setText("City");
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        tv.setGravity(Gravity.CENTER);
        rowHeader.addView(tv);
        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        row = new TableRow(requireContext());
        addCell(row, event.getCity());
        tableLayout.addView(row);

        rowHeader = new TableRow(requireContext());
        tv = new TextView(requireContext());
        tv.setText("Address");
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        tv.setGravity(Gravity.CENTER);
        rowHeader.addView(tv);
        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        row = new TableRow(requireContext());
        addCell(row, event.getAddress());
        tableLayout.addView(row);

        rowHeader = new TableRow(requireContext());
        tv = new TextView(requireContext());
        tv.setText("Participants");
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        tv.setGravity(Gravity.CENTER);
        rowHeader.addView(tv);
        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        row = new TableRow(requireContext());
        addCell(row, event.getParticipants());
        tableLayout.addView(row);

        rowHeader = new TableRow(requireContext());
        tv = new TextView(requireContext());
        tv.setText("DateTime");
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        tv.setGravity(Gravity.CENTER);
        rowHeader.addView(tv);
        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        row = new TableRow(requireContext());
        addCell(row, event.getDateTime());
        tableLayout.addView(row);

        rowHeader = new TableRow(requireContext());
        tv = new TextView(requireContext());
        tv.setText("EventType");
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        tv.setGravity(Gravity.CENTER);
        rowHeader.addView(tv);
        rowHeader.setBackgroundColor(Color.parseColor("#EC8305"));
        tableLayout.addView(rowHeader);

        row = new TableRow(requireContext());
        addCell(row, event.getEventType());
        tableLayout.addView(row);


    }

    private void displayParticipantsInTable(ArrayList<String> participants, TableLayout tableLayout) {
        if (participants == null) return;

        for (String p : participants) {
            TableRow row = new TableRow(requireContext());
            addCell(row, p);
            tableLayout.addView(row);
        }

    }

    private void addCell(TableRow row, String text) {
        TextView tv = new TextView(requireContext());
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#5B3407"));
        tv.setTextSize(18f);
        tv.setText(text);
        row.addView(tv);
    }


    public void generatePdfParticipants(View rootView) {

        PdfDocument document = new PdfDocument();

        View contentToPrint = tableLayout1; // Assuming your content is the first child of ScrollView
        View contentToPrint1 = title;

        // Calculate content height to determine if multiple pages are needed
        int contentHeight = contentToPrint.getHeight()+contentToPrint1.getHeight()+24;
        int pageHeight = 842; // A4 page height in points (approx.)
        int pageWidth = title.getWidth();  // A4 page width in points (approx.)

        int numPages = (int) Math.ceil((double) contentHeight / pageHeight);

        for (int i = 0; i < numPages; i++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i + 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            // Calculate the vertical offset for the current page
            int verticalOffset = i * pageHeight;

            // Translate the canvas to draw the correct portion of the content
            canvas.translate(0, 0);

            contentToPrint1.draw(canvas);
            canvas.translate((float)pageWidth/5, -verticalOffset+20+24+30);
            // Draw the content onto the canvas
            contentToPrint.draw(canvas);

            document.finishPage(page);
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "EventParticipants.pdf");
        try {
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(requireContext(), "PDF generated successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "PDF generation failed!", Toast.LENGTH_SHORT).show();
        } finally {
            document.close();
        }
    }

    public void generatePdfEventDetails(View rootView) {

        PdfDocument document = new PdfDocument();

        View contentToPrint = title1; // Assuming your content is the first child of ScrollView
        View contentToPrint1 = tableLayout;


        // Calculate content height to determine if multiple pages are needed
        int contentHeight = contentToPrint.getHeight()+contentToPrint1.getHeight()+24;
        int pageHeight = 842; // A4 page height in points (approx.)
        int pageWidth = tableLayout.getWidth();  // A4 page width in points (approx.)

        int numPages = (int) Math.ceil((double) contentHeight / pageHeight);

        for (int i = 0; i < numPages; i++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i + 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            // Calculate the vertical offset for the current page
            int verticalOffset = i * pageHeight;

            // Translate the canvas to draw the correct portion of the content
            canvas.translate(0, 0);

            contentToPrint.draw(canvas);
            canvas.translate(0, -verticalOffset+20+24+30);
            // Draw the content onto the canvas
            contentToPrint1.draw(canvas);

            document.finishPage(page);
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "EventDetails1.pdf");
        try {
            document.writeTo(new FileOutputStream(file));
            Toast.makeText(requireContext(), "PDF generated successfully!", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "PDF generation failed!", Toast.LENGTH_SHORT).show();
        } finally {
            document.close();
        }
    }
}