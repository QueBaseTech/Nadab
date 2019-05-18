package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

public class HotelResponse {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("hotel")
    private Hotel hotel;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public boolean isSuccessful() {
        return success;
    }
}
