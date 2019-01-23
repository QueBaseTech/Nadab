package com.example.karokojnr.nadab.orders;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.karokojnr.nadab.R;
import com.example.karokojnr.nadab.RecyclerTouchListener;
import com.example.karokojnr.nadab.adapter.ItemsAdapter;
import com.example.karokojnr.nadab.adapter.OrdersAdapter;
import com.example.karokojnr.nadab.api.HotelService;
import com.example.karokojnr.nadab.api.RetrofitInstance;
import com.example.karokojnr.nadab.model.Order;
import com.example.karokojnr.nadab.model.Orders;
import com.example.karokojnr.nadab.model.Product;
import com.example.karokojnr.nadab.model.Products;
import com.example.karokojnr.nadab.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderList extends AppCompatActivity {
    private OrdersAdapter adapter;
    private Context context;
    RecyclerView recyclerView;

    private List<Order> orderLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        /*Create handle for the RetrofitInstance interface*/
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<Orders> call = service.getOrders( SharedPrefManager.getInstance(OrderList.this).getToken() );
        call.enqueue ( new Callback<Orders>() {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                for (int i = 0; i < response.body ().getOrdersList ().size (); i++) {
                    orderLists.add ( response.body ().getOrdersList().get(i) );
                }
                generateOrdersList ( response.body ().getOrdersList () );
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Log.wtf("LOG", "onFailure: "+t.getMessage() );
                Toast.makeText ( OrderList.this, "Something went wrong...Please try later!"+t.getMessage(), Toast.LENGTH_SHORT ).show ();
            }
        } );


        recyclerView = (RecyclerView) findViewById ( R.id.orderslist_recycler_view );
        recyclerView.addOnItemTouchListener ( new RecyclerTouchListener( OrderList.this, recyclerView, new RecyclerTouchListener.ClickListener () {
            @Override
            public void onClick(View view, int position) {
                Order order = orderLists.get ( position );
                Toast.makeText ( OrderList.this, order.getOrderId () + " is selected!", Toast.LENGTH_SHORT ).show ();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );
    }

    private void generateOrdersList(ArrayList<Order> empDataList) {
        adapter = new OrdersAdapter( empDataList, OrderList.this );

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager (context, 4));
        }

        recyclerView.setAdapter (adapter);
    }

}
