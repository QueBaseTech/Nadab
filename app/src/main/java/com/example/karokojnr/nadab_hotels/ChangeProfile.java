package com.example.karokojnr.nadab_hotels;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.HotelResponse;
import com.example.karokojnr.nadab_hotels.utils.HotelSharedPreference;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeProfile  extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,View.OnClickListener{

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
    private String  pUserId, pHotelId;

    private static final int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage;
    private static final int READ_REQUEST_CODE = 300;
    private String filePath;
    private File file;
    private static final String TAG = "hotel";
    private String token;
    private String pName, pPrice,  pUnitMeasure, pImage;
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

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult ( galleryIntent, RESULT_LOAD_IMAGE  );
            }
        });


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
        pHotelId = SharedPrefManager.getInstance(ChangeProfile.this).getHotel ().getId();

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
        String image = imageView.getDrawable ().toString ().trim ();
        MultipartBody.Part fileToUpload;
        Call<HotelResponse> call;


        // MultipartBody.Part fileToUpload;
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        RequestBody filename = RequestBody.create ( MediaType.parse ( "text/plain" ), image );
        RequestBody mbusinessName = RequestBody.create ( MediaType.parse ( "text/plain" ), Arrays.toString ( businessName.getBytes () ) );
        RequestBody mapplicantName = RequestBody.create ( MediaType.parse ( "text/plain" ), Arrays.toString ( applicantName.getBytes () ) );
        RequestBody mbusinessEmail = RequestBody.create ( MediaType.parse ( "text/plain" ), Arrays.toString ( businessEmail.getBytes () ) );
        RequestBody mmobileNumber = RequestBody.create ( MediaType.parse ( "text/plain" ), Arrays.toString ( mobileNumber.getBytes () ) );
        RequestBody mcity = RequestBody.create ( MediaType.parse ( "text/plain" ), Arrays.toString ( city.getBytes () ) );
        RequestBody maddress = RequestBody.create ( MediaType.parse ( "text/plain" ), Arrays.toString ( address.getBytes () ) );
        RequestBody mpaybillNumber = RequestBody.create ( MediaType.parse ( "text/plain" ), Arrays.toString ( paybillNumber.getBytes () ) );
        RequestBody muserId = RequestBody.create ( MediaType.parse ( "text/plain" ), pUserId );
        RequestBody mhotelId = RequestBody.create ( MediaType.parse ( "text/plain" ), pHotelId );
        final String authToken = SharedPrefManager.getInstance ( ChangeProfile.this ).getToken ();



        if (selectedImage != null) {
            String filePath = getRealPathFromURIPath ( selectedImage, ChangeProfile.this );
            File file = new File ( filePath );
            RequestBody mFile = RequestBody.create ( MediaType.parse ( "image/*" ), file );
            fileToUpload = MultipartBody.Part.createFormData ( "image", file.getName (), mFile );

            call = service.hotelEditWithImage ( authToken, pHotelId, fileToUpload, filename,applicantName, businessEmail, mobileNumber, businessName, city, address, paybillNumber );

        }else {
            call = service.profileEdit ( authToken, pUserId, applicantName, businessEmail, mobileNumber, businessName, city, address, paybillNumber );

        }

            call.enqueue ( new Callback<HotelResponse> () {
                @Override
                public void onResponse(Call<HotelResponse> call, Response<HotelResponse> response) {
                    if (response.isSuccessful ()) {
                        assert response.body () != null;
                        SharedPrefManager.getInstance ( ChangeProfile.this ).userLogin ( response.body ().getHotel (), authToken );
                        Toast.makeText ( ChangeProfile.this, "Profile edited successfully...", Toast.LENGTH_SHORT ).show ();
                        finish ();
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
    private void showProgressDialogWithTitle() {
            progressDialog.setTitle("Editing Meal");
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




    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData ();
            imageView.setImageURI(selectedImage);
        }
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                filePath = getRealPathFromURIPath(selectedImage, ChangeProfile.this);
                file = new File(filePath);
                Log.d(TAG, "Filename " + file.getName());
            }else{
                EasyPermissions.requestPermissions(this, "This app needs access to your file storage so that it can read photos.", READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) { }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, ChangeProfile.this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (selectedImage != null) {
            filePath = getRealPathFromURIPath(selectedImage, ChangeProfile.this);
            file = new File(filePath);
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }

    @Override
    public void onClick(View v) {

        switch (v.getId ()) {
            case R.id.imageView:
                Intent galleryIntent = new Intent ( Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
                startActivityForResult ( galleryIntent, RESULT_LOAD_IMAGE );
                break;

        }
    }
}