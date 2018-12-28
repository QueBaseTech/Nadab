package com.example.karokojnr.nadab;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karokojnr.nadab.api.HotelService;
import com.example.karokojnr.nadab.api.RetrofitInstance;
import com.example.karokojnr.nadab.model.Login;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btn_login;
    TextView tiEmail, tiPassword, tiRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_login );

        email = (EditText) findViewById ( R.id.et_email );
        password = (EditText) findViewById ( R.id.et_password );
        tiRegister = (TextView) findViewById ( R.id.tv_register );
        btn_login = (Button)findViewById ( R.id.btn_login );

        btn_login.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                // display a progress dialog
                /*final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setCancelable(false); // set cancelable to false
                progressDialog.setMessage("Please Wait"); // set message
                progressDialog.show(); // show progress dialog*/


                String mEmail = email.getText().toString();
                String mPassword = password.getText().toString();

                HotelService service = RetrofitInstance.getRetrofitInstance().create(HotelService.class);
                Call<Login> call = service.login(mEmail, mPassword);

                call.enqueue(new Callback<Login> () {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        if (response.isSuccessful()) {
                            String authToken = response.body().getToken().getToken();
                            // TODO :: Persist token to SharedPreference
                            //  token.setText("TOKEN :: " + authToken);
                            Intent intent = new Intent ( LoginActivity.this, HomeActivity.class );
                            startActivity ( intent );
                        } else {
                            Toast.makeText(LoginActivity.this, "Error logging in...", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Something went wrong...Error message: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } );
        tiRegister.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent ( LoginActivity.this, RegisterActivity.class );
                startActivity ( intent );
            }
        } );

    }



}
