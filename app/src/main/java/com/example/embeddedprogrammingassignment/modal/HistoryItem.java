package com.example.embeddedprogrammingassignment.modal;

import com.example.embeddedprogrammingassignment.R;

public class HistoryItem {
    int image;
    String location;
    String time;

    public HistoryItem() {
        this.image = R.drawable.icon_default;
        this.location = "";
        this.time = "";
    }

    public HistoryItem(int image, String location, String time) {
        this.image = image;
        this.location = location;
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
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
                "image=" + image +
                ", location='" + location + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
