package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

public class Stat {
    @SerializedName("totalItems")
    private double totalItems;

    @SerializedName("totalPrice")
    private double totalPrice;

    public Stat(double totalItems, double totalPrice) {
        this.totalItems = totalItems;
        this.totalPrice = totalPrice;
    }

    public double getTotalItems() {
        return totalItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "totalItems=" + totalItems +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
