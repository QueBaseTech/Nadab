package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

public class Year {
    @SerializedName("January")
    private Stat january;

    @SerializedName("Feb")
    private Stat feb;

    @SerializedName("March")
    private Stat march;

    @SerializedName("April")
    private Stat april;

    @SerializedName("May")
    private Stat may;

    @SerializedName("June")
    private Stat june;

    @SerializedName("July")
    private Stat july;

    @SerializedName("August")
    private Stat august;

    @SerializedName("September")
    private Stat september;

    @SerializedName("October")
    private Stat october;

    @SerializedName("November")
    private Stat november;

    @SerializedName("December")
    private Stat december;

    public Stat getJanuary() {
        return january;
    }

    public Stat getFeb() {
        return feb;
    }

    public Stat getMarch() {
        return march;
    }

    public Stat getApril() {
        return april;
    }

    public Stat getMay() {
        return may;
    }

    public Stat getJune() {
        return june;
    }

    public Stat getJuly() {
        return july;
    }

    public Stat getAugust() {
        return august;
    }

    public Stat getSeptember() {
        return september;
    }

    public Stat getOctober() {
        return october;
    }

    public Stat getNovember() {
        return november;
    }

    public Stat getDecember() {
        return december;
    }
}
