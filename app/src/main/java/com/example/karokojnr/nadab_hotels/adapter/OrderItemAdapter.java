package com.example.karokojnr.nadab_hotels.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.karokojnr.nadab_hotels.R;
import com.example.karokojnr.nadab_hotels.model.OrderItem;

import java.util.ArrayList;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>  {
    private ArrayList<OrderItem> orderItems;
    Context context;

    public OrderItemAdapter(ArrayList<OrderItem> empDataList, Context context) {
        this.orderItems = empDataList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.orders_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int i) {
        OrderItem item = orderItems.get ( i );

        holder.name.setText(item.getName());
        holder.price.setText("Kshs. "+ item.getPrice());
        holder.qty.setText("Qty : "+item.getQty());
        holder.status.setText(item.getStatus());
        if(!item.getStatus().equals("NEW")) {
            holder.accept.setVisibility(View.GONE);
            holder.reject.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orderItems.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, status, qty;
        Button accept, reject;

        MyViewHolder(View itemView) {
            super ( itemView );

            name = (TextView) itemView.findViewById(R.id.order_item_name);
            status = (TextView) itemView.findViewById(R.id.order_item_status);
            qty = (TextView) itemView.findViewById ( R.id.qty_tv);
            price = (TextView) itemView.findViewById ( R.id.order_total_price);
            accept = itemView.findViewById(R.id.accept_btn);
            reject = itemView.findViewById(R.id.reject_btn);
        }
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
    }

}