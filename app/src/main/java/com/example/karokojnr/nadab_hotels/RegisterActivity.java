package com.example.karokojnr.nadab_hotels;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Hotel;
import com.example.karokojnr.nadab_hotels.model.HotelRegister;
import com.example.karokojnr.nadab_hotels.utils.Constants;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;
import com.example.karokojnr.nadab_hotels.utils.utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText mobileNumber, businessName, applicantName, paybillNumber, address, businessEmail, city, password, passwordAgain;
    Button addHotel;
    ProgressBar mLoading;
    ImageView ivImage;
    TextView ti_first, login, readTerms;
    CheckBox terms_conditions;

    private LinearLayout linearLayout;
    private static final String TAG = "Profile";
    private static final int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private String filePath;
    private File file;
    private static Animation shakeAnimation;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_register );

        // Initialize all views
        linearLayout = (LinearLayout) findViewById(R.id
                .linear_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar ().setDisplayHomeAsUpEnabled ( true );
        getSupportActionBar ().setDisplayShowHomeEnabled ( true );

        toolbar.setNavigationIcon(R.drawable.ic_arrow);
        toolbar.setNavigationOnClickListener ( new View.OnClickListener () {

            @Override
            public void onClick(View view) {

                // Your code
                finish ();
            }
        } );


        progressDialog = new ProgressDialog(this);
//        TODO:: initi UI components
        ti_first = (TextView)findViewById ( R.id.ti_first );
        mLoading = (ProgressBar) findViewById(R.id.login_loading);
        login = (TextView) findViewById(R.id.already_user);
        login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent i = new Intent ( getApplicationContext (), LoginActivity.class );
                startActivity ( i );
            }
        } );

        ivImage = (ImageView)findViewById ( R.id.ivImage );
        ivImage.setOnClickListener (  this );
        mobileNumber = (EditText)findViewById ( R.id.mobileNumber );
        businessName = (EditText)findViewById ( R.id.businessName );
        applicantName = (EditText)findViewById ( R.id.applicantName );
        paybillNumber = (EditText)findViewById ( R.id.paybillNumber );
        address = (EditText)findViewById ( R.id.address );
        businessEmail = (EditText)findViewById ( R.id.businessEmail );
        city = (EditText)findViewById ( R.id.city );
        password = (EditText)findViewById ( R.id.password );
        passwordAgain = (EditText)findViewById ( R.id.passwordAgain );
        terms_conditions = (CheckBox) findViewById(R.id.terms_conditions);
        addHotel = (Button)findViewById ( R.id.addHotel );
        readTerms = (TextView)findViewById ( R.id.read_terms_conditions );
        readTerms.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent terms = new Intent ( getApplicationContext (), Terms.class );
                startActivity ( terms );
            }
        } );



        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext (),
                R.anim.shake);

        // Setting text selector over textviews
        @SuppressLint("ResourceType") XmlResourceParser xrp = getResources().getXml(R.drawable.text_selector);
        try {
            ColorStateList csl = ColorStateList.createFromXml(getResources(),
                    xrp);

            login.setTextColor(csl);
        } catch (Exception e) {
        }


    }

    public void addHotel(View view) {
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        final Hotel hotel = new Hotel ();

        String mimage = ivImage.getDrawable ().toString ();
        String mmobileNumber = mobileNumber.getText ().toString ();
        String mbusinessName = businessName.getText ().toString ();
        String mapplicantName = applicantName.getText ().toString ();
        String mpaybillNumber = paybillNumber.getText ().toString ();
        String maddress = address.getText ().toString ();
        String mbusinessEmail = businessEmail.getText ().toString ();
        String mcity = city.getText ().toString ();
        String mpassword = password.getText ().toString ();
        String mpasswordAgain = passwordAgain.getText ().toString ();

        // Pattern match for email id
        Pattern p = Pattern.compile ( Constants.regEx );
        Matcher m = p.matcher ( mbusinessEmail );
        // Check if all strings are null or not
        if (mbusinessName.equals ( "" ) || mbusinessName.length () == 0 || mimage.equals ( "" ) || mimage.length () == 0 || mbusinessEmail.equals ( "" ) || mbusinessEmail.length () == 0 || mapplicantName.equals ( "" ) || mapplicantName.length () == 0 || mpaybillNumber.equals ( "" ) || mpaybillNumber.length () == 0 || mcity.equals ( "" ) || mcity.length () == 0 || mmobileNumber.equals ( "" ) || mmobileNumber.length () == 0 || maddress.equals ( "" ) || maddress.length () == 0 || mpassword.equals ( "" ) || mpassword.length () == 0 || mpasswordAgain.equals ( "" ) || mpasswordAgain.length () == 0) {
            linearLayout.startAnimation ( shakeAnimation );

            {
                Snackbar snackbar = Snackbar.make ( linearLayout, "All fields are required!", Snackbar.LENGTH_LONG ).setAction ( "RETRY", new View.OnClickListener () {
                    @Override
                    public void onClick(View view) {
                    }
                } );
                // Changing message text color
                snackbar.setActionTextColor ( Color.GREEN );

                // Changing action button text color
                View sbView = snackbar.getView ();
                TextView textView = (TextView) sbView.findViewById ( android.support.design.R.id.snackbar_text );
                textView.setTextColor ( Color.RED );

                snackbar.show ();

            }
        }


            // Check if email id valid or not
        else if (!m.find())
           /* new CustomToast().Show_Toast(getApplicationContext (), view,
                    "Your Email Id is Invalid.");*/ {
            Snackbar snackbar = Snackbar.make ( linearLayout, "Your Email Id is Invalid!", Snackbar.LENGTH_LONG ).setAction ( "RETRY", new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                }
            } );
            // Changing message text color
            snackbar.setActionTextColor ( Color.GREEN );

            // Changing action button text color
            View sbView = snackbar.getView ();
            TextView textView = (TextView) sbView.findViewById ( android.support.design.R.id.snackbar_text );
            textView.setTextColor ( Color.RED );

            snackbar.show ();
        }

            // Check if both password should be equal
        else if (!mpasswordAgain.equals(mpassword))
           /* new CustomToast().Show_Toast(getApplicationContext (), view,
                    "Both password doesn't match.");*/
        {
            Snackbar snackbar = Snackbar.make ( linearLayout, "Both password doesn't match!", Snackbar.LENGTH_LONG ).setAction ( "RETRY", new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                }
            } );
            // Changing message text color
            snackbar.setActionTextColor ( Color.GREEN );

            // Changing action button text color
            View sbView = snackbar.getView ();
            TextView textView = (TextView) sbView.findViewById ( android.support.design.R.id.snackbar_text );
            textView.setTextColor ( Color.RED );

            snackbar.show ();
        }

            // Make sure user should check Terms and Conditions checkbox
        else if (!terms_conditions.isChecked())
            /*new CustomToast().Show_Toast(getApplicationContext (), view,
                    "Please select Terms and Conditions.");*/
        {
            Snackbar snackbar = Snackbar.make ( linearLayout, "Please select Terms and Conditions!", Snackbar.LENGTH_LONG ).setAction ( "RETRY", new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                }
            } );
            // Changing message text color
            snackbar.setActionTextColor ( Color.GREEN );

            // Changing action button text color
            View sbView = snackbar.getView ();
            TextView textView = (TextView) sbView.findViewById ( android.support.design.R.id.snackbar_text );
            textView.setTextColor ( Color.RED );

            snackbar.show ();
        }


        // Else do signup or do your stuff
        else
            Toast.makeText(getApplicationContext (), "Signing up......", Toast.LENGTH_SHORT)
                    .show();


        /*//validating inputs
        if (TextUtils.isEmpty(mimage)) {
            ivImage.setImageDrawable (Drawable.createFromPath ( "Please enter your username" ) );
            ivImage.requestFocus();
            return;
        }*/
        if (TextUtils.isEmpty(mbusinessEmail)) {
            businessEmail.setError("Please enter your business email");
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
            mobileNumber.setError("Please enter your mobile number");
            mobileNumber.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mbusinessName)) {
            businessName.setError("Please enter your business name");
            businessName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mcity)) {
            city.setError("Please enter your city");
            city.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(maddress)) {
            address.setError("Please enter your address");
            address.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mapplicantName)) {
            applicantName.setError("Please enter the applicant name");
            applicantName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mpaybillNumber)) {
            paybillNumber.setError("Please enter your paybill number");
            paybillNumber.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mpasswordAgain)) {
            passwordAgain.setError("Please confirm your password!");
            passwordAgain.requestFocus();
            return;
        }
        // Make sure user should check Terms and Conditions checkbox
        if (!terms_conditions.isChecked())
            /*new CustomToast().Show_Toast(getApplicationContext (), view,
                    "Please select Terms and Conditions.");*/
        {
            Snackbar snackbar = Snackbar.make ( linearLayout, "Please select Terms and Conditions!", Snackbar.LENGTH_LONG ).setAction ( "RETRY", new View.OnClickListener () {
                @Override
                public void onClick(View view) {
                }
            } );
            // Changing message text color
            snackbar.setActionTextColor ( Color.GREEN );

            // Changing action button text color
            View sbView = snackbar.getView ();
            TextView textView = (TextView) sbView.findViewById ( android.support.design.R.id.snackbar_text );
            textView.setTextColor ( Color.RED );

            snackbar.show ();
        }

        // mLoading.setVisibility(View.VISIBLE); // show progress dialog*/
        showProgressDialogWithTitle ();

        String filePath = getRealPathFromURIPath(selectedImage, this);
        File file = new File(filePath);
        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), mFile);
        hotel.setApplicantName(mapplicantName);
        hotel.setBusinessEmail(mbusinessEmail);
        hotel.setBusinessName(mbusinessName);
        hotel.setAddress(maddress);
        hotel.setCity(mcity);
        hotel.setPayBillNo(mpaybillNumber);
        hotel.setPassword(mpassword);

        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        RequestBody applicantName = RequestBody.create(MediaType.parse("text/plain"), hotel.getApplicantName());
        RequestBody businessEmail = RequestBody.create(MediaType.parse("text/plain"), hotel.getBusinessEmail());
        RequestBody businessName = RequestBody.create(MediaType.parse("text/plain"), hotel.getBusinessName());
        RequestBody address = RequestBody.create(MediaType.parse("text/plain"), hotel.getAddress());
        RequestBody city = RequestBody.create(MediaType.parse("text/plain"), hotel.getCity());
        RequestBody mobileNumber = RequestBody.create(MediaType.parse("text/plain"), mmobileNumber.trim());
        RequestBody payBill = RequestBody.create(MediaType.parse("text/plain"), hotel.getPayBillNo());
        RequestBody password = RequestBody.create(MediaType.parse("text/plain"), hotel.getPassword());


        Call<HotelRegister> call = service.addHotel(fileToUpload, filename, businessName, applicantName, payBill, mobileNumber, city, address, businessEmail, password);

        call.enqueue(new Callback<HotelRegister>() {
            @Override
            public void onResponse(Call<HotelRegister> call, Response<HotelRegister> response) {

                if(response.isSuccessful()) {
                    Log.d("JOA", "Hotel:: "+response.body().getHotel().toString());
                    Hotel hotel = response.body().getHotel();

                    //storing the user in shared preferences
                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(hotel, response.body().getToken());
                    hideProgressDialogWithTitle ();
                    sendToken();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
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
                Toast.makeText(RegisterActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_LONG).show();
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
        progressDialog.setTitle("Registering");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

    }

    // Method to hide/ dismiss Progress bar
    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }

}
