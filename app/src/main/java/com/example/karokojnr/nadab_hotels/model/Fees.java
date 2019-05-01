package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Fees {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    @SerializedName("fees")
    private ArrayList<Fee> fees;

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Fee> getFees() {
        return fees;
    }
}
