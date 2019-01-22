package com.example.karokojnr.nadab;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.karokojnr.nadab.adapter.ItemsAdapter;
import com.example.karokojnr.nadab.api.HotelService;
import com.example.karokojnr.nadab.api.RetrofitInstance;
import com.example.karokojnr.nadab.model.Product;
import com.example.karokojnr.nadab.model.Products;
import com.example.karokojnr.nadab.utils.Constants;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMeals extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Product> productList = new ArrayList<> ();
    private ItemsAdapter adapter;
    private static final String TAG = "Items";
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView image;
    Button btn_okay, btn_cancel;
    EditText name, price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_meals );

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        name = (EditText) findViewById ( R.id.name );
        price = (EditText) findViewById ( R.id.price );

        image = (ImageView) findViewById ( R.id.ivImage );
        image.setOnClickListener (  this );

        btn_okay = (Button) findViewById ( R.id.btn_ok );
        btn_cancel = (Button) findViewById ( R.id.btn_cancel );
        btn_okay.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
                Product product = new Product(
                        name.getText ().toString ().trim (),
                        price.getText ().toString ().trim (),
                        image.getDrawable ().toString ().trim ()
                );

                //Set defaults
                product.setHotel(getApplicationContext()
                        .getSharedPreferences(Constants.M_SHARED_PREFERENCE, MODE_PRIVATE)
                        .getString(Constants.M_SHARED_PREFERENCE_HOTEL_ID, ""));
                productList.add (product);
                //notify data set changed in RecyclerView adapter
//                adapter.notifyDataSetChanged ();

                Call<Products> call = service.addProduct(product);

                call.enqueue ( new Callback<Products> () {
                    @Override
                    public void onResponse(Call<Products> call, Response<Products> response) {
                        if (response.isSuccessful ()) {
                            assert response.body () != null;
                            Log.d ( TAG, "Hotel:: " + response.body ().getProductArrayList ().toString () );
                            Toast.makeText ( AddMeals.this, "Added Successfully...", Toast.LENGTH_SHORT ).show ();
                        } else {
                            Toast.makeText ( AddMeals.this, "Error adding...", Toast.LENGTH_SHORT ).show ();
                        }
                    }

                    @Override
                    public void onFailure(Call<Products> call, Throwable t) {
                        Toast.makeText ( AddMeals.this, "Something went wrong...Error message: " + t.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        } );
        btn_cancel.setOnClickListener ( this );
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

            Uri selectedImage = data.getData ();
            image.setImageURI(selectedImage);

        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
