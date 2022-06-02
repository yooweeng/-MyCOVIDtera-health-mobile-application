package com.example.embeddedprogrammingassignment.modal;

import java.util.ArrayList;

public class QrHistory {
    String date;
    String location;
    ArrayList<HistoryItem> details;

    public QrHistory() {
        this.date = "";
        this.location = "";
        this.details = details;
    }

    public QrHistory(String date, String location, ArrayList<HistoryItem> details) {
        this.date = date;
        this.location = location;
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ArrayList<HistoryItem> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<HistoryItem> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "QrHistory{" +
                "date='" + date + '\'' +
                ", location='" + location + '\'' +
                ", details=" + details +
                '}';
    }
}
