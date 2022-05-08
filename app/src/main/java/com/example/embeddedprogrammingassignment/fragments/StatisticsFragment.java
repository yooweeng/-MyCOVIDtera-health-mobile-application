package com.example.embeddedprogrammingassignment.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.fragments.statistics.DailyCases;
import com.example.embeddedprogrammingassignment.fragments.statistics.GraphMarkerView;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.PointValueLabel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StatisticsFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Entry> total_cases_data = new ArrayList<>();
    private List<String> total_cases_label = new ArrayList<>();
    List<Integer> dailyCasesProgress;
    ProgressBar progMon, progTue, progWed, progThurs, progFri, progSat, progSun;
    TextView dailyCasesTv, dailyIncreaseTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        progressBarHooker(view);

        DailyCases dailyCases = new DailyCases();
        dailyCases.setDailyValues();
        dailyCasesProgress = dailyCases.setProgressValues();
        Log.d("Kotlin set daily cases @ Statistics Fragment", dailyCasesProgress.toString());

        setProgressBar(dailyCasesProgress, dailyCases);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("stats/total_cases");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChildren()) {
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        PointValueLabel pvl = ds.getValue(PointValueLabel.class);
                        total_cases_data.add(new Entry(pvl.getxValue(), pvl.getyValue()));
                        total_cases_label.add(pvl.getxLabel());
                    }
                    showTotalCaseChart(view);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void setProgressBar(List<Integer> list, DailyCases dailyCases) {
        int maxProgress = Collections.max(list);
        String day = LocalDate.now().getDayOfWeek().name();
        List<String> daysList = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");
        List<ProgressBar> progressBarList = Arrays.asList(progMon, progTue, progWed, progThurs, progFri, progSat, progSun);
        int index = daysList.indexOf(day);
        for (int i = 0; i < dailyCasesProgress.size(); i++) {
            if(i == index) {
                progressBarList.get(i).setProgressDrawable(getResources().getDrawable(R.drawable.progress_vertical_selected));
                int previousDay = 0;
                if(index==0)
                    previousDay = dailyCases.getDailyCasesList().get(list.size()-1);
                else
                    previousDay = dailyCases.getDailyCasesList().get(i-1);
                String change = calculateChange(previousDay, dailyCases.getDailyCasesList().get(i));
                if (change.contains("-"))
                    dailyIncreaseTv.setText(change+"%↓");
                else
                    dailyIncreaseTv.setText(change+"%↑");
                dailyCasesTv.setText(dailyCases.getDailyCasesList().get(i).toString());
            }
            else
                progressBarList.get(i).setProgressDrawable(getResources().getDrawable(R.drawable.progress_vertical_unselected));
            progressBarList.get(i).setProgress(Math.round(list.get(i)));
            progressBarList.get(i).setMax(maxProgress);
            Log.d("Progress to int", ""+Math.round(list.get(i)));
        }
    }

    private String calculateChange(int previousDayCases, Integer todayCases) {
        Float percentageChange = ((todayCases-previousDayCases) * 100.0f)/previousDayCases;
        return percentageChange.toString();
    }

    private void progressBarHooker(View view) {
        progMon = view.findViewById(R.id.progressStatMon);
        progTue = view.findViewById(R.id.progressStatTue);
        progWed = view.findViewById(R.id.progressStatWed);
        progThurs = view.findViewById(R.id.progressStatThur);
        progFri = view.findViewById(R.id.progressStatFri);
        progSat = view.findViewById(R.id.progressStatSat);
        progSun = view.findViewById(R.id.progressStatSun);
        dailyCasesTv = view.findViewById(R.id.tvStatsNewDailyCases);
        dailyIncreaseTv = view.findViewById(R.id.tvStatsDailyIncreasePercentage);
    }

    private void showTotalCaseChart(View view) {
        // Hooker
        LineChart lineChart = view.findViewById(R.id.statisticsLineChart);

        // Axis Line
        XAxis xAxis = lineChart.getXAxis();
        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis rightAxis = lineChart.getAxisRight();

        // XAxis customization
        XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
        xAxis.setPosition(position);
        xAxis.setAxisLineWidth(1.5f);
        List<String> xAxisLabels = total_cases_label;

        Log.i("Database label", xAxisLabels.toString());
        Log.i("Database data", total_cases_data.toString());

        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisLabels));
        xAxis.setGranularity(1f);
        xAxis.setEnabled(true);
        xAxis.setSpaceMin(0.5f);
        xAxis.setSpaceMax(0.5f);
        lineChart.getDescription().setEnabled(false);
        rightAxis.setEnabled(false);
        rightAxis.setGranularity(1f);
        leftAxis.setGranularity(1f);

        // Hide grid lines
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        GraphMarkerView mv = new GraphMarkerView(requireContext(), R.layout.layout_graph_marker_view, xAxisLabels);
        lineChart.setMarker(mv);

        LineDataSet lineDataSet = new LineDataSet(total_cases_data, "Daily Cases");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(R.color.purple_title);
        lineDataSet.setLineWidth(2f);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

}