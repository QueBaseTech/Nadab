package com.example.karokojnr.nadab_hotels;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.utils.Constants;
import com.example.karokojnr.nadab_hotels.utils.HotelSharedPreference;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;


public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_name,tv_email,tv_message,tv_applicant_name, tv_city,tv_address, tv_mobile, tv_paybill;
    private SharedPreferences pref;
    private Button btn_change_password;
    private FloatingActionButton btn_logout;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


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


        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_email = (TextView)findViewById(R.id.tv_email);
        tv_applicant_name = (TextView)findViewById ( R.id.tv_applicant_name );
        tv_city = (TextView)findViewById ( R.id.tv_city );
        tv_address = (TextView)findViewById ( R.id.tv_address );
        tv_paybill = (TextView)findViewById ( R.id.tv_paybill );
        tv_mobile = (TextView)findViewById ( R.id.tv_mobile );
        imageView = (ImageView)findViewById ( R.id.ivImage );



        //getting the current user
        HotelSharedPreference hotel = SharedPrefManager.getInstance(this).getHotel ();
        tv_name.setText(String.valueOf(hotel.getUsername ()));
        tv_email.setText(String.valueOf ( hotel.getEmail ()));
        tv_applicant_name.setText(String.valueOf ( hotel.getTv_applicant_name ()));
        tv_city.setText(String.valueOf ( hotel.getTv_city ()));
        tv_address.setText(String.valueOf ( hotel.getTv_address ()));
        tv_paybill.setText(String.valueOf ( hotel.getTv_paybill ()));
        tv_mobile.setText(String.valueOf ( hotel.getTv_mobile()));
        Glide.with(this)
                .load(RetrofitInstance.BASE_URL+"images/uploads/hotels/"+ String.valueOf ( hotel.getIvImage ()))
                .into(imageView);


        btn_change_password = (Button)findViewById(R.id.btn_chg_password);
        btn_logout = (FloatingActionButton) findViewById(R.id.btn_logout);
        btn_change_password.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //changePasswordProcess (  );
            }
        } );
        btn_logout.setOnClickListener ( this);




    }
    public void onClick (View v){
        switch (v.getId ()){
            case R.id.btn_logout:
                // Create an AlertDialog.Builder and set the message, and click listeners
                // for the postivie and negative buttons on the dialog.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to logout?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked the "Cancel" button, so dismiss the dialog
                        // and continue editing the items.
                        if (dialog != null) {
                            dialog.dismiss();
                        }
                    }
                });

                // Create and show the AlertDialog
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


                break;


        }


    }
    private void showDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_change_password, null);
        et_old_password = (EditText)view.findViewById(R.id.et_old_password);
        et_new_password = (EditText)view.findViewById(R.id.et_new_password);
        tv_message = (TextView)view.findViewById(R.id.tv_message);
        progress = (ProgressBar)view.findViewById(R.id.progress);
        builder.setView(view);
        builder.setTitle("Change Password");
        builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String old_password = et_old_password.getText().toString();
                String new_password = et_new_password.getText().toString();
                if(!old_password.isEmpty() && !new_password.isEmpty()){

                    progress.setVisibility(View.VISIBLE);
                    //changePasswordProcess();

                }else {

                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText("Fields are empty");
                }
            }
        });
    }
   /* @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_chg_password:
                showDialog();
                break;
            case R.id.btn_logout:
                logout();
                break;
        }
    }
*/

    private void goToLogin() {
        Intent intent = new Intent ( this, LoginActivity.class );
        startActivity ( intent );

            /*Fragment login = new LoginFragment();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame,login);
            ft.commit();*/
    }
    private void logout() {
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.UNIQUE_ID,"");
        editor.apply();
        goToLogin();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    /*private void changePasswordProcess(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HotelService requestInterface = retrofit.create(HotelService.class);

        Hotel hotel = new Hotel();
        hotel.setBusinessName (email;);
        hotel.setPassword (old_password;);
        hotel.setNew_password(new_password);
        HotelRegister request = new HotelRegister ();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setUser(hotel);
        Call<Hotel> response = requestInterface.operation(request);

        response.enqueue(new Callback<Hotel>() {
            @Override
            public void onResponse(Call<Hotel> call, retrofit2.Response<Hotel> response) {

                Hotel resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());

            }
        });
    }*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.optionsmenu, menu);

        return super.onCreateOptionsMenu ( menu );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        switch (item.getItemId()) {

            case R.id.change_profile:
                Intent intent = new Intent(this, ChangeProfile.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.logout:
                // Log.wtf(TAG, "onOptionsItemSelected: Logout");
                SharedPrefManager.getInstance ( getApplicationContext () ).logout ();
                startActivity ( new Intent ( getApplicationContext (), LoginActivity.class ) );
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
