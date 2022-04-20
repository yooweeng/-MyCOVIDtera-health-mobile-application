package com.example.embeddedprogrammingassignment.modal;

public class User {
    String nric, password, name, phone, gender, state;

    public User(String nric, String password, String name, String phone, String gender, String state) {
        this.nric = nric;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.gender = gender;
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public User() {
    }

    public String getNric() {
        return nric;
    }

    public void setNric(String nric) {
        this.nric = nric;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
