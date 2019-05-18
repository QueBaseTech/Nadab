package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Orders {
    @SerializedName("orders")
    private ArrayList<Order> ordersList;

    public ArrayList<Order> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(ArrayList<Order> productArrayList) {
        this.ordersList = productArrayList;
    }

}
