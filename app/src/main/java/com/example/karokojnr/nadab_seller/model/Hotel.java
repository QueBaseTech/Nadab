package com.example.karokojnr.nadab_seller.model;


public class Hotel {

    private String businessName;
    private String applicantName;
    private String businessEmail;
    private String mobileNumber;
    private String paybillNo;
    private String address;
    private String city;
    private String password;
    private String paymentStatus;
    private String created_at;
    private String newPassword;
    private String token;

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public void setBusinessEmail(String businessEmail) {
        this.businessEmail = businessEmail;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPaybillNo(String paybillNo) {
        this.paybillNo = paybillNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }






    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessEmail() {
        return businessEmail;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
