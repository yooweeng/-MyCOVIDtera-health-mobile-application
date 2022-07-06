package com.example.embeddedprogrammingassignment.modal;

import org.parceler.Parcel;

@Parcel
public class Appointments {
    String nric, getVaccine, comorbidities, vaccineManufacturer, appointmentDate, appointmentLocation, status;

    public Appointments(String nric, String getVaccine, String comorbidities, String vaccineManufacturer, String appointmentDate, String appointmentLocation, String status) {
        this.nric = nric;
        this.getVaccine = getVaccine;
        this.comorbidities = comorbidities;
        this.vaccineManufacturer = vaccineManufacturer;
        this.appointmentDate = appointmentDate;
        this.appointmentLocation = appointmentLocation;
        this.status = status;
    }

    public Appointments(){

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

    public String getVaccineManufacturer() {
        return vaccineManufacturer;
    }

    public void setVaccineManufacturer(String vaccineManufacturer) {
        this.vaccineManufacturer = vaccineManufacturer;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Appointments{" +
                "nric='" + nric + '\'' +
                ", getVaccine='" + getVaccine + '\'' +
                ", comorbiditites='" + comorbidities + '\'' +
                ", manufacturer='" + vaccineManufacturer + '\'' +
                ", date='" + appointmentDate + '\'' +
                ", location='" + appointmentLocation + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}