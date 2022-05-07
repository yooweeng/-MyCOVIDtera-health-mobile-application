package com.example.embeddedprogrammingassignment.modal;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class GraphDataModal {
    public GraphDataModal() {

    }

    public List<Entry> LineChartDataSet() {
        ArrayList<Entry> dataset = new ArrayList<>();

        dataset.add(new Entry(0, 11));
        dataset.add(new Entry(1, 12));
        dataset.add(new Entry(2, 8));
        dataset.add(new Entry(3, 5));
        dataset.add(new Entry(4, 7));
        dataset.add(new Entry(5, 2));

        return dataset;
    }

    public List<String> xAxisLabel () {
        List<String> label = new ArrayList<>();
        label.add("01-01");
        label.add("01-02");
        label.add("01-03");
        label.add("01-04");
        label.add("01-05");
        label.add("01-06");

        return label;
    }
}
