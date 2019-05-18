package com.example.karokojnr.nadab_hotels.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class Order {
    @SerializedName("_id")
    private String orderId;

    @SerializedName("status")
    private String orderStatus;

    @SerializedName("totalPrice")
    private Double totalPrice;

    @SerializedName("totalBill")
    private Double totalBill;

    @SerializedName("totalItems")
    private int totalItems;

    @SerializedName("items")
    private OrderItem[] orderItems;

    @SerializedName("payments")
    private OrderPayment[] orderPayments;

    @SerializedName("hotel")
    private String hotel;

    @SerializedName("customerId")
    private Customer customer;

    @SerializedName("updatedAt")
    private String updatedAt;


    public Order(String orderStatus, Double totalPrice, int totalItems, OrderItem[] orderItems, String hotel) {
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.totalItems = totalItems;
        this.orderItems = orderItems;
        this.hotel = hotel;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Double getTotalBill() {
        return totalBill;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public OrderItem[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem[] orderItems) {
        this.orderItems = orderItems;
    }

    public OrderPayment[] getOrderPayments() {
        return orderPayments;
    }

    public void setOrderPayments(OrderPayment[] orderPayments) {
        this.orderPayments = orderPayments;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderStatus='" + orderStatus + '\'' +
                ", totalPrice=" + totalPrice +
                ", totalItems=" + totalItems +
                ", orderItems=" + Arrays.toString(orderItems) +
                ", hotel='" + hotel + '\'' +
                ", customer='" + customer.toString() + '\'' +
                '}';
    }
}