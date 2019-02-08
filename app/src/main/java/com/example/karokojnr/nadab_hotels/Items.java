package com.example.karokojnr.nadab_hotels;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.adapter.ItemsAdapter;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Product;
import com.example.karokojnr.nadab_hotels.model.Products;
import com.example.karokojnr.nadab_hotels.utils.Constants;
import com.example.karokojnr.nadab_hotels.utils.Utility;
import com.example.karokojnr.nadab_hotels.utils.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Items extends AppCompatActivity {
    private static final int RESULT_LOAD_IMAGE = 1;

    private ItemsAdapter adapter;
    RecyclerView recyclerView;

    private ArrayList<Product> productList = new ArrayList<> ();
    private ActionBar toolbar;
    FloatingActionButton fab;

    private static final String TAG = "Items";

    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private ImageView imageView;
    private ImageButton image;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_items );
        context = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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


/*

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
                ImageView image = (ImageView) dialog.findViewById ( R.id.image );
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
*/


        recyclerView = (RecyclerView) findViewById ( R.id.recycler_view );
        adapter = new ItemsAdapter ( productList, context);
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
               // EditText unitMeasure = (EditText) dialog.findViewById ( R.id.unitMeasure );
                EditText price = (EditText) dialog.findViewById ( R.id.price );
                //EditText hotel = (EditText) dialog.findViewById ( R.id.hotel );
                imageView = (ImageView) dialog.findViewById ( R.id.image );
                imageView.setOnClickListener ( new View.OnClickListener () {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        selectImage ();
                    }
                } );
                imageView.setFocusable ( false );
               // EditText sellingStatus = (EditText) dialog.findViewById ( R.id.sellingStatus );


               // EditText servedWith = (EditText) dialog.findViewById ( R.id.servedWith );
                View btnAdd = dialog.findViewById ( R.id.btn_ok );
                View btnCancel = dialog.findViewById ( R.id.btn_cancel );


                //ImageView ivImage = (ImageView) findViewById(R.id.ivImage);





                //set handling event for 2 buttons and spinner
                btnAdd.setOnClickListener ( onConfirmListener ( name,  price,  image,  dialog ) );
                btnCancel.setOnClickListener ( onCancelListener ( dialog ) );

                //show dialog box
                dialog.show ();
            }
        } );


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null){

            Uri selectedImage = data.getData ();
            imageView.setImageURI(selectedImage);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void selectImage() {


        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Items.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=Utility.checkPermission(Items.this);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask ="Take Photo";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask ="Choose from Library";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    /*Method to generate List of employees using RecyclerView with custom adapter*/
    private void generateProductsList(ArrayList<Product> empDataList) {
        recyclerView = (RecyclerView) findViewById ( R.id.recycler_view );

        adapter = new ItemsAdapter ( empDataList, context );

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
    private View.OnClickListener onCancelListener(final Dialog dialog) {
        return new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                dialog.dismiss ();
            }
        };
    }

    private View.OnClickListener onConfirmListener(final EditText name, final EditText price, final ImageButton image, final Dialog dialog) {
        return new View.OnClickListener () {
            @Override
            public void onClick(final View v) {
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



    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }*/

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream ();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File (Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream (destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageView.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        image.setImageBitmap(bm);
    }

}



