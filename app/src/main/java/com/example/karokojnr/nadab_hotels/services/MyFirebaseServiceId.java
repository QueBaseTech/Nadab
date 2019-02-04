package com.example.karokojnr.nadab_hotels.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.karokojnr.nadab_hotels.HomeActivity;
import com.example.karokojnr.nadab_hotels.LoginActivity;
import com.example.karokojnr.nadab_hotels.utils.Constants;
import com.example.karokojnr.nadab_hotels.utils.utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.InstanceIdResult;

public class MyFirebaseServiceId extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                if (!task.isSuccessful()) {
                    Log.w("FCM_TOKEN", "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();
                Log.d("FCM_TOKEN", "Token:: "+token);
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                preferences.edit().putString(Constants.FIREBASE_TOKEN, token).apply();
                utils.sendRegistrationToServer(getApplicationContext(), token);
            }
        });
    }
}