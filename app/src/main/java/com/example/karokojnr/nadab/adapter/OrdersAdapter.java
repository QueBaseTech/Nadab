package com.example.karokojnr.nadab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karokojnr.nadab.R;
import com.example.karokojnr.nadab.model.Order;

import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyViewHolder>  {
    private ArrayList<Order> orders;
    Context context;

    public OrdersAdapter(ArrayList<Order> empDataList, Context context) {
        this.orders = empDataList;
        this.context = context;
    }

    @Override
    public OrdersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.order_item, parent, false);
        return new OrdersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrdersAdapter.MyViewHolder holder, int position) {
        Order order = orders.get ( position );
//        holder.name.setText(order.getName());
//        holder.unitMeasure.setText(product.getUnitMeasure());
        holder.price.setText(order.getTotalPrice().toString());
        holder.qty.setText(order.getTotalItems() + " items");
        //holder.hotel.setText(product.getHotel());
//        Glide.with(context)
//                .load("https://ccc7835e.ngrok.io/images/uploads/thumbs/e4a27e9b74b1907706fa31f3dd519b36.jpg")
//                .load(RetrofitInstance.BASE_URL+"images/uploads/thumbs/"+product.getImage())
//                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return orders.size ();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, price, status, qty;

        MyViewHolder(View itemView) {
            super ( itemView );
            qty = (TextView) itemView.findViewById ( R.id.tv_total_items);
            price = (TextView) itemView.findViewById ( R.id.order_total_price);
            //unitMeasure = (TextView) itemView.findViewById ( R.id.tvUnitMeasure );
//            price = (TextView) itemView.findViewById ( R.id.tvPrice );
            //hotel = (TextView) itemView.findViewById ( R.id.tvHotel );
//            imageView = (ImageView) itemView.findViewById ( R.id.tvImage );
            //  sellingStatus = (TextView) view.findViewById ( R.id.sellingStatus );
        }
    }
}
