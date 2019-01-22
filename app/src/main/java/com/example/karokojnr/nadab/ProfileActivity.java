package com.example.karokojnr.nadab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.karokojnr.nadab.api.HotelService;
import com.example.karokojnr.nadab.model.Hotel;
import com.example.karokojnr.nadab.model.HotelRegister;
import com.example.karokojnr.nadab.utils.Constants;
import com.example.karokojnr.nadab.utils.HotelSharedPreference;
import com.example.karokojnr.nadab.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileActivity extends AppCompatActivity {

    private TextView tv_name,tv_email,tv_message;
    private SharedPreferences pref;
    private AppCompatButton btn_change_password,btn_logout;
    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        /*//if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }*/

        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_email = (TextView)findViewById(R.id.tv_email);

        //getting the current user
        HotelSharedPreference hotel = SharedPrefManager.getInstance(this).getHotel ();

        /*//setting the values to the textviews
        //textViewId.setText(String.valueOf(user.getId()));
        tv_name.setText(hotel.getBusinessName ());
        tv_email.setText(hotel.getBusinessEmail ());
        //textViewGender.setText(user.getGender());*/

        //setting the values to the textviews
        tv_name.setText(String.valueOf(hotel.getUsername ()));
        tv_email.setText(String.valueOf ( hotel.getEmail ()));




        btn_change_password = (AppCompatButton)findViewById(R.id.btn_chg_password);
        btn_logout = (AppCompatButton)findViewById(R.id.btn_logout);
        btn_change_password.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                //changePasswordProcess (  );
            }
        } );
        btn_logout.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        } );




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
}
