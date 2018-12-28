package com.example.karokojnr.nadab.model;

import com.google.gson.annotations.SerializedName;

public class ProductRegister {
    @SerializedName("product")
    private Products products;

    public Products getProduct() {
        return products;
    }

    public void setProduct(Products product) {
        this.products = products;
    }
}
