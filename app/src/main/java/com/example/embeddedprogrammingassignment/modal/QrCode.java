package com.example.embeddedprogrammingassignment.modal;

public class QrCode {
    String isCheckIn;
    String location;

    public QrCode() {
    }

    public QrCode(String isCheckIn, String location) {
        this.isCheckIn = isCheckIn;
        this.location = location;
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

    @Override
    public String toString() {
        return "QrCode{" +
                "isCheckIn='" + isCheckIn + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
