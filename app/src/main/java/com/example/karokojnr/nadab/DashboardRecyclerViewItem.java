package com.example.karokojnr.nadab;

public class DashboardRecyclerViewItem {

    // Save  name.
    private String name;

    // Save  image resource id.
    private int dashboardImage;

    public DashboardRecyclerViewItem(String name, int dashboardImage) {
        this.name = name;
        this.dashboardImage = dashboardImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDashboardImage() {
        return dashboardImage;
    }

    public void setDashboardImage(int dashboardImage) {
        this.dashboardImage = dashboardImage;
    }
}
