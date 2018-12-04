package com.example.karokojnr.nadab_seller.network;

import com.example.karokojnr.nadab_seller.model.Hotel;
import com.example.karokojnr.nadab_seller.model.Response;


import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

public interface RetrofitInterface {

    @POST("register")
    Observable<Response> register(@Body Hotel hotel);

    @FormUrlEncoded
    @POST("/login")
    void Login (@Field("email") String email,
                @Field ( "password" )String password, Callback<Hotel>cb);
  //  Observable<Response> login();




//My profile
    @GET("users/{email}")
    Observable<Hotel> getProfile(@Path("email") String email);


//change password
    @PUT("users/{email}")
    Observable<Response> changePassword(@Path("email") String email, @Body Hotel hotel);

//reset password
    @POST("users/{email}/password")
    Observable<Response> resetPasswordInit(@Path("email") String email);

    @POST("users/{email}/password")
    Observable<Response> resetPasswordFinish(@Path("email") String email, @Body Hotel hotel);
}
