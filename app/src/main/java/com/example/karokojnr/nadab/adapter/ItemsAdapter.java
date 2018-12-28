package com.example.karokojnr.nadab.adapter;


import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.karokojnr.nadab.R;
import com.example.karokojnr.nadab.model.Products;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> {

    private List<Products> productList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, unitMeasure, price, hotel, image, sellingStatus;


        public MyViewHolder(View view) {
            super ( view );
            name = (TextView) view.findViewById ( R.id.productName );

            unitMeasure = (TextView) view.findViewById ( R.id.quantity );

            price = (TextView) view.findViewById ( R.id.price );
            hotel = (TextView) view.findViewById ( R.id.hotel );
            image = (TextView) view.findViewById ( R.id.image );
            sellingStatus = (TextView) view.findViewById ( R.id.sellingStatus );
        }
    }


    public ItemsAdapter(List<Products> productList, FragmentActivity activity) {
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.items_list_row, parent, false );

        return new MyViewHolder ( itemView );
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Products products = productList.get ( position );
        holder.name.setText (  products.getName () );
        holder.unitMeasure.setText (  products.getUnitMeasure () );
        holder.price.setText (  products.getPrice () );
        holder.hotel.setText (  products.getHotel () );
        holder.image.setText (  products.getImage () );
        //holder.sellingStatus.setText (  products.getSellingStatus () );
    }

    @Override
    public int getItemCount() {
            return productList.size ();

    }

}