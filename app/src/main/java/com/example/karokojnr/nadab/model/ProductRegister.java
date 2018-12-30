package com.example.karokojnr.nadab.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/*
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
*/
public class ProductRegister {
    @SerializedName("products")
    private ArrayList<Products> productList;

    public ArrayList<Products> getProductArrayList() {
        return productList;
    }

    public void setProductArrayList(ArrayList<Products> productArrayList) {
        this.productList = productArrayList;
    }
}