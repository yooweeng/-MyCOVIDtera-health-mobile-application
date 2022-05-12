package com.example.embeddedprogrammingassignment.apiclient.Covid19;

import java.util.ArrayList;

public class Covid19Data {

    String Confirmed;
    String Deaths;
    String Active;
    String Date;

    public String getConfirmed() {
        return Confirmed;
    }

    public void setConfirmed(String confirmed) {
        Confirmed = confirmed;
    }

    public String getDeaths() {
        return Deaths;
    }

    public void setDeaths(String deaths) {
        Deaths = deaths;
    }

    public String getActive() {
        return Active;
    }

    public void setActive(String active) {
        Active = active;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    @Override
    public String toString() {
        return "Covid19Data{" +
                "Confirmed='" + Confirmed + '\'' +
                ", Deaths='" + Deaths + '\'' +
                ", Active='" + Active + '\'' +
                ", Date='" + Date + '\'' +
                '}';
    }
}
