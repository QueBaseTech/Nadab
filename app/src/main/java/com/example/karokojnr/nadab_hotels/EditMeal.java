package com.example.karokojnr.nadab_hotels;

import android.annotation.SuppressLint;
import android.graphics.Color;
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
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Product;
import com.example.karokojnr.nadab_hotels.model.Products;
import com.example.karokojnr.nadab_hotels.utils.Constants;
import com.example.karokojnr.nadab_hotels.utils.HotelSharedPreference;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
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

import static com.example.karokojnr.nadab_hotels.MainActivity.tabLayout;

public class EditMeal extends AppCompatActivity implements EasyPermissions.PermissionCallbacks,
        NavigationView.OnNavigationItemSelectedListener {
    ImageView ivImage;
    EditText meal_name, price;
    FloatingTextButton edit, cancel;
    private String pName, pPrice, pHotelId, pUnitMeasure, pProductId, pImage;
    private Boolean pStatus;
    private ProgressBar mLoading;
    private int progressStatus = 0;
    ProgressDialog progressDialog;
    private Handler handler = new Handler ();

    private static final int RESULT_LOAD_IMAGE = 1;
    private Uri selectedImage;
    private static final int READ_REQUEST_CODE = 300;
    private String filePath;
    private File file;
    private static final String TAG = "Items";
    private Switch toggleSellingStatus;
    private TextView sellingStatus;
    private String token;
    static ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_edit_meal );

        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById ( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.setDrawerListener ( toggle );
        toggle.syncState ();

        NavigationView navigationView = (NavigationView) findViewById ( R.id.nav_view );
        View headerView = navigationView.getHeaderView ( 0 );
        TextView navUsername = (TextView) headerView.findViewById ( R.id.navTextview );
        ImageView navImageview = (ImageView) headerView.findViewById ( R.id.imageView );
        navigationView.setNavigationItemSelectedListener(this);

        HotelSharedPreference hotel = SharedPrefManager.getInstance ( this ).getHotel ();
        navUsername.setText ( hotel.getUsername () );
        Glide.with ( this ).load ( RetrofitInstance.BASE_URL + "images/uploads/hotels/" + String.valueOf ( hotel.getIvImage () ) ).into ( navImageview );

        token = SharedPrefManager.getInstance ( getApplicationContext () ).getToken ();
        progressDialog = new ProgressDialog (this);
        mLoading = (ProgressBar) findViewById(R.id.edit_loading);
        toggleSellingStatus = findViewById(R.id.selling_switch);
        sellingStatus = findViewById(R.id.selling_status);

        ivImage = findViewById ( R.id.ivImage );
        meal_name = findViewById ( R.id.name );
        price = findViewById ( R.id.add_item_price );
        edit = findViewById ( R.id.btn_edit );


        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult ( galleryIntent, RESULT_LOAD_IMAGE  );
            }
        });

        if(getIntent ().getExtras () != null ){
            Intent i = getIntent ();
            pImage = i.getStringExtra ( Constants.M_IMAGE );
            pName = i.getStringExtra ( Constants.M_NAME);
            pPrice = i.getStringExtra ( Constants.M_PRICE);
            pHotelId = i.getStringExtra ( Constants.M_HOTEL_ID);
            pUnitMeasure = i.getStringExtra( Constants.M_UNITMEASURE);
            pProductId = i.getStringExtra ( Constants.M_PRODUCT_ID );
            pStatus = i.getBooleanExtra(Constants.M_STATUS, false);
        } else {
            pUnitMeasure = "";
            pHotelId = "";
            pPrice = "";
            pName = "";
            pImage = "";
            pProductId = "";
            pStatus = false;
        }

        toggleSellingStatus.setChecked(pStatus);
        updateSellingStatus(pStatus);

        meal_name.setText(pName);
        price.setText ( pPrice );
        Glide.with(this)
                .load(RetrofitInstance.BASE_URL+"images/uploads/products/thumb_"+pImage)
                .into(ivImage);

        edit.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                updateMeal ();
            }
        } );

        toggleSellingStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
                Call<Product> call = service.productEdit(token, pProductId, null, null, ""+isChecked);
                call.enqueue ( new Callback<Product>() {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful ()) {
                            updateSellingStatus(isChecked);
                        } else {
                            hideProgressDialogWithTitle ();
                            Toast.makeText ( EditMeal.this, "Error editing...", Toast.LENGTH_SHORT ).show ();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
                        hideProgressDialogWithTitle ();
                        Toast.makeText ( EditMeal.this, "Something went wrong...Error message: " + t.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void updateSellingStatus(Boolean status) {

        if(status) {
            sellingStatus.setText("IS SELLING");
            sellingStatus.setTextColor ( Color.GREEN );

        } else {
            sellingStatus.setText("NOT SELLING");
            sellingStatus.setTextColor ( Color.RED );
        }
    }

    private void updateMeal() {
        String image = ivImage.getDrawable ().toString ().trim ();
        String mealName = meal_name.getText ().toString ().trim ();
        String mealPrice = price.getText ().toString ().trim ();
        MultipartBody.Part fileToUpload;
        Call<Product> call;

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
        RequestBody mName = RequestBody.create(MediaType.parse("text/plain"), meal_name.getText().toString());
        RequestBody mPrice = RequestBody.create(MediaType.parse("text/plain"), price.getText().toString());
        RequestBody mUnitMeasure = RequestBody.create(MediaType.parse("text/plain"), pUnitMeasure);
        RequestBody mHotelId = RequestBody.create(MediaType.parse("text/plain"), pHotelId);

        if(selectedImage != null) {
            String filePath = getRealPathFromURIPath(selectedImage, EditMeal.this);
            File file = new File(filePath);
            RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
            fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), mFile);

            call = service.productEditWithImage(token, pProductId, fileToUpload, filename, mName, mPrice, mUnitMeasure, mHotelId);
        } else {
            call = service.productEdit(token, pProductId, mealName, mealPrice, null);
        }

        call.enqueue ( new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if (response.isSuccessful ()) {
                    assert response.body () != null;
                    hideProgressDialogWithTitle ();
                    Toast.makeText ( EditMeal.this, "Meal edited successfully...", Toast.LENGTH_SHORT ).show ();
                    Intent intent = new Intent ( getApplicationContext (), MainActivity.class );
                    startActivity ( intent );
                } else {
                    hideProgressDialogWithTitle ();
                    Toast.makeText ( EditMeal.this, "Error editing...", Toast.LENGTH_SHORT ).show ();
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                hideProgressDialogWithTitle ();
                Toast.makeText ( EditMeal.this, "Something went wrong...Error message: " + t.getMessage (), Toast.LENGTH_SHORT ).show ();
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
    public void onPointerCaptureChanged(boolean hasCapture) { }

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
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");
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

    // Method to hide/ dismiss Progress bar
    private void hideProgressDialogWithTitle() {
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.dismiss();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
// Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(EditMeal.this, MainActivity.class));
        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(EditMeal.this, ProfileActivity.class));
        } else if (id == R.id.nav_add_meals) {
            startActivity(new Intent(EditMeal.this, AddMeals.class));

        } else if (id == R.id.nav_sign_out) {
            // Log.wtf(TAG, "onOptionsItemSelected: Logout");
            SharedPrefManager.getInstance ( getApplicationContext () ).logout ();
            startActivity ( new Intent ( getApplicationContext (), LoginActivity.class ) );
            finish();
        }else if (id == R.id.terms_conditions){
            startActivity(new Intent(EditMeal.this, Terms.class));

        }
        this.finish();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
