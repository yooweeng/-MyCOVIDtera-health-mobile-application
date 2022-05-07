package com.example.embeddedprogrammingassignment.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.embeddedprogrammingassignment.GraphMarkerView;
import com.example.embeddedprogrammingassignment.R;
import com.example.embeddedprogrammingassignment.modal.GraphDataModal;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticsFragment extends Fragment {

    public StatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        // Hooker
        LineChart lineChart = view.findViewById(R.id.statisticsLineChart);
        GraphDataModal readDataSet = new GraphDataModal();

        // Pinch, click, zoom
//        lineChart.setTouchEnabled(false);
//        lineChart.setPinchZoom(false);

        // Axis Line
        XAxis xAxis = lineChart.getXAxis();
        YAxis leftAxis = lineChart.getAxisLeft();
        YAxis rightAxis = lineChart.getAxisRight();

        // XAxis customization
        XAxis.XAxisPosition position = XAxis.XAxisPosition.BOTTOM;
        xAxis.setPosition(position);
        xAxis.setAxisLineWidth(1.5f);
        List<String> xAxisValues = readDataSet.xAxisLabel();
        xAxis.setValueFormatter(new com.github.mikephil.charting.formatter.IndexAxisValueFormatter(xAxisValues));
        xAxis.setGranularity(1f);
        xAxis.setEnabled(true);
        xAxis.setSpaceMin(0.5f);
        xAxis.setSpaceMax(0.5f);
        lineChart.getDescription().setEnabled(false);
        rightAxis.setEnabled(false);
        rightAxis.setGranularity(1f);
        leftAxis.setGranularity(1f);

        // Legend
        Legend legend = lineChart.getLegend();
//        legend.setOrientation(Legend.LegendOrientation.VERTICAL);  //Set the legend horizontal display
//        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP); //top
//        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT); //Right to it


        // Hide grid lines
        lineChart.getAxisRight().setDrawGridLines(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setDrawGridLines(false);

        GraphMarkerView mv = new GraphMarkerView(requireContext(), R.layout.layout_graph_marker_view, xAxisValues);
        lineChart.setMarker(mv);

        LineDataSet lineDataSet = new LineDataSet(readDataSet.LineChartDataSet(), "Daily Cases");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawValues(false);
        lineDataSet.setColor(R.color.purple_title);
        lineDataSet.setLineWidth(2f);
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);

        LineData lineData = new LineData(iLineDataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();

        // Inflate the layout for this fragment
        return view;
    }

}