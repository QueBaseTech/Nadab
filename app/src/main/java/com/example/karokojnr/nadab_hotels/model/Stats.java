package com.example.karokojnr.nadab_hotels.model;

import com.google.gson.annotations.SerializedName;

public class Stats {
    @SerializedName("today")
    private Stat today;

    @SerializedName("currentWeek")
    private Stat currentWeek;

    @SerializedName("currentMonth")
    private Stat currentMonth;

    @SerializedName("weekly")
    private Stat weekly;

    @SerializedName("2018")
    private Year year2018;

    @SerializedName("2019")
    private Year year2019;

    @SerializedName("2020")
    private Year year2020;

    @SerializedName("2021")
    private Year year2021;

    public Stat getToday() {
        return today;
    }

    public Stat getCurrentWeek() {
        return currentWeek;
    }

    public Stat getCurrentMonth() {
        return currentMonth;
    }

    public Stat getWeekly() {
        return weekly;
    }

    public Year getYear2018() {
        return year2018;
    }

    public Year getYear2019() {
        return year2019;
    }

    public Year getYear2020() {
        return year2020;
    }

    public Year getYear2021() {
        return year2021;
    }
}

