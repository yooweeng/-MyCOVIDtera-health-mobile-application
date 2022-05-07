package com.example.embeddedprogrammingassignment.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.embeddedprogrammingassignment.GraphMarkerView;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.PointValueLabel;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
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

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ArrayList<Entry> total_cases_data = new ArrayList<>();
    private List<String> total_cases_label = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

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