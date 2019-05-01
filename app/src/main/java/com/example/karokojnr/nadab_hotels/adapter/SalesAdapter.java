package com.example.karokojnr.nadab_hotels.adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.R;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Order;
import com.example.karokojnr.nadab_hotels.model.OrderResponse;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.MyViewHolder>  {
    private ArrayList<Order> sales;
    Context context;

    public SalesAdapter(ArrayList<Order> empDataList, Context context) {
        this.sales = empDataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sales_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Order order = sales.get ( position );
        String upadtedAt = order.getUpdatedAt();

        holder.name.setText(order.getCustomer().getName());
//        holder.time.setText(upadtedAt);
        holder.price.setText(order.getTotalPrice().toString());
        holder.qty.setText(order.getTotalItems() + " items");
        holder.status.setText(order.getOrderStatus());

        holder.hideBt.setVisibility(View.GONE);
        holder.hideBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
                Call<OrderResponse> call = service.acceptAll(order.getOrderId(), "HIDE");
                call.enqueue ( new Callback<OrderResponse>() {
                    @Override
                    public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                        if(response.body().isSuccess()) {

                        }
                    }

                    @Override
                    public void onFailure(Call<OrderResponse> call, Throwable t) {
                        Log.wtf("LOG", "onFailure: sales.."+t.getMessage() );
                        Toast.makeText (  context, "Something went wrong...Please try later!"+t.getMessage(), Toast.LENGTH_SHORT ).show ();
                    }
                } );
            }
        });
    }

    @Override
    public int getItemCount() {
        return sales.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, status, qty, hideBt;

        MyViewHolder(View itemView) {
            super ( itemView );

//            time = (TextView) itemView.findViewById(R.id.time_elapsed);
            name = (TextView) itemView.findViewById(R.id.sale_item_name);
            qty = (TextView) itemView.findViewById ( R.id.qty_tv);
            price = (TextView) itemView.findViewById ( R.id.sale_total_price);
            status = (TextView) itemView.findViewById ( R.id.sale_item_status);
            hideBt = itemView.findViewById(R.id.hide_item_btn);
        }
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

}

