package com.example.karokojnr.nadab_hotels;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.adapter.ItemsAdapter;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Product;
import com.example.karokojnr.nadab_hotels.utils.HotelSharedPreference;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;

import static com.example.karokojnr.nadab_hotels.MainActivity.tabLayout;


public class AddMeals extends AppCompatActivity implements View.OnClickListener, EasyPermissions.PermissionCallbacks ,
        NavigationView.OnNavigationItemSelectedListener {


    private ArrayList<Product> productList = new ArrayList<> ();
    private ItemsAdapter adapter;
    private static final String TAG = "Items";
    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView image;
    private Button btn_okay, btn_cancel;
    private EditText name, price;
    private Uri selectedImage;
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private String filePath;
    private File file;
    LinearLayout layout_add_meals;
    private static Animation shakeAnimation;
    private ProgressBar mLoading;
    private int progressStatus = 0;
    ProgressDialog progressDialog;
    private Handler handler = new Handler ();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_meals );

        progressDialog = new ProgressDialog (this);
        layout_add_meals = (LinearLayout) findViewById ( R.id.layout_add_meals );


        mLoading = (ProgressBar) findViewById(R.id.login_loading);


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


        name = (EditText) findViewById ( R.id.name );
        price = (EditText) findViewById ( R.id.add_item_price );

        image = (ImageView) findViewById ( R.id.ivImage );


        // Load ShakeAnimation
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext (),
                R.anim.shake);

        image.setOnClickListener (  this );
        final RelativeLayout container = (RelativeLayout) findViewById(R.id.container);


        FloatingTextButton btn_okay = (FloatingTextButton) findViewById(R.id.btn_ok);
        btn_okay.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //first getting the values
                String mname = name.getText().toString();
                String mprice = price.getText().toString();
                String mimage = image.getDrawable ().toString();

                if (mname.equals("") || mname.length() == 0
                        || mimage.equals("") || mimage.length() == 0
                        || mprice.equals("") || mprice.length() == 0) {
                    layout_add_meals.startAnimation ( shakeAnimation );

                    {

                        Snackbar snackbar = Snackbar.make ( layout_add_meals, "All fields are required!", Snackbar.LENGTH_LONG ).setAction ( "RETRY", new View.OnClickListener () {
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

                //validating inputs
                if (TextUtils.isEmpty(  mname )) {
                    name.setError("Please enter Food name");
                    name.requestFocus();
                    return;
                }



                if (TextUtils.isEmpty(  mprice )) {
                    price.setError("Please enter price of the food");
                    price.requestFocus();
                    return;
                }

              //  mLoading.setVisibility(View.VISIBLE); // show progress dialog*/
                showProgressDialogWithTitle (  );

                Snackbar.make(container, "Adding Meal...", Snackbar.LENGTH_SHORT).show();

                HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
                Product product = new Product(
                        name.getText ().toString ().trim (),
                        price.getText ().toString ().trim (),
                        image.getDrawable ().toString ().trim ()
                );
                //Set defaults
                HotelSharedPreference hotelSharedPreference = SharedPrefManager.getInstance(AddMeals.this).getHotel();
                String hotelId = hotelSharedPreference.getId();
                productList.add (product);
                String filePath = getRealPathFromURIPath(selectedImage, AddMeals.this);
                File file = new File(filePath);
                RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("image", file.getName(), mFile);
                RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
                RequestBody mName = RequestBody.create(MediaType.parse("text/plain"), name.getText().toString());
                RequestBody mPrice = RequestBody.create(MediaType.parse("text/plain"), price.getText().toString());
                RequestBody mUnitMeasure = RequestBody.create(MediaType.parse("text/plain"), "Box");
                RequestBody mHotelId = RequestBody.create(MediaType.parse("text/plain"), hotelId);
                Call<Product> call = service.addProduct(fileToUpload, filename, mName, mPrice, mUnitMeasure, mHotelId);

                call.enqueue ( new Callback<Product> () {
                    @Override
                    public void onResponse(Call<Product> call, Response<Product> response) {
                        if (response.isSuccessful ()) {
                            assert response.body () != null;
                            //mLoading.setVisibility(View.GONE);
                            hideProgressDialogWithTitle ();
                            Toast.makeText ( AddMeals.this, "Added Successfully...", Toast.LENGTH_SHORT ).show ();
                            Intent intent = new Intent ( getApplicationContext (), MainActivity.class );
                            startActivity ( intent );
                            //notify data set changed in RecyclerView adapter
//                            adapter.notifyDataSetChanged ();
                        } else {
                           // mLoading.setVisibility(View.INVISIBLE);
                            hideProgressDialogWithTitle ();
                            Toast.makeText ( AddMeals.this, "Error adding...", Toast.LENGTH_SHORT ).show ();
                        }
                    }

                    @Override
                    public void onFailure(Call<Product> call, Throwable t) {
//                        mLoading.setVisibility(View.INVISIBLE);
                        hideProgressDialogWithTitle ();
                        Toast.makeText ( AddMeals.this, "Something went wrong...Error message: " + t.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        } );
        /*btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });*/
        //btn_cancel.setOnClickListener ( this );
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
            image.setImageURI(selectedImage);
        }
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){
            selectedImage = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                filePath = getRealPathFromURIPath(selectedImage, AddMeals.this);
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
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AddMeals.this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if (selectedImage != null) {
            filePath = getRealPathFromURIPath(selectedImage, AddMeals.this);
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
        progressDialog.setTitle("Adding Meal");
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
            startActivity(new Intent(AddMeals.this, MainActivity.class));

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(AddMeals.this, ProfileActivity.class));
        } else if (id == R.id.nav_add_meals) {
            startActivity(new Intent(AddMeals.this, AddMeals.class));
        } else if (id == R.id.nav_sign_out) {
            // Log.wtf(TAG, "onOptionsItemSelected: Logout");
            SharedPrefManager.getInstance ( getApplicationContext () ).logout ();
            startActivity ( new Intent ( getApplicationContext (), LoginActivity.class ) );
            finish();
        }else if (id == R.id.terms_conditions){
            startActivity(new Intent(AddMeals.this, Terms.class));

        }
        this.finish();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
