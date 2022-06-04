package com.example.embeddedprogrammingassignment.modal;

import org.parceler.Parcel;

@Parcel
public class Appointments {
    String nric, getVaccine, comorbidities, appointmentDate, appointmentLocation, vaccine1, vaccine2, vaccine3;

    public Appointments(String nric, String getVaccine, String comorbidities, String appointmentDate, String appointmentLocation, String vaccine1, String vaccine2, String vaccine3) {
        this.nric = nric;
        this.getVaccine = getVaccine;
        this.comorbidities = comorbidities;
        this.appointmentDate = appointmentDate;
        this.appointmentLocation = appointmentLocation;
        this.vaccine1 = vaccine1;
        this.vaccine2 = vaccine2;
        this.vaccine3 = vaccine3;
    }

    public Appointments() {
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getGetVaccine() {
        return getVaccine;
    }

    public void setGetVaccine(String getVaccine) {
        this.getVaccine = getVaccine;
    }

    public String getComorbidities() {
        return comorbidities;
    }

    public void setComorbidities(String comorbidities) {
        this.comorbidities = comorbidities;
    }

    public String getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentLocation() {
        return appointmentLocation;
    }

    public void setAppointmentLocation(String appointmentLocation) {
        this.appointmentLocation = appointmentLocation;
    }

    public String getVaccine1() {
        return vaccine1;
    }

    public void setVaccine1(String vaccine1) {
        this.vaccine1 = vaccine1;
    }

    public String getVaccine2() {
        return vaccine2;
    }

    public void setVaccine2(String vaccine2) {
        this.vaccine2 = vaccine2;
    }

    public String getVaccine3() {
        return vaccine3;
    }

    public void setVaccine3(String vaccine3) {
        this.vaccine3 = vaccine3;
    }

    @Override
    public String toString() {
        return "Appointments{" +
                "nric='" + nric + '\'' +
                ", getVaccine='" + getVaccine + '\'' +
                ", comorbiditites='" + comorbidities + '\'' +
                ", date='" + appointmentDate + '\'' +
                ", location='" + appointmentLocation + '\'' +
                ", vaccine1='" + vaccine1 + '\'' +
                ", vaccine2='" + vaccine2 + '\'' +
                ", vaccine3='" + vaccine3 + '\'' +
                '}';
    }
}