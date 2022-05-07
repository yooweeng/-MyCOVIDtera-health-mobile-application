package com.example.embeddedprogrammingassignment.modal;

public class PointValueLabel {
    int xValue, yValue;
    String xLabel;

    public PointValueLabel(int xValue, int yValue, String xLabel) {
        this.xValue = xValue;
        this.yValue = yValue;
        this.xLabel = xLabel;
    }

    public PointValueLabel() {
    }

    public int getxValue() {
        return xValue;
    }

    public int getyValue() {
        return yValue;
    }

    public String getxLabel() {
        return xLabel;
    }
}
