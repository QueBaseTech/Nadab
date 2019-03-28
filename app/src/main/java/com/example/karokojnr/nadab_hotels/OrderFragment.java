package com.example.karokojnr.nadab_hotels;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.adapter.OrdersAdapter;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Order;
import com.example.karokojnr.nadab_hotels.model.OrderItem;
import com.example.karokojnr.nadab_hotels.model.Orders;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderFragment extends Fragment {

    private OrdersAdapter adapter;
    RecyclerView recyclerView;
    private List<Order> orderLists = new ArrayList<> ();

    @Nullable

    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.activity_order_list, container, false);

        recyclerView = (RecyclerView) view.findViewById ( R.id.orderslist_recycler_view );
        // white background notification bar
        whiteNotificationBar(recyclerView);


        recyclerView.addOnItemTouchListener ( new RecyclerTouchListener( getActivity (), recyclerView, new RecyclerTouchListener.ClickListener () {
            @Override
            public void onClick(View view, final int position) {
                final Order order = orderLists.get ( position );
                OrderItem[] orderItems = order.getOrderItems();

                Intent intent = new Intent(getContext(), com.example.karokojnr.nadab_hotels.Order.class);
                intent.putExtra("orderId", order.getOrderId());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );
        /*Create handle for the RetrofitInstance interface*/
        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<Orders> call = service.getOrders( SharedPrefManager.getInstance(getActivity ()).getToken() );
        call.enqueue ( new Callback<Orders> () {
            @Override
            public void onResponse(Call<Orders> call, Response<Orders> response) {
                orderLists.clear();
                for (int i = 0; i < response.body ().getOrdersList ().size (); i++) {
                    Order order = response.body ().getOrdersList().get(i);
                    if(order.getOrderStatus().equals("NEW") || order.getOrderStatus().equals("RE-ORDER")) orderLists.add ( order );

                    orderLists.add ( order );
                }
                generateOrdersList((ArrayList<Order>) orderLists);
            }

            @Override
            public void onFailure(Call<Orders> call, Throwable t) {
                Log.wtf("LOG", "onFailure: "+t.getMessage() );
                Toast.makeText ( getActivity (), "Something went wrong...Please try later!"+t.getMessage(), Toast.LENGTH_SHORT ).show ();
            }
        } );


        return view;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

    }

    private void generateOrdersList(ArrayList<Order> empDataList) {
        adapter = new OrdersAdapter( empDataList, getActivity ());

        /*RecyclerView.LayoutManager layoutManager = new LinearLayoutManager ( getActivity () );

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter (adapter);*/
        if (getContext ().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(getContext (), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager (getContext (), 4));
        }

        recyclerView.setAdapter ( adapter );
        adapter.notifyDataSetChanged();
    }


    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getActivity ().getWindow().setStatusBarColor(Color.WHITE);
        }
    }
}
