package com.example.karokojnr.nadab.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karokojnr.nadab.Items;
import com.example.karokojnr.nadab.R;
import com.example.karokojnr.nadab.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private ArrayList<Product> productList;

    public ItemsAdapter(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public ItemsAdapter(List<Product> productList, Items items) {
        this.productList = (ArrayList<Product>) productList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_list_row, parent, false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get ( position );
        holder.name.setText(product.getName());
        holder.unitMeasure.setText(product.getUnitMeasure());
        holder.price.setText(product.getPrice());
        holder.hotel.setText(product.getHotel());
        holder.image.setText(product.getImage());
    }

    @Override
    public int getItemCount() {
        return productList.size ();
    }

     class MyViewHolder extends RecyclerView.ViewHolder {
         TextView name, unitMeasure, price, hotel, image, sellingStatus;

         MyViewHolder(View itemView) {
            super ( itemView );
            name = (TextView) itemView.findViewById ( R.id.tvName );
            unitMeasure = (TextView) itemView.findViewById ( R.id.tvUnitMeasure );
            price = (TextView) itemView.findViewById ( R.id.tvPrice );
            hotel = (TextView) itemView.findViewById ( R.id.tvHotel );
            image = (TextView) itemView.findViewById ( R.id.tvImage );
          //  sellingStatus = (TextView) view.findViewById ( R.id.sellingStatus );
        }
    }

}