package com.example.karokojnr.nadab_hotels;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Hotel;
import com.example.karokojnr.nadab_hotels.model.HotelRegister;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mobileNumber, businessName, applicantName, paybillNumber, address, businessEmail, city, password, passwordAgain;
    Button addHotel;
    ProgressBar mLoading;
    ImageView ivImage;
    TextView ti_first;

    private static final String TAG = "Profile";
    private static final int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private String filePath;
    private File file;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );

        progressDialog = new ProgressDialog(this);
//        TODO:: initi UI components
        ti_first = (TextView)findViewById ( R.id.ti_first );
        mLoading = (ProgressBar) findViewById(R.id.login_loading);

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
        ivImage = (ImageView)findViewById ( R.id.ivImage );
        ivImage.setOnClickListener (  this );



    }

    public void addHotel(View view) {

        /*// display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog*/

        HotelService service = RetrofitInstance.getRetrofitInstance().create(HotelService.class);
        final Hotel hotel = new Hotel();



        String mimage = ivImage.getDrawable().toString();
        String mmobileNumber = mobileNumber.getText ().toString();
        String mbusinessName = businessName.getText().toString();
        String mapplicantName = applicantName.getText().toString();
        String mpaybillNumber = paybillNumber.getText().toString();
        String maddress = address.getText().toString();
        String mbusinessEmail = businessEmail.getText().toString();
        String mcity = city.getText().toString();
        String mpassword = password.getText().toString();
        String mpasswordAgain = passwordAgain.getText().toString();
        /*//validating inputs
        if (TextUtils.isEmpty(mimage)) {
            ivImage.setImageDrawable (Drawable.createFromPath ( "Please enter your username" ) );
            ivImage.requestFocus();
            return;
        }*/
        if (TextUtils.isEmpty(mbusinessEmail)) {
            businessEmail.setError("Please enter your username");
            businessEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(mbusinessEmail).matches()) {
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
       // mLoading.setVisibility(View.VISIBLE); // show progress dialog*/
        showProgressDialogWithTitle ();


        // TODO:: Fetch fields from form
        hotel.setProfile(mimage);
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
                    startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
                    //storing the user in shared preferences
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(hotel, "");
                   // SharedPrefManager.getInstance(getApplicationContext()).userLogin(hotel);
                   // mLoading.setVisibility(View.GONE);
                    hideProgressDialogWithTitle ();

                }
                else
                   // mLoading.setVisibility(View.INVISIBLE);
                    hideProgressDialogWithTitle ();
                Toast.makeText(RegisterActivity.this,   "Error adding...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<HotelRegister> call, Throwable t) {
                //mLoading.setVisibility(View.INVISIBLE);
                hideProgressDialogWithTitle ();
                Toast.makeText(RegisterActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });

    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public void onClick (View v){
        switch (v.getId ()){
            case R.id.ivImage:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult ( galleryIntent, RESULT_LOAD_IMAGE  );
                break;

           /* case R.id.btn_ok:

                break;

            case R.id.btn_cancel:
                break;*/
        }


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData ();
            ivImage.setImageURI(selectedImage);
        }
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                filePath = getRealPathFromURIPath(selectedImage, RegisterActivity.this);
                file = new File (filePath);
                Log.d(TAG, "Filename " + file.getName());
            }else{
                EasyPermissions.requestPermissions(this, "This app needs access to your file storage so that it can read photos.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, RegisterActivity.this);
    }
    // Method to show Progress bar
    private void showProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //Without this user can hide loader by tapping outside screen
        progressDialog.setCancelable(false);
        //Setting Title
        progressDialog.setTitle("Logging In");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

    }

    // Method to hide/ dismiss Progress bar
    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }

}
