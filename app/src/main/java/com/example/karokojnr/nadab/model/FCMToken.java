package com.example.karokojnr.nadab.model;

import com.google.gson.annotations.SerializedName;

public class FCMToken {
    @SerializedName("token")
    private String token;

    @SerializedName("hotelID")
    public String hotelID;

    public FCMToken(String token, String hotelID) {
        this.token = token;
        this.hotelID = hotelID;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getHotelID() {
        return hotelID;
    }

    public void setHotelID(String hotelID) {
        this.hotelID = hotelID;
    }
}
