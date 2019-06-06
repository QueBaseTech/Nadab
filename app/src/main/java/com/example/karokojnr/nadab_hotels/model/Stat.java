package com.example.karokojnr.nadab_hotels.model;

public class Stat {
    private double totalItems;
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
}
