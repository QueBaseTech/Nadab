package com.example.karokojnr.nadab_hotels;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.adapter.ItemsAdapter;
import com.example.karokojnr.nadab_hotels.adapter.OrderItemAdapter;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.OrderItem;
import com.example.karokojnr.nadab_hotels.model.OrderResponse;
import com.example.karokojnr.nadab_hotels.model.Orders;
import com.example.karokojnr.nadab_hotels.model.Product;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order extends AppCompatActivity {
    private Context mContext;
    private OrderItemAdapter adapter;
    RecyclerView recyclerView;
    private TextView totalPrice, customerName, orderStatus;
    private Button confirmPay, acceptAll, rejectAll, completeOrder;
    private com.example.karokojnr.nadab_hotels.model.Order order;

    private ArrayList<OrderItem> orderItems = new ArrayList<> ();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;
        totalPrice = findViewById(R.id.total_price_tv);
        customerName = findViewById(R.id.customer_name_tv);
        confirmPay = findViewById(R.id.confirm_payment);
        acceptAll = findViewById(R.id.accept_all_btn);
        rejectAll = findViewById(R.id.reject_all_btn);
        orderStatus = findViewById(R.id.order_status_tv);
        completeOrder = findViewById(R.id.complete_order);

        Intent i = getIntent();
        String orderId = i.getStringExtra("orderId");
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<OrderResponse> call = service.getOrder(orderId);
        call.enqueue ( new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.body().isSuccess()) {
                    order = response.body().getOrder();
                    totalPrice.setText("Total :: Kshs. " + order.getTotalPrice());
                    customerName.setText("Customer: " +order.getCustomer().getName());
                    orderItems.addAll(Arrays.asList(order.getOrderItems()));
                    orderStatus.setText(order.getOrderStatus());
                }

                recyclerView = (RecyclerView) findViewById ( R.id.order_items_recycler_view );

                adapter = new OrderItemAdapter ( orderItems, mContext);

                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager ( mContext );

                recyclerView.setLayoutManager ( layoutManager );

                recyclerView.setAdapter ( adapter );
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.wtf("Order", "onFailure: "+t.getMessage());
                Toast.makeText ( mContext, "Something went wrong...Please try later!"+t.getMessage(), Toast.LENGTH_SHORT ).show ();
            }
        } );

        adapter = new OrderItemAdapter(orderItems, mContext);

        recyclerView = (RecyclerView) findViewById ( R.id.order_items_recycler_view );
        recyclerView.setHasFixedSize ( true );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager ( getApplicationContext () );
        recyclerView.setLayoutManager ( mLayoutManager );
        // adding inbuilt divider line
        recyclerView.addItemDecoration ( new DividerItemDecoration( this, LinearLayoutManager.VERTICAL ) );
        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setItemAnimator ( new DefaultItemAnimator() );
        recyclerView.setAdapter ( adapter );
        // row click listener
        recyclerView.addOnItemTouchListener ( new RecyclerTouchListener ( getApplicationContext (), recyclerView, new RecyclerTouchListener.ClickListener () {
            @Override
            public void onClick(View view, final int position) {
                // handle button clicks
                OrderItem orderItem = orderItems.get(position);
                final String orderItemId = orderItem.getId();
                String status = orderItem.getStatus();

                view.findViewById(R.id.accept_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateOrderItemStatus(orderItemId, true, position);
                    }
                });

                view.findViewById(R.id.reject_btn).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateOrderItemStatus(orderItemId, false, position);
                    }
                });


            }

            private void updateOrderItemStatus(String itemId, boolean accept, int position) {
                String orderStatus = accept? "ACCEPTED": "REJECTED";
                HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
                Call<OrderResponse> call = service.updateOrderItemStatus(order.getOrderId(), itemId, orderStatus);
                call.enqueue ( new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if(response.body().isSuccess()) {
                            orderItems.clear();
                            orderItems.addAll(Arrays.asList(response.body().getOrder().getOrderItems()));
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        Log.wtf("LOG", "onFailure: "+t.getMessage() );
                        Toast.makeText (  mContext, "Something went wrong...Please try later!"+t.getMessage(), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );

        acceptAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrderStatus("ACCEPTED");
            }
        });

        rejectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOrderStatus("REJECTED");
            }
        });

        confirmPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send notification to client
                updateOrderStatus("PAID");
            }
        });

        completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send notification to client
                updateOrderStatus("COMPLETE");
            }
        });

    }

    private void updateOrderStatus(String status) {
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<OrderResponse> call = service.acceptAll(order.getOrderId(), status);
        call.enqueue ( new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                if(response.body().isSuccess()) {
                    orderItems.clear();
                    orderItems.addAll(Arrays.asList(response.body().getOrder().getOrderItems()));
                    adapter.notifyDataSetChanged();
                    orderStatus.setText(response.body().getOrder().getOrderStatus());
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                Log.wtf("LOG", "onFailure: "+t.getMessage() );
                Toast.makeText (  mContext, "Something went wrong...Please try later!"+t.getMessage(), Toast.LENGTH_SHORT ).show ();
            }
        } );
    }
}
