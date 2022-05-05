package com.example.embeddedprogrammingassignment.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.embeddedprogrammingassignment.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class StatisticsFragment extends Fragment {

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        LineChart lineChart = view.findViewById(R.id.statisticsLineChart);
        LineDataSet lineDataSet = new LineDataSet(LineChartDataSet(), "Data set");
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();

        // Inflate the layout for this fragment
        return view;
    }

    private ArrayList<Entry> LineChartDataSet() {
        ArrayList<Entry> dataset = new ArrayList<>();

        dataset.add(new Entry(0, 11));
        dataset.add(new Entry(1, 12));
        dataset.add(new Entry(2, 8));
        dataset.add(new Entry(3, 5));
        dataset.add(new Entry(4, 7));
        dataset.add(new Entry(5, 2));
        dataset.add(new Entry(6, 5));
        dataset.add(new Entry(7, 8));
        dataset.add(new Entry(8, 1));
        dataset.add(new Entry(9, 5));
        dataset.add(new Entry(10, 12));

        return dataset;
    }
}