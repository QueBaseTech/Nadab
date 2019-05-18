package com.example.karokojnr.nadab_hotels;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.HotelResponse;
import com.example.karokojnr.nadab_hotels.utils.HotelSharedPreference;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfile  extends AppCompatActivity implements View.OnClickListener{

    private EditText tv_user_name,tv_user_email,tv_message, tv_user_mobile;
    private EditText tv_business_name, tv_applicant_name, tv_business_email , tv_mobile_no , tv_city, tv_address, tv_paybill_no;
    private SharedPreferences pref;
    private Button btn_change_password;
    private FloatingActionButton btn_logout;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar mLoading;
    ProgressDialog progressDialog;
    ImageView imageView;
    private Handler handler = new Handler ();
    private int progressStatus = 0;
    private String pUserName, pEmail, pMobileNumber, pUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
        getSupportActionBar ().setDisplayShowHomeEnabled ( true );
        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) { finish (); }
        } );


        tv_business_name = (EditText) findViewById(R.id.tv_name);
        tv_applicant_name = (EditText) findViewById(R.id.tv_applicant_name);
        tv_business_email = (EditText) findViewById ( R.id.tv_email );
        tv_mobile_no = (EditText) findViewById ( R.id.tv_mobile );
        tv_city = (EditText) findViewById ( R.id.tv_city );
        tv_address = (EditText) findViewById ( R.id.tv_address );
        tv_paybill_no = (EditText) findViewById ( R.id.tv_paybill );



        imageView = (ImageView)findViewById ( R.id.ivImage );
        progressDialog = new ProgressDialog (this);
        mLoading = (ProgressBar) findViewById(R.id.edit_loading);

        //getting the current user
        HotelSharedPreference hotel = SharedPrefManager.getInstance(this).getHotel ();
        tv_business_name.setText(String.valueOf(hotel.getUsername ()));
        tv_business_email.setText(String.valueOf ( hotel.getEmail ()));
        tv_applicant_name.setText(String.valueOf ( hotel.getTv_applicant_name ()));
        tv_city.setText(String.valueOf ( hotel.getTv_city ()));
        tv_address.setText(String.valueOf ( hotel.getTv_address ()));
        tv_paybill_no.setText(String.valueOf ( hotel.getTv_paybill ()));
        tv_mobile_no.setText(String.valueOf ( hotel.getTv_mobile()));
        Glide.with(this)
                .load( RetrofitInstance.BASE_URL+"images/uploads/hotels/"+ String.valueOf ( hotel.getIvImage ()))
                .into(imageView);

        pUserId = SharedPrefManager.getInstance(ChangeProfile.this).getHotel ().getId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.changeprofile_menu, menu);
        return super.onCreateOptionsMenu ( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_change:
                updateProfile ();
                Intent intent = new Intent ( this, ProfileActivity.class );
                startActivity ( intent );
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateProfile() {
        String businessName = tv_business_name.getText ().toString ().trim ();
        String applicantName = tv_applicant_name.getText ().toString ().trim ();
        String businessEmail = tv_business_email.getText ().toString ().trim ();
        String mobileNumber = tv_mobile_no.getText ().toString ().trim ();
        String city = tv_city.getText ().toString ().trim ();
        String address = tv_address.getText ().toString ().trim ();
        String paybillNumber = tv_paybill_no.getText ().toString ().trim ();

        // MultipartBody.Part fileToUpload;
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        //RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), image);
        RequestBody mbusinessName = RequestBody.create(MediaType.parse("text/plain"), businessName.getBytes ().toString());
        RequestBody mapplicantName = RequestBody.create(MediaType.parse("text/plain"), applicantName.getBytes ().toString());
        RequestBody mbusinessEmail = RequestBody.create(MediaType.parse("text/plain"), businessEmail);
        RequestBody mmobileNumber = RequestBody.create(MediaType.parse("text/plain"), mobileNumber);
        RequestBody mcity = RequestBody.create(MediaType.parse("text/plain"), city);
        RequestBody maddress = RequestBody.create(MediaType.parse("text/plain"), address);
        RequestBody mpaybillNumber = RequestBody.create(MediaType.parse("text/plain"), paybillNumber);

        RequestBody userId = RequestBody.create(MediaType.parse("text/plain"), pUserId);
        final String authToken = SharedPrefManager.getInstance(ChangeProfile.this).getToken();

        Call<HotelResponse> call = service.profileEdit(authToken, pUserId, mbusinessName, mapplicantName, mbusinessEmail,mmobileNumber,mcity,maddress,mpaybillNumber);


        call.enqueue ( new Callback<HotelResponse> () {
            @Override
            public void onResponse(Call<HotelResponse> call, Response<HotelResponse> response) {
                if (response.isSuccessful ()) {
                    assert response.body () != null;
                    SharedPrefManager.getInstance(ChangeProfile.this).userLogin(response.body().getHotel (), authToken);
                    Toast.makeText ( ChangeProfile.this, "Profile edited successfully...", Toast.LENGTH_SHORT ).show ();
                    finish();
                    Intent intent = new Intent ( getApplicationContext (), ProfileActivity.class );
                    startActivity ( intent );
                } else {
                    hideProgressDialogWithTitle ();
                    Toast.makeText ( ChangeProfile.this, "Error editing...", Toast.LENGTH_SHORT ).show ();
                }
            }

            @Override
            public void onFailure(Call<HotelResponse> call, Throwable t) {
                hideProgressDialogWithTitle ();
                Toast.makeText ( ChangeProfile.this, "Something went wrong...Error message: " + t.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void showProgressDialogWithTitle() {
        progressDialog.setTitle("Editing Profile");
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.show();

        // Start Process Operation in a background thread
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus < 100) {
                    try{
                        // This is mock thread using sleep to show progress
                        Thread.sleep(200);
                        progressStatus += 5;
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                    // Change percentage in the progress bar
                    handler.post(new Runnable() {
                        public void run() {
                            progressDialog.setProgress(progressStatus);
                        }
                    });
                }
                //hide Progressbar after finishing process
                hideProgressDialogWithTitle();
            }
        }).start();

    }

    // Method to hide/ dismiss Progress bar
    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }
    @Override
    public void onClick(View v) {

    }
}