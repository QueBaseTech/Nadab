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
import com.example.karokojnr.nadab.model.ProductRegister;
import com.example.karokojnr.nadab.model.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Items extends AppCompatActivity {
    private List<Products> productList = new ArrayList<> ();
    private ItemsAdapter mAdapter;
    RecyclerView recyclerView;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_items );
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar = getSupportActionBar ();
//        toolbar.setTitle("NADAB");
        FloatingActionButton fab = (FloatingActionButton) findViewById ( R.id.fab );
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
                btnAdd.setOnClickListener ( onConfirmListener ( name, unitMeasure,price,hotel, image, sellingStatus, servedWith, dialog ) );
                btnCancel.setOnClickListener ( onCancelListener ( dialog ) );

                //show dialog box
                dialog.show ();
            }
        } );


        recyclerView = (RecyclerView) findViewById ( R.id.recycler_view );

        mAdapter = new ItemsAdapter ( productList, this );


        recyclerView.setHasFixedSize ( true );

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager ( getApplicationContext () );

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager ( mLayoutManager );

        // adding inbuilt divider line
        recyclerView.addItemDecoration ( new DividerItemDecoration ( this, LinearLayoutManager.VERTICAL ) );

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator ( new DefaultItemAnimator () );

        recyclerView.setAdapter ( mAdapter );

        // row click listener
        recyclerView.addOnItemTouchListener ( new RecyclerTouchListener ( getApplicationContext (), recyclerView, new RecyclerTouchListener.ClickListener () {
            @Override
            public void onClick(View view, int position) {
                Products products = productList.get ( position );
                Toast.makeText ( getApplicationContext (), products.getName () + " is selected!", Toast.LENGTH_SHORT ).show ();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );

        getMovieList ();
    }

    private View.OnClickListener onCancelListener ( final Dialog dialog){
        return new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                dialog.dismiss ();
            }
        };
    }

    /**
     * Prepares sample data to provide data set to adapter
     */
    public void getMovieList() {

//        // display a progress dialog
//        final ProgressDialog progressDialog = new ProgressDialog (this);
//        progressDialog.setCancelable(false); // set cancelable to false
//        progressDialog.setMessage("Please Wait"); // set message
//        progressDialog.show(); // show progress dialog
        //made changes
        HotelService apiInterface = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<Products> call = apiInterface.getProduct ();
        call.enqueue ( new Callback<Products> () {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                if (response == null) {
                    Toast.makeText ( getApplicationContext (), "Something Went Wrong...!!", Toast.LENGTH_SHORT ).show ();


                    /* Edited */
                }else{
                    for (Products products: response.body ()){
                        productsList.add(products);
                    }

                    Log.i ( "RESPONSE: ", "" + response.toString () );
                }
                mAdapter.notifyDataSetChanged ();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText ( getApplicationContext (), "Unable to fetch json: " + t.getMessage (), Toast.LENGTH_LONG ).show ();
                Log.e ( "ERROR: ", t.getMessage () );
            }
        } );
    }

    //
//        // notify adapter about data set changes
//        // so that it will render the list with new data
//        mAdapter.notifyDataSetChanged();
//    }
    //add items to recyclerView using the dialog box
    private View.OnClickListener onConfirmListener(final EditText name, final EditText unitMeasure, final EditText price,
                                                   final EditText hotel, final EditText image, final EditText sellingStatus,
                                                   final EditText servedWith, final Dialog dialog) {
        return new View.OnClickListener () {
            @Override
            public void onClick(final View v) {
                HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
               // Product products = new Product ( name.getText ().toString ().trim (), unitMeasure.getText ().toString ().trim (), servedWith.getText ().toString ().trim () );
                Products products = new Products (
                        name.getText ().toString().trim (),
                        unitMeasure.getText().toString().trim (),
                        price.getText().toString().trim (),
                        hotel.getText().toString().trim (),

                        image.getText().toString().trim ()

//                        sellingStatus.getText().toString().trim (),
//                        servedWith.getText().toString().trim ()


                );

                //adding new object to arraylist
                productList.add ( products );

                //notify data set changed in RecyclerView adapter
                mAdapter.notifyDataSetChanged ();

                //close dialog after all
                dialog.dismiss ();


//    String name = name.getText ().toString();
//    String unitMeasure = unitMeasure.getText().toString();
//    String price = price.getText().toString();
//    String hotel = hotel.getText().toString();
//    String image = image.getText().toString();
//    String sellingStatus = sellingStatus.getText().toString();
//    String servedWith = servedWith.getText().toString();


                // TODO:: Fetch fields from form
                products.setName  ( String.valueOf ( name ) );
                products.setUnitMeasure ( String.valueOf ( unitMeasure ) );
                products.setPrice(String.valueOf ( price ));
                products.setHotel( String.valueOf ( hotel ) );
                products.setImage( String.valueOf ( image ) );
                //products.setSellingStatus( String.valueOf ( sellingStatus ) );
               // products.setServedWith ( String.valueOf ( servedWith ) );

                // TODO :: Remove all the hard coded values

                Call<ProductRegister> call = service.addProduct ( products );

                call.enqueue ( new Callback<ProductRegister> () {
                    @Override
                    public void onResponse(Call<ProductRegister> call, Response<ProductRegister> response) {
//                        Products products = new Products(name, unitMeasure, price, hotel, image, sellingStatus, servedWith);
//                        productList.add(products);

                        if (response.isSuccessful ()) {
                            Log.d ( "JOA", "Hotel:: " + response.body ().getProduct ().toString () );
                            Toast.makeText ( Items.this, "Added Successfully...", Toast.LENGTH_SHORT ).show ();
                        } else
                            Toast.makeText ( Items.this, "Error adding...", Toast.LENGTH_SHORT ).show ();
                    }

                    @Override
                    public void onFailure(Call<ProductRegister> call, Throwable t) {
                        Toast.makeText ( Items.this, "Something went wrong...Error message: " + t.getMessage (), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
                //cancel button on dialog box
                private View.OnClickListener onCancelListener ( final Dialog dialog){
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

