package com.example.karokojnr.nadab;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karokojnr.nadab.api.HotelService;
import com.example.karokojnr.nadab.api.RetrofitInstance;
import com.example.karokojnr.nadab.model.Hotel;
import com.example.karokojnr.nadab.model.Login;
import com.example.karokojnr.nadab.utils.Constants;
import com.example.karokojnr.nadab.utils.SharedPrefManager;
import com.example.karokojnr.nadab.utils.utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button btn_login;
    private ProgressBar mLoading;
    private static final String TAG = "LoginActivity";
    private TextView mRegister;
    private SharedPreferences mSharePrefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        email = (EditText) findViewById ( R.id.et_email );
        password = (EditText) findViewById ( R.id.et_password );
        mRegister = (TextView) findViewById ( R.id.tv_register );
        btn_login = (Button)findViewById ( R.id.btn_login );
        mLoading = (ProgressBar) findViewById(R.id.login_loading);

        mSharePrefs = getApplicationContext().getSharedPreferences(Constants.M_SHARED_PREFERENCE, MODE_PRIVATE);
        editor = mSharePrefs.edit();

        // Redirect home if is logged in
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {
            goHome();
            finish();
        }

        btn_login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                    //first getting the values
                String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();

                    //validating inputs
                    if (TextUtils.isEmpty(mEmail)) {
                        email.setError("Please enter your username");
                        email.requestFocus();
                        return;
                    }

                     if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                    email.setError("Enter a valid email");
                    email.requestFocus();
                    return;
                    }

                    if (TextUtils.isEmpty(mPassword)) {
                        password.setError("Please enter your password");
                        password.requestFocus();
                        return;
                    }
                mLoading.setVisibility(View.VISIBLE); // show progress dialog*/



                HotelService service = RetrofitInstance.getRetrofitInstance().create(HotelService.class);
                Call<Login> call = service.login(mEmail, mPassword);

                call.enqueue(new Callback<Login> () {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful()) {
                            String token = response.body().getToken();
                            Hotel hotel = response.body().getHotel();

                            // Persist to local storage
                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(hotel, token);
                            mLoading.setVisibility(View.GONE);
                           // Start Home activity
                            goHome();
                        } else {
                            mLoading.setVisibility(View.INVISIBLE);
                            Toast.makeText(LoginActivity.this, "Error logging in...", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        mLoading.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
            }
        } );
        mRegister.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( LoginActivity.this, RegisterActivity.class );
                startActivity ( intent );
            }
        } );

    }

    private void goHome() {
        Intent intent = new Intent ( LoginActivity.this, HomeActivity.class );
        startActivity ( intent );
    }


}
