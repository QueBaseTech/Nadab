package com.example.karokojnr.nadab.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.karokojnr.nadab.LoginActivity;
import com.example.karokojnr.nadab.model.Hotel;
import com.example.karokojnr.nadab.model.HotelRegister;


//here for this class we are using a singleton pattern

public class SharedPrefManager {

    //the constants
    private static final String SHARED_PREF_NAME = "simplifiedcodingsharedpref";
    private static final String KEY_USERNAME = "keyusername";
    private static final String KEY_EMAIL = "keyemail";
    //private static final String KEY_PAYBILL = "keypaybill";
    private static final String KEY_ID = "keyid";

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
    public void userLogin(Hotel hotel) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString (KEY_ID, hotel.getId ());
        editor.putString(KEY_USERNAME, hotel.getBusinessName ());
        editor.putString(KEY_EMAIL, hotel.getBusinessEmail ());
        //editor.putString (KEY_PAYBILL, hotel.getPayBillNo ());
        editor.apply();
    }

    //this method will checker whether hotel is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERNAME, null) != null;
    }

    //this method will give the logged in user
    public HotelSharedPreference getHotel() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new HotelSharedPreference (
                sharedPreferences.getString (KEY_ID, null),
                sharedPreferences.getString(KEY_USERNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null)
                //sharedPreferences.getString (KEY_PAYBILL, null)
        );
    }

    //this method will logout the hotel
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
}