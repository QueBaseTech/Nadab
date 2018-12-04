package com.example.karokojnr.nadab_seller;


import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import android.support.v7.widget.DividerItemDecoration;

public class Items extends AppCompatActivity {
    private List<Product> productList = new ArrayList<Product>();
    private ItemsAdapter mAdapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Snackbar.make(view, "Fill in the details of the Item you want to add", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                final Dialog dialog = new Dialog (Items.this);
                dialog.setContentView(R.layout.dialog_items); //layout for dialog
                dialog.setTitle("Add a new product");
                dialog.setCancelable(false); //none-dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                EditText productName = (EditText) dialog.findViewById(R.id.productName);
                EditText quantity = (EditText) dialog.findViewById(R.id.quantity);
                EditText price = (EditText) dialog.findViewById(R.id.price);
                View btnAdd = dialog.findViewById(R.id.btn_ok);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                //set handling event for 2 buttons and spinner
                btnAdd.setOnClickListener(onConfirmListener(productName, quantity, price, dialog));
                btnCancel.setOnClickListener(onCancelListener(dialog));

                //show dialog box
                dialog.show();
            }
        });





        recyclerView = (RecyclerView) findViewById ( R.id.recycler_view );

        mAdapter = new ItemsAdapter(productList);


        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product product = productList.get(position);
                Toast.makeText(getApplicationContext(), product.getProductName () + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        prepareMovieData();
    }

    /**
     * Prepares sample data to provide data set to adapter
     */
    private void prepareMovieData() {
        Product product = new Product("Pizza", "1", "700");
        productList.add(product);

        product = new Product("Chicken", "1 / 4", "400");
        productList.add(product);

        product = new Product("Passion Juice", "250g", "150");
        productList.add(product);

        product = new Product("Ugali & Beef", "1", "250");
        productList.add(product);

        product = new Product("Rice & Beef ", "1", "210");
        productList.add(product);

        product = new Product("Chips", "1", "100");
        productList.add(product);

        product = new Product("Chips Masala", "1", "150");
        productList.add(product);

        product = new Product("Chips & Sausage", "1", "150");
        productList.add(product);

        product = new Product("1 Chapati & Beef", "1", "100");
        productList.add(product);

        product = new Product("Chapati", "1", "20");
        productList.add(product);

        product = new Product("Tusker", "250g", "200");
        productList.add(product);

        product = new Product("Guiness", "250g", "200");
        productList.add(product);

        product = new Product("Tusker Cedar", "250g", "200");
        productList.add(product);

        product = new Product("Icebag", "150g", "150");
        productList.add(product);

        product = new Product("Burger", "1", "130");
        productList.add(product);

        product = new Product("Tea", "1 Cup", "50");
        productList.add(product);


        // notify adapter about data set changes
        // so that it will render the list with new data
        mAdapter.notifyDataSetChanged();
    }
    //add items to recyclerView using the dialog box
    private View.OnClickListener onConfirmListener(final EditText productName, final EditText quantity,final EditText price, final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product product = new Product (productName.getText().toString().trim(),quantity.getText().toString().trim(), price.getText().toString().trim() );

                //adding new object to arraylist
                productList.add(product);

                //notify data set changed in RecyclerView adapter
                mAdapter.notifyDataSetChanged();

                //close dialog after all
                dialog.dismiss();
            }
        };
    }
    //cancel button on dialog box
    private View.OnClickListener onCancelListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }

}