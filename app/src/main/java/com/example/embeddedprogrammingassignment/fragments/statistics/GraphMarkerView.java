package com.example.embeddedprogrammingassignment.fragments.statistics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.embeddedprogrammingassignment.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class GraphMarkerView extends MarkerView {

    private final TextView tvContent1;
    private final TextView tvContent2;
    private final List<String> mXLabels;
    private final Context mContext;

    public GraphMarkerView(Context context, int layoutResource, List<String> xAxisValues) {
        super(context, layoutResource);
        this.mContext = context;
        tvContent1 = (TextView) findViewById(R.id.tvGraphMarkerView1);
        tvContent2 = (TextView) findViewById(R.id.tvGraphMarkerView2);
        mXLabels = xAxisValues;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent1.setText("Cases: " + (int) e.getY());
        tvContent2.setText("Date: " + mXLabels.get((int) e.getX()));
        super.refreshContent(e, highlight);
    }

    private MPPointF mOffset;

    @Override
    public MPPointF getOffset() {

        if(mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = new MPPointF(-(getWidth() / 2), -getHeight());
        }

        return mOffset;
    }



}
