package com.example.karokojnr.nadab_seller;

import android.content.Intent;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.karokojnr.nadab_seller.fragments.LoginFragment;
import com.example.karokojnr.nadab_seller.fragments.ResetPasswordDialog;


public class MainActivity extends AppCompatActivity implements ResetPasswordDialog.Listener {

    public static final String TAG = MainActivity.class.getSimpleName();

    private LoginFragment mLoginFragment;
    private ResetPasswordDialog mResetPasswordDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (savedInstanceState == null) {

            loadFragment();
        }
    }

    private void loadFragment(){

        if (mLoginFragment == null) {

            mLoginFragment = new LoginFragment();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragmentFrame,mLoginFragment,LoginFragment.TAG).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        String data = intent.getData().getLastPathSegment();
        Log.d(TAG, "onNewIntent: "+data);

        mResetPasswordDialog = (ResetPasswordDialog) getFragmentManager().findFragmentByTag(ResetPasswordDialog.TAG);

        if (mResetPasswordDialog != null)
            mResetPasswordDialog.setToken(data);
    }

    @Override
    public void onPasswordReset(String message) {

        showSnackBarMessage(message);
    }

    private void showSnackBarMessage(String message) {

        Snackbar.make(findViewById(R.id.activity_main),message,Snackbar.LENGTH_SHORT).show();

    }
}
