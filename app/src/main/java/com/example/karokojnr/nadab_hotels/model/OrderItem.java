package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

public class OrderItem {

    @SerializedName("name")
    private String name;

    @SerializedName("qty")
    private int qty;

    @SerializedName("status")
    private String status;

    @SerializedName("_id")
    private String id;

    @SerializedName("price")
    private double price;

    public OrderItem() {}

    public OrderItem(String name, int qty, double price) {
        this.name = name;
        this.qty = qty;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getQty() {
        return qty;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
