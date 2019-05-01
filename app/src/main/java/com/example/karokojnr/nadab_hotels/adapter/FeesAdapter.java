package com.example.karokojnr.nadab_hotels.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.karokojnr.nadab_hotels.R;
import com.example.karokojnr.nadab_hotels.model.Fee;

import java.util.ArrayList;


public class FeesAdapter extends RecyclerView.Adapter<FeesAdapter.MyViewHolder> {

    private ArrayList<Fee> feeList;

    Context context;


    public FeesAdapter(ArrayList<Fee> feesList, Context context) {
        this.feeList = feesList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.fee_item , parent, false);
        return new MyViewHolder (view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Fee fee = feeList.get ( position );

        holder.day.setText(fee.getDay());
        holder.numberOfOrders.setText(Integer.toString(fee.getNumberOfOrders()));
        holder.totalFees.setText("Ksh " + fee.getTotal());
//        holder.time.setText("Date: "+sdf);
//        holder.feeStatus.setText(fee.getOrderStatus());
    }

    @Override
    public int getItemCount() {
        return feeList.size ();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
         TextView day, numberOfOrders, totalFees, time, orderStatus;

         MyViewHolder(View itemView) {
            super ( itemView );
            day = (TextView) itemView.findViewById ( R.id.day );
            numberOfOrders = (TextView) itemView.findViewById ( R.id.numberOfOrders );
            totalFees = (TextView) itemView.findViewById ( R.id.total_fees);
//            time = itemView.findViewById ( R.id.time_ordered );
//            orderStatus = itemView.findViewById ( R.id.order_status );
        }
    }

}