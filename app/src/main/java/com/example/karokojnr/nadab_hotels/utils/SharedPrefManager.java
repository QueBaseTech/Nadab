package com.example.karokojnr.nadab_hotels.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.karokojnr.nadab_hotels.model.Hotel;
import com.example.karokojnr.nadab_hotels.model.Product;


//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "NADAB_USER";

    private static final String KEY_ID = "KEY_ID";
    private static final String KEY_USERNAME = "KEY_USERNAME";
    private static final String KEY_EMAIL = "KEY_EMAIL";
    private static final String KEY_APPLICANT_NAME = "keyapplicantname";
    private static final String KEY_CITY = "keycity";
    private static final String KEY_ADDRESS = "keyaddress";
    private static final String KEY_PAYBILL = "keypaybill";
    private static final String KEY_MOBILE = "keymobile";
    private static final String KEY_PROFILE = "keyprofile";

    private static final String KEY_MEAL_NAME = "keymealname";
    private static final String KEY_PRICE = "keyprice";
    private static final String KEY_IMAGE = "keyimage";



    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //method to let the hotel login
    //this method will store the hotel data in shared preferences
    public void userLogin(Hotel hotel, String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString (KEY_ID, hotel.getId ());
        editor.putString(KEY_USERNAME, hotel.getBusinessName ());
        editor.putString(KEY_EMAIL, hotel.getBusinessEmail ());
        editor.putString (KEY_APPLICANT_NAME, hotel.getApplicantName ());
        editor.putString (KEY_CITY, hotel.getCity ());
        editor.putString (KEY_ADDRESS, hotel.getAddress ());
        editor.putString (KEY_PAYBILL, hotel.getPayBillNo ());
        editor.putString (KEY_MOBILE, hotel.getMobileNumber ());
        editor.putString (KEY_PROFILE, hotel.getProfile ());
        editor.putString (Constants.M_SHARED_PREFERENCE_LOGIN_TOKEN, token);
        editor.apply();
        editor.commit();
    }

    //this method will checker whether hotel is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.M_SHARED_PREFERENCE_LOGIN_TOKEN, null) != null;
    }

    public String getToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.M_SHARED_PREFERENCE_LOGIN_TOKEN, null);
    }

    //this method will give the logged in user
    public HotelSharedPreference getHotel() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new HotelSharedPreference (
                sharedPreferences.getString (KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_APPLICANT_NAME, null),
                sharedPreferences.getString(KEY_MOBILE, null),
                sharedPreferences.getString(KEY_PAYBILL, null),
                sharedPreferences.getString(KEY_CITY, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_PROFILE, null),
                sharedPreferences.getString(KEY_EMAIL, null) );
    }


    public Product getMeal() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Product (
                sharedPreferences.getString (KEY_MEAL_NAME, null),
                sharedPreferences.getString(KEY_PRICE, null),
                sharedPreferences.getString(KEY_IMAGE, null)

        );
    }

    //this method will logout the hotel
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}