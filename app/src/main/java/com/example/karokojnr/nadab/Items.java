package com.example.karokojnr.nadab;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.karokojnr.nadab.adapter.ItemsAdapter;
import com.example.karokojnr.nadab.api.HotelService;
import com.example.karokojnr.nadab.api.RetrofitInstance;
import com.example.karokojnr.nadab.model.Product;
import com.example.karokojnr.nadab.model.Products;
import com.example.karokojnr.nadab.model.Products;
import com.example.karokojnr.nadab.utils.Constants;
import com.example.karokojnr.nadab.utils.utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Items extends AppCompatActivity {

    private ItemsAdapter adapter;
    RecyclerView recyclerView;

    private List<Product> productList = new ArrayList<> ();
    private ActionBar toolbar;
    FloatingActionButton fab;

    private static final String TAG = "Items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_items );

        /*Create handle for the RetrofitInstance interface*/
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        /*Call the method with parameter in the interface to get the employee data*/
        Call<Products> call = service.getProducts(utils.getToken(getApplicationContext()));
        /*Log the URL called*/
        Log.wtf ( "URL Called", call.request ().url () + "" );

        call.enqueue ( new Callback<Products> () {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                generateProductsList ( response.body ().getProductArrayList () );
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText ( Items.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT ).show ();
            }
        } );


        //FLOATING BUTTON

        fab = (FloatingActionButton) findViewById ( R.id.fab );
        fab.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Snackbar.make ( view, "Fill in the details of the Item you want to add", Snackbar.LENGTH_LONG ).setAction ( "Action", null ).show ();

                final Dialog dialog = new Dialog ( Items.this );
                dialog.setContentView ( R.layout.dialog_items ); //layout for dialog
                dialog.setTitle ( "Add a new products" );
                dialog.setCancelable ( false ); //none-dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                EditText name = (EditText) dialog.findViewById ( R.id.name );
                EditText unitMeasure = (EditText) dialog.findViewById ( R.id.unitMeasure );
                EditText price = (EditText) dialog.findViewById ( R.id.price );
                EditText hotel = (EditText) dialog.findViewById ( R.id.hotel );
                EditText image = (EditText) dialog.findViewById ( R.id.image );
                EditText sellingStatus = (EditText) dialog.findViewById ( R.id.sellingStatus );


                EditText servedWith = (EditText) dialog.findViewById ( R.id.servedWith );
                View btnAdd = dialog.findViewById ( R.id.btn_ok );
                View btnCancel = dialog.findViewById ( R.id.btn_cancel );

                //set handling event for 2 buttons and spinner
                btnAdd.setOnClickListener ( onConfirmListener ( name, unitMeasure, price, hotel, image, sellingStatus, servedWith, dialog ) );
                btnCancel.setOnClickListener ( onCancelListener ( dialog ) );

                //show dialog box
                dialog.show ();
            }
        } );


        recyclerView = (RecyclerView) findViewById ( R.id.recycler_view );
        adapter = new ItemsAdapter ( productList, this );
        recyclerView.setHasFixedSize ( true );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager ( getApplicationContext () );
        recyclerView.setLayoutManager ( mLayoutManager );
        // adding inbuilt divider line
        recyclerView.addItemDecoration ( new DividerItemDecoration ( this, LinearLayoutManager.VERTICAL ) );
        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator ( new DefaultItemAnimator () );
        recyclerView.setAdapter ( adapter );
        // row click listener
        recyclerView.addOnItemTouchListener ( new RecyclerTouchListener ( getApplicationContext (), recyclerView, new RecyclerTouchListener.ClickListener () {
            @Override
            public void onClick(View view, int position) {
                Product product = productList.get ( position );
                Toast.makeText ( getApplicationContext (), product.getName () + " is selected!", Toast.LENGTH_SHORT ).show ();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );

        getProductList ();
    }

    private View.OnClickListener onCancelListener(final Dialog dialog) {
        return new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                dialog.dismiss ();
            }
        };
    }


    /*Method to generate List of employees using RecyclerView with custom adapter*/
    private void generateProductsList(ArrayList<Product> empDataList) {
        recyclerView = (RecyclerView) findViewById ( R.id.recycler_view );

        adapter = new ItemsAdapter ( empDataList );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager ( Items.this );

        recyclerView.setLayoutManager ( layoutManager );

        recyclerView.setAdapter ( adapter );
    }

    public void getProductList() {
        HotelService apiInterface = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<Products> call = apiInterface.getProducts(utils.getToken(getApplicationContext()));
        call.enqueue ( new Callback<Products> () {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response == null) {
                    Toast.makeText ( getApplicationContext (), "Something Went Wrong...!!", Toast.LENGTH_SHORT ).show ();
                    //edited
                } else {
                    assert response.body () != null;
                    for (int i = 0; i < response.body().getProductArrayList().size(); i++) {
                        productList.add(response.body().getProductArrayList().get(i));
                    }
                    Log.i ( "RESPONSE: ", "" + response.toString () );
                }
                adapter.notifyDataSetChanged ();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText ( getApplicationContext (), "Unable to fetch json: " + t.getMessage (), Toast.LENGTH_LONG ).show ();
                Log.e ( "ERROR: ", t.getMessage () );
            }
        } );
    }
    
    private View.OnClickListener onConfirmListener(final EditText name, final EditText unitMeasure, final EditText price, final EditText hotel, final EditText image, final EditText sellingStatus, final EditText servedWith, final Dialog dialog) {
        return new View.OnClickListener () {
            @Override
            public void onClick(final View v) {
                HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
                Product product = new Product(
                        name.getText ().toString ().trim (), 
                        unitMeasure.getText ().toString ().trim (), 
                        price.getText ().toString ().trim ()
                );
                
                //Set defaults
                product.setHotel(getApplicationContext()
                        .getSharedPreferences(Constants.M_SHARED_PREFERENCE, MODE_PRIVATE)
                        .getString(Constants.M_SHARED_PREFERENCE_HOTEL_ID, ""));
                productList.add (product);
                //notify data set changed in RecyclerView adapter
                adapter.notifyDataSetChanged ();
                dialog.dismiss ();

                Call<Products> call = service.addProduct(product);

                call.enqueue ( new Callback<Products> () {
                    @Override
                    public void onResponse(Call<Products> call, Response<Products> response) {
                        if (response.isSuccessful ()) {
                            assert response.body () != null;
                            Log.d ( TAG, "Hotel:: " + response.body ().getProductArrayList ().toString () );
                            Toast.makeText ( Items.this, "Added Successfully...", Toast.LENGTH_SHORT ).show ();
                        } else {
                            Toast.makeText ( Items.this, "Error adding...", Toast.LENGTH_SHORT ).show ();
                        }
                    }

                    @Override
                    public void onFailure(Call<Products> call, Throwable t) {
                        Toast.makeText ( Items.this, "Something went wrong...Error message: " + t.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
                
            }

            //cancel button on dialog box
            private View.OnClickListener onCancelListener(final Dialog dialog) {
                return new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss ();
                    }
                };
            }

        };
    }
}
