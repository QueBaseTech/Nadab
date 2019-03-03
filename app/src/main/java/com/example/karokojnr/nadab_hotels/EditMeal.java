package com.example.karokojnr.nadab_hotels;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Product;
import com.example.karokojnr.nadab_hotels.model.Products;
import com.example.karokojnr.nadab_hotels.utils.Constants;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;
import com.example.karokojnr.nadab_hotels.utils.Constants;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class EditMeal extends AppCompatActivity implements EasyPermissions.PermissionCallbacks{
    ImageView ivImage;
    EditText meal_name, price;
    FloatingTextButton edit, cancel;
    private String pName, pPrice, pHotelId, pUnitMeasure, pProductId, pImage;
    private ProgressBar mLoading;
    private int progressStatus = 0;
    ProgressDialog progressDialog;
    private Handler handler = new Handler ();

    private static final int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private String filePath;
    private File file;
    private static final String TAG = "Items";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_meal );

        progressDialog = new ProgressDialog (this);
        mLoading = (ProgressBar) findViewById(R.id.login_loading);

        ivImage = findViewById ( R.id.ivImage );
        meal_name = findViewById ( R.id.name );
        price = findViewById ( R.id.add_item_price );

        edit = findViewById ( R.id.btn_edit );
        cancel = findViewById ( R.id.btn_cancel );

        if(getIntent ().getExtras () != null ){
            Intent i = getIntent ();
            pImage = i.getStringExtra ( Constants.M_IMAGE );
            pName = i.getStringExtra ( Constants.M_NAME);
            pPrice = i.getStringExtra ( Constants.M_PRICE);
            pHotelId = i.getStringExtra ( Constants.M_HOTEL_ID);
            pUnitMeasure = i.getStringExtra( Constants.M_UNITMEASURE);
            pProductId = i.getStringExtra ( Constants.M_PRODUCT_ID );
        } else {
            pUnitMeasure = "";
            pHotelId = "";
            pPrice = "";
            pName = "";
            pImage = "";
            pProductId = "";
        }

        // Populate with defaults
//        ivImage.setImageDrawable ( Drawable.createFromPath ( pImage ) );
        meal_name.setText(pName);
        price.setText ( pPrice );

        // you will do the rest

        edit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                updateMeal ();
            }
        } );
//        ivImage.setOnClickListener ( this );
    }

    private void updateMeal() {
        String image = ivImage.getDrawable ().toString ().trim ();
        String mealName = meal_name.getText ().toString ().trim ();
        String mealPrice = price.getText ().toString ().trim ();

        if (mealName.isEmpty ()) {
            meal_name.setError ( "Name is required" );
            meal_name.requestFocus ();
            return;
        }


        if (mealPrice.isEmpty ()) {
            price.setError ( "Price required" );
            price.requestFocus ();
            return;
        }

        if (image.isEmpty ()) {
            ivImage.setImageDrawable ( Drawable.createFromPath ( "Image is required!" ) );
            ivImage.requestFocus ();
            return;
        }

        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), image);
        RequestBody mName = RequestBody.create(MediaType.parse("text/plain"), mealName);
        RequestBody mPrice = RequestBody.create(MediaType.parse("text/plain"), mealPrice);
        RequestBody mUnitMeasure = RequestBody.create(MediaType.parse("text/plain"), "Box");
        RequestBody mHotelId = RequestBody.create(MediaType.parse("text/plain"), pHotelId);

        String token = SharedPrefManager.getInstance ( getApplicationContext () ).getToken ();
        Call<Product> call = service.getProductsEdit(token, pProductId, filename, mName, mPrice);
// handle call que here...okay

        call.enqueue ( new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful ()) {
                    assert response.body () != null;
                    //mLoading.setVisibility(View.GONE);
                    hideProgressDialogWithTitle ();
                    Toast.makeText ( EditMeal.this, "Meal edited successfully...", Toast.LENGTH_SHORT ).show ();
                    Intent intent = new Intent ( getApplicationContext (), MainActivity.class );
                    startActivity ( intent );
                    //notify data set changed in RecyclerView adapter
//                            adapter.notifyDataSetChanged ();
                } else {
                    // mLoading.setVisibility(View.INVISIBLE);
                    hideProgressDialogWithTitle ();
                    Toast.makeText ( EditMeal.this, "Error editing...", Toast.LENGTH_SHORT ).show ();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
//                        mLoading.setVisibility(View.INVISIBLE);
                hideProgressDialogWithTitle ();
                Toast.makeText ( EditMeal.this, "Something went wrong...Error message: " + t.getMessage (), Toast.LENGTH_SHORT ).show ();
            }
        } );



    }
    //Upload Image
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

            case R.id.btn_ok:

                break;

            case R.id.btn_cancel:
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
                filePath = getRealPathFromURIPath(selectedImage, EditMeal.this);
                file = new File(filePath);
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
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, EditMeal.this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (selectedImage != null) {
            filePath = getRealPathFromURIPath(selectedImage, EditMeal.this);
            file = new File(filePath);
            Log.d(TAG, "Filename " + file.getName());
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
    }
    // Method to show Progress bar
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

    // Method to hide/ dismiss Progress bar
    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }
}
