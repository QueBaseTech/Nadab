package com.example.karokojnr.nadab_hotels;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.adapter.ItemsAdapter;
import com.example.karokojnr.nadab_hotels.adapter.OrderItemAdapter;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.OrderItem;
import com.example.karokojnr.nadab_hotels.model.OrderResponse;
import com.example.karokojnr.nadab_hotels.model.Orders;
import com.example.karokojnr.nadab_hotels.model.Product;
import com.example.karokojnr.nadab_hotels.utils.HotelSharedPreference;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.graphics.Color.GREEN;

public class Order extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener {
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

        Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar ( toolbar );

        DrawerLayout drawer = (DrawerLayout) findViewById ( R.id.drawer_layout );
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle ( this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close );
        drawer.setDrawerListener ( toggle );
        toggle.syncState ();

        NavigationView navigationView = (NavigationView) findViewById ( R.id.nav_view );
        View headerView = navigationView.getHeaderView ( 0 );
        TextView navUsername = (TextView) headerView.findViewById ( R.id.navTextview );
        ImageView navImageview = (ImageView) headerView.findViewById ( R.id.imageView );
        navigationView.setNavigationItemSelectedListener(this);



        HotelSharedPreference hotel = SharedPrefManager.getInstance ( this ).getHotel ();
        navUsername.setText ( hotel.getUsername () );
        toolbar.setTitle(hotel.getUsername());
        Glide.with ( this ).load ( RetrofitInstance.BASE_URL + "images/uploads/hotels/" + String.valueOf ( hotel.getIvImage () ) ).into ( navImageview );

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
                    orderItems.addAll(Arrays.asList(order.getOrderItems()));
                    double totalAccepted = 0;
                    for (int i=0; i< orderItems.size(); i++ ){
                        OrderItem orderItem = orderItems.get(i);
                        totalAccepted += orderItem.getPrice();
                    }
                    totalPrice.setText("Total :: Kshs. " + totalAccepted);
                    customerName.setText("CUSTOMER NAME: \n " +order.getCustomer().getName());
                    orderStatus.setText(order.getOrderStatus());
                    if(order.getOrderStatus().equals("COMPLETE")) {
                        acceptAll.setVisibility(View.GONE);
                        rejectAll.setVisibility(View.GONE);
                        confirmPay.setVisibility(View.GONE);
                        completeOrder.setText("Hide order");
                    }

                    if(order.getOrderStatus().equals("BILLS")) {
                        acceptAll.setVisibility(View.GONE);
                        rejectAll.setVisibility(View.GONE);
                    }
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
                String status = accept? "ACCEPTED": "REJECTED";
                HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
                Call<OrderResponse> call = service.updateOrderItemStatus(order.getOrderId(), itemId, status);
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
                Log.wtf("status", "onClick: "+ order.getOrderStatus().equals("COMPLETE"));
                if(order.getOrderStatus().equals("COMPLETE")) {
                    HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
                    Call<OrderResponse> call = service.acceptOrder(order.getOrderId(), "HIDDEN");
                    call.enqueue ( new Callback<OrderResponse>() {
                        @Override
                        public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                            if(response.body().isSuccess()) {
                                order = response.body().getOrder();
                                orderStatus.setText(order.getOrderStatus());
                            }
                        }

                        @Override
                        public void onFailure(Call<OrderResponse> call, Throwable t) {
                            Log.wtf("LOG", "onFailure: "+t.getMessage() );
                            Toast.makeText (  mContext, "Something went wrong...Please try later!"+t.getMessage(), Toast.LENGTH_SHORT ).show ();
                        }
                    } );
                } else {
                    updateOrderStatus("COMPLETE");
                }
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        // Handle navigation view item clicks here.
        int id = menuItem.getItemId();

        if (id == R.id.nav_home) {
            startActivity(new Intent(Order.this, MainActivity.class));

        } else if (id == R.id.nav_profile) {
            startActivity(new Intent(Order.this, ProfileActivity.class));
        } else if (id == R.id.nav_add_meals) {
            startActivity(new Intent(Order.this, AddMeals.class));

        } else if (id == R.id.nav_sign_out) {
            // Log.wtf(TAG, "onOptionsItemSelected: Logout");
            SharedPrefManager.getInstance ( getApplicationContext () ).logout ();
            startActivity ( new Intent ( getApplicationContext (), LoginActivity.class ) );
            finish();
        }else if (id == R.id.terms_conditions){
            startActivity(new Intent(Order.this, Terms.class));

        }
        this.finish();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem search = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        search(searchView);


        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }
}
