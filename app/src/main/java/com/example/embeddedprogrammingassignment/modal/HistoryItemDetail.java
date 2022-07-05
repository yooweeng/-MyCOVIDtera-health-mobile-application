package com.example.embeddedprogrammingassignment.modal;

public class HistoryItemDetail {
    String location;
    String isCheckIn;
    String time;

    public HistoryItemDetail() {
    }

    public HistoryItemDetail(String location, String isCheckIn, String time) {
        this.location = location;
        this.isCheckIn = isCheckIn;
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getIsCheckIn() {
        return isCheckIn;
    }

    public void setIsCheckIn(String isCheckIn) {
        this.isCheckIn = isCheckIn;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "HistoryItemDetail{" +
                "location='" + location + '\'' +
                ", isCheckIn='" + isCheckIn + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
