package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

public class StatsResponse {
    @SerializedName("success")
    private boolean isSuccessful;

    @SerializedName("message")
    private String message;

    @SerializedName("stats")
    private Stats stats;

    public StatsResponse(boolean isSuccessful, String message, Stats stats) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.stats = stats;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public Stats getStats() {
        return stats;
    }
}
