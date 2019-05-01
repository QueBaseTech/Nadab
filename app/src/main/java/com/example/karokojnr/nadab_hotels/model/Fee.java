package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

public class Fee {

    @SerializedName("_id")
    private String id;

    @SerializedName("numberOfOrders")
    private int numberOfOrders;

    @SerializedName("status")
    private String status;

    @SerializedName("day")
    private String day;

    @SerializedName("total")
    private double total;

    @SerializedName("hotel")
    private String hotel;

    public String getId() {
        return id;
    }

    public int getNumberOfOrders() {
        return numberOfOrders;
    }

    public String getStatus() {
        return status;
    }

    public String getDay() {
        return day;
    }

    public double getTotal() {
        return total;
    }

    public String getHotel() {
        return hotel;
    }
}

