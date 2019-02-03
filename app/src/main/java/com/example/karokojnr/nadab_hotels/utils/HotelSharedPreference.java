package com.example.karokojnr.nadab_hotels.utils;

public class HotelSharedPreference {
    private String id, username, email, tv_applicant_name, tv_city,tv_address, tv_mobile, tv_paybill, ivImage;

    public String getIvImage() {
        return ivImage;
    }

    public HotelSharedPreference(String id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.tv_applicant_name = tv_applicant_name;
        this.tv_city = tv_city;
        this.tv_address = tv_address;
        this.tv_mobile = tv_mobile;
        this.tv_paybill = tv_paybill;
        this.ivImage = ivImage;

    }

    public String getTv_applicant_name() {
        return tv_applicant_name;
    }

    public String getTv_city() {
        return tv_city;
    }

    public String getTv_address() {
        return tv_address;
    }

    public String getTv_mobile() {
        return tv_mobile;
    }

    public String getTv_paybill() {
        return tv_paybill;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
