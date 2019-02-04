package com.example.karokojnr.nadab.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.karokojnr.nadab.api.HotelService;
import com.example.karokojnr.nadab.api.RetrofitInstance;
import com.example.karokojnr.nadab.model.FCMToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class utils {

    /*
    * Check if the user is logged in
    * Return true or false
    * */
    public static final boolean isLoggedIn(Context context) {
        SharedPreferences sharedPreferences =   context.getSharedPreferences(Constants.M_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        Log.d("PREFs", "Token:: "+sharedPreferences.getString(Constants.M_SHARED_PREFERENCE_LOGIN_TOKEN, null));
        Log.d("PREFs", "Token:: "+sharedPreferences.getString(Constants.M_SHARED_PREFERENCE_HOTEL_ID, null));
        return sharedPreferences.getString(Constants.M_SHARED_PREFERENCE_LOGIN_TOKEN, null) != null;// return false if it's null
    }

    public static final String getToken(Context context) {
        SharedPreferences sharedPreferences =   context.getSharedPreferences(Constants.M_SHARED_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getString(Constants.M_SHARED_PREFERENCE_LOGIN_TOKEN, null);
    }

    public static void sendRegistrationToServer(final Context context, String token) {
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        String authToken = SharedPrefManager.getInstance(context).getToken();
        Call<FCMToken> call = service.sendTokenToServer(authToken, token);
        call.enqueue ( new Callback<FCMToken>() {
            @Override
            public void onResponse(Call<FCMToken> call, Response<FCMToken> response) {
                Log.wtf("TOKEN", "onResponse: Token sent" );
            }

            @Override
            public void onFailure(Call<FCMToken> call, Throwable t) {
                Toast.makeText ( context, "Something went wrong...Please try later!"+t.getMessage(), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    /*public static Task<InstanceIdResult> retriveFCMToken() {
        return FirebaseInstanceId.getInstance().getInstanceId();
    }*/

}
