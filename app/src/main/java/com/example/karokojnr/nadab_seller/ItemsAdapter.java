package com.example.karokojnr.nadab_seller;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private List<Product> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView productName, quantity, price;


        public MyViewHolder(View view) {
            super ( view );
            productName = (TextView) view.findViewById ( R.id.productName );

            quantity = (TextView) view.findViewById ( R.id.quantity );

            price = (TextView) view.findViewById ( R.id.price );
        }
    }


    public ItemsAdapter(List<Product> moviesList) {
        this.productList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.items_list_row, parent, false );

        return new MyViewHolder ( itemView );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get ( position );
        holder.productName.setText ( product.getProductName () );
        holder.quantity.setText ( product.getQuantity () );
        holder.price.setText ( product.getPrice () );
    }

    @Override
    public int getItemCount() {
            return productList.size ();

    }

}