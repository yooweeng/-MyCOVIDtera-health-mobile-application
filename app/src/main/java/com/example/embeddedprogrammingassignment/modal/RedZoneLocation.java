package com.example.embeddedprogrammingassignment.modal;

public class RedZoneLocation {
    int cases;
    double longitude;
    double latitude;


    public RedZoneLocation(int cases, double latitude, double longitude) {
        this.cases = cases;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getCases() {
        return cases;
    }

    public void setCases(int cases) {
        this.cases = cases;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "RedZoneLocation{" +
                "cases=" + cases +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
