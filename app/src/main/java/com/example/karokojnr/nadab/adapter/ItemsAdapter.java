package com.example.karokojnr.nadab.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karokojnr.nadab.Items;
import com.example.karokojnr.nadab.R;
import com.example.karokojnr.nadab.model.Products;

import java.util.ArrayList;
import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private ArrayList<Products> productList;

    public ItemsAdapter(ArrayList<Products> productList) {
        this.productList = productList;
    }

    public ItemsAdapter(List<Products> productList, Items items) {
        this.productList = (ArrayList<Products>) productList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_list_row, parent, false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       // Products products = productList.get ( position );

        holder.name.setText (  productList.get(position).getName () );
        holder.unitMeasure.setText (  productList.get ( position ).getUnitMeasure () );
        holder.price.setText (  productList.get ( position ).getPrice () );
        holder.hotel.setText (  productList.get ( position ).getHotel () );
        holder.image.setText (  productList.get ( position ).getImage () );
        //holder.sellingStatus.setText (  products.getSellingStatus () );
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