package com.example.karokojnr.nadab.utils;

public class HotelSharedPreference {
    private String id, username, email;

    public HotelSharedPreference(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
