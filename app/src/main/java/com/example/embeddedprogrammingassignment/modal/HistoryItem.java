package com.example.embeddedprogrammingassignment.modal;

public class HistoryItem {
    String isCheckIn;
    String location;
    String time;

    public HistoryItem() {
    }

    public HistoryItem(String isCheckIn, String location, String time) {
        this.isCheckIn = isCheckIn;
        this.location = location;
        this.time = time;
    }

    public String getIsCheckIn() {
        return isCheckIn;
    }

    public void setIsCheckIn(String isCheckIn) {
        this.isCheckIn = isCheckIn;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "HistoryItem{" +
                "isCheckIn='" + isCheckIn + '\'' +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
