package com.example.karokojnr.nadab;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karokojnr.nadab.api.HotelService;
import com.example.karokojnr.nadab.api.RetrofitInstance;
import com.example.karokojnr.nadab.model.Hotel;
import com.example.karokojnr.nadab.model.HotelRegister;
import com.example.karokojnr.nadab.utils.SharedPrefManager;
import com.example.karokojnr.nadab.utils.utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText mobileNumber, businessName, applicantName, paybillNumber, address, businessEmail, city, password, passwordAgain;
    private Button addHotel;
    public static final String TAG = RegisterActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );
//        TODO:: initi UI components

        mobileNumber = (EditText)findViewById ( R.id.mobileNumber );
        businessName = (EditText)findViewById ( R.id.businessName );
        applicantName = (EditText)findViewById ( R.id.applicantName );
        paybillNumber = (EditText)findViewById ( R.id.paybillNumber );
        address = (EditText)findViewById ( R.id.address );
        businessEmail = (EditText)findViewById ( R.id.businessEmail );
        city = (EditText)findViewById ( R.id.city );
        password = (EditText)findViewById ( R.id.password );
        passwordAgain = (EditText)findViewById ( R.id.passwordAgain );

        addHotel = (Button)findViewById ( R.id.addHotel );


    }

    public void addHotel(View view) {

        /*// display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog*/

        HotelService service = RetrofitInstance.getRetrofitInstance().create(HotelService.class);
        final Hotel hotel = new Hotel();



        String mmobileNumber = mobileNumber.getText ().toString();
        String mbusinessName = businessName.getText().toString();
        String mapplicantName = applicantName.getText().toString();
        String mpaybillNumber = paybillNumber.getText().toString();
        String maddress = address.getText().toString();
        String mbusinessEmail = businessEmail.getText().toString();
        String mcity = city.getText().toString();
        String mpassword = password.getText().toString();
        String mpasswordAgain = passwordAgain.getText().toString();
        //validating inputs
        if (TextUtils.isEmpty(mbusinessEmail)) {
            businessEmail.setError("Please enter your username");
            businessEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mbusinessEmail).matches()) {
            businessEmail.setError("Enter a valid businessEmail");
            businessEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(mpassword)) {
            password.setError("Please enter your password");
            password.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mmobileNumber)) {
            mobileNumber.setError("Please enter your password");
            mobileNumber.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mbusinessName)) {
            businessName.setError("Please enter your password");
            businessName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mcity)) {
            city.setError("Please enter your password");
            city.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(maddress)) {
            address.setError("Please enter your password");
            address.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mapplicantName)) {
            applicantName.setError("Please enter your password");
            applicantName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mpaybillNumber)) {
            paybillNumber.setError("Please enter your password");
            paybillNumber.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mpasswordAgain)) {
            passwordAgain.setError("Please enter your password");
            passwordAgain.requestFocus();
            return;
        }


        // TODO:: Fetch fields from form
        hotel.setApplicantName(mapplicantName);
        hotel.setBusinessEmail(mbusinessEmail);
        hotel.setBusinessName(mbusinessName);
        hotel.setAddress(maddress);
        hotel.setCity(mcity);
        hotel.setMobileNumber( Integer.parseInt ( mmobileNumber ) );
        hotel.setPayBillNo(mpaybillNumber);
        hotel.setPassword(mpassword);
        // TODO :: Remove all the hard coded values

        Call<HotelRegister> call = service.addHotel(hotel);

        call.enqueue(new Callback<HotelRegister>() {
            @Override
            public void onResponse(Call<HotelRegister> call, Response<HotelRegister> response) {

                if(response.isSuccessful()) {
                    Log.d("JOA", "Hotel:: "+response.body().getHotel().toString());
                    //storing the user in shared preferences
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(hotel, "");
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    sendToken();
                }
                else
                    Toast.makeText(RegisterActivity.this,   "Error adding...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HotelRegister> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void sendToken() {
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
                utils.sendRegistrationToServer(RegisterActivity.this, token);
            }
        });
    }
}
