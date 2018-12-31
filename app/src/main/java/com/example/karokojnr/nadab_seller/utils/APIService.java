package com.example.karokojnr.nadab_seller.utils;

import com.example.karokojnr.nadab_seller.model.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @POST("/login")
    @FormUrlEncoded
    Call<User> login(@Field("email") String email,
                        @Field("password") String password);
}
