package com.example.eventplanner.fragments;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.eventplanner.R;
import com.example.eventplanner.activities.DisplayAllEventsActivity;
import com.example.eventplanner.clients.ClientUtils;
import com.example.eventplanner.model.CustomXAxisRenderer;
import com.example.eventplanner.model.DisplayEvent;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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

    private ArrayList<Integer> ratings = new ArrayList<>();
    private ArrayList<PieEntry> pieEntries = new ArrayList<>();

    private ArrayList<String> participationDateTime = new ArrayList<>();

    private ArrayList<BarEntry> lineEntries = new ArrayList<>();
    int monthNumber=0;
    int yearNumber=0;

    final String[] xAxisLabels = new String[12];
    final ArrayList<String> months = new ArrayList<>();

    private ScrollView scrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.fragment_statistics, container, false);
        scrollView=rootView.findViewById(R.id.scroll);
        Call<ArrayList<Integer>> call = ClientUtils.eventService.getRatingsForEvent(requireActivity().getIntent().getIntExtra("id",0));
        call.enqueue(new Callback<ArrayList<Integer>>() {
            @Override
            public void onResponse(Call<ArrayList<Integer>> call, Response<ArrayList<Integer>> response) {
                if (response.isSuccessful()) {
                    ratings=response.body();
                    pieEntries.add(new PieEntry(0,"Rate 1"));
                    pieEntries.add(new PieEntry(0,"Rate 2"));
                    pieEntries.add(new PieEntry(0,"Rate 3"));
                    pieEntries.add(new PieEntry(0,"Rate 4"));
                    pieEntries.add(new PieEntry(0,"Rate 5"));
                    for (int i = 0; i < ratings.size(); i++) {
                        for(int j=0;j<pieEntries.size();j++){
                            if(String.valueOf(ratings.get(i)).equals(pieEntries.get(j).getLabel().split(" ")[1].trim())){
                                int value=(int)pieEntries.get(j).getValue();
                                String label= pieEntries.get(j).getLabel();
                                pieEntries.remove(j);
                                pieEntries.add(new PieEntry(value+1,label));
                                break;
                            }
                        }
                    }
                    PieDataSet pieDataSet = new PieDataSet(pieEntries, "Ratings");
                    int[] colors = {Color.RED, Color.GREEN, Color.BLUE,
                            Color.YELLOW, Color.CYAN, Color.MAGENTA};
                    pieDataSet.setColors(colors);
                    PieData pieData = new PieData(pieDataSet);
                    pieData.setValueTextSize(15f);
                    PieChart pieChart = rootView.findViewById(R.id.chart);
                    pieChart.setData(pieData);
                    pieChart.animateY(1000);
                    pieChart.animateX(1000);
                    pieChart.setEntryLabelColor(Color.BLACK);
                } else {
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load ratings", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Integer>> call, Throwable t) {
                Log.d("REZ2", t.getMessage() != null ? t.getMessage() : "error");
            }
        });
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        Call<DisplayEvent> callEvent = ClientUtils.eventService.getSpecificEvent(requireActivity().getIntent().getIntExtra("id", 0));
        callEvent.enqueue(new Callback<DisplayEvent>() {
            @Override
            public void onResponse(Call<DisplayEvent> call, Response<DisplayEvent> response) {
                if(response.isSuccessful()) {
                    DisplayEvent event = response.body();
                    monthNumber = LocalDateTime.parse(event.getDateTime(), formatter).getMonthValue();
                    yearNumber = LocalDateTime.parse(event.getDateTime(), formatter).getYear();

                    Log.d("REZZZZ",  "Rezultat meseci " + monthNumber);

                }
                else{
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DisplayEvent> call, Throwable t) {
                Log.d("REZULTAT", t.getMessage() != null ? t.getMessage() : "error");
            }
        });
        Call<ArrayList<String>> call1 = ClientUtils.eventService.getParticipationDateTime(requireActivity().getIntent().getIntExtra("id",0));
        call1.enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if(response.isSuccessful()) {

                    participationDateTime = response.body();

                    for(int i=0;i<=11;i++){
                        lineEntries.add(new BarEntry(i+1, 0));
                    }
                    for (int i = 0; i < participationDateTime.size(); i++) {
                        if ((Integer.parseInt(participationDateTime.get(i).split("-")[0]) == yearNumber && Integer.parseInt(participationDateTime.get(i).split("-")[1]) <= monthNumber)
                                || (Integer.parseInt(participationDateTime.get(i).split("-")[0]) ==yearNumber-1 && Integer.parseInt(participationDateTime.get(i).split("-")[1])>monthNumber)) {
                            if(Integer.parseInt(participationDateTime.get(i).split("-")[1])<=monthNumber) {
                                lineEntries.get(lineEntries.size() - monthNumber - 1 + Integer.parseInt(participationDateTime.get(i).split("-")[1])).setY(lineEntries.get(lineEntries.size() - monthNumber - 1 + Integer.parseInt(participationDateTime.get(i).split("-")[1])).getY() + 1);
                            }
                            else{
                                lineEntries.get(Integer.parseInt(participationDateTime.get(i).split("-")[1])-monthNumber-1).setY(lineEntries.get(Integer.parseInt(participationDateTime.get(i).split("-")[1])-monthNumber-1).getY() + 1);
                            }
                        }

                    }
                    BarDataSet barDataSet = new BarDataSet(lineEntries, "Participation date");
                    /*int[] colors = {Color.RED, Color.GREEN, Color.BLUE,
                            Color.YELLOW, Color.CYAN, Color.MAGENTA};
                    lineDataSet.setColors(colors);*/
                    BarData barData = new BarData(barDataSet);
                    barData.setValueTextSize(8f);
                    BarChart barChart = rootView.findViewById(R.id.barChart);
                    barChart.setData(barData);
                    barChart.animateY(1000);
                    barChart.animateX(1000);
                    XAxis xAxis = barChart.getXAxis();
                    xAxis.setTextSize(8f);
                    months.add("JAN");
                    months.add("FEB");
                    months.add("MAR");
                    months.add("APR");
                    months.add("MAY");
                    months.add("JUN");
                    months.add("JUL");
                    months.add("AVG");
                    months.add("SEP");
                    months.add("OCT");
                    months.add("NOV");
                    months.add("DEC");

                    int year=yearNumber%2000;
                    for(int i=1;i<=monthNumber;i++){
                        xAxisLabels[i-1]=months.get(i-1) + year;
                    }

                    for(int i=12;i>monthNumber;i--){
                        xAxisLabels[i-1]=months.get(i-1) + (year-1);
                    }


                    xAxis.setValueFormatter(new ValueFormatter() {
                        @Override
                        public String getFormattedValue(float value) {
                            // Ensure the value is within the bounds of your labels list
                            if (value >= 1 && value <= xAxisLabels.length-monthNumber) {
                                return xAxisLabels[(int) (monthNumber+value-1)];
                            }
                            if(value>=xAxisLabels.length-monthNumber+1 && value<=xAxisLabels.length){
                                return xAxisLabels[(int) (value-xAxisLabels.length+monthNumber-1)];
                            }
                            return ""; // Return empty string for out-of-bounds values
                        }

                    });
                    barChart.getXAxis().setLabelCount(lineEntries.size(), false);

                    xAxis.setGranularity(1f);
                    xAxis.setGranularityEnabled(true);

                }
                else{
                    Log.i("rez", String.valueOf(response.code()));
                    Toast.makeText(requireContext(), "Failed to load participation dates", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                Log.d("REZ1", t.getMessage() != null ? t.getMessage() : "error");
            }
        });

        Button generatePdfButton = rootView.findViewById(R.id.btnGeneratePdf);

        generatePdfButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generatePdf(rootView);
            }
        });
        return rootView;
    }
    public void generatePdf(View rootView) {

        PdfDocument document = new PdfDocument();
        View contentToPrint = scrollView.getChildAt(0); // Assuming your content is the first child of ScrollView

        // Calculate content height to determine if multiple pages are needed
        int contentHeight = contentToPrint.getHeight();
        int pageHeight = 842; // A4 page height in points (approx.)
        int pageWidth = rootView.getWidth();  // A4 page width in points (approx.)

        int numPages = (int) Math.ceil((double) contentHeight / pageHeight);

        for (int i = 0; i < numPages; i++) {
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(pageWidth, pageHeight, i + 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();

            // Calculate the vertical offset for the current page
            int verticalOffset = i * pageHeight;

            // Translate the canvas to draw the correct portion of the content
            canvas.translate(0, -verticalOffset);

            // Draw the content onto the canvas
            contentToPrint.draw(canvas);

            document.finishPage(page);
        }
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Statistic.pdf");
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