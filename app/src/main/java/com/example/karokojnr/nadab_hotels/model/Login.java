package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

public class Login {
    @SerializedName("token")
    private String token;

    @SerializedName("hotel")
    private Hotel hotel;

    public Hotel getHotel() {
        return hotel;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "Login{" +
                "token='" + token + '\'' +
                '}';
    }

}
