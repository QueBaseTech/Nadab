package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

public class HotelRegister {
    @SerializedName("hotel")
    private Hotel hotel;


    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}
