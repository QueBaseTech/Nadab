package com.example.karokojnr.nadab_hotels.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab_hotels.R;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.OrderItem;
import com.example.karokojnr.nadab_hotels.model.Product;

import java.util.ArrayList;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.MyViewHolder> implements Filterable {

    private ArrayList<Product> productList;
    private ArrayList<Product> mFilteredList;
    Context context;


    public ItemsAdapter(ArrayList<Product> productList, Context context) {
        this.productList = productList;
        mFilteredList = productList;
        this.context = context;
    }

    /* Within the RecyclerView.Adapter class */

    // Clean all elements of the recycler
    public void clear() {
        productList.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(ArrayList<Product> list) {
        productList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_list_row, parent, false);
        return new MyViewHolder (view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product product = productList.get ( position );
        holder.name.setText(product.getName());
        holder.price.setText("Kshs " + product.getPrice());
        Glide.with(context)
                .load(RetrofitInstance.BASE_URL+"images/uploads/products/thumb_"+product.getImage())
                .into(holder.imageView);
    }

    //Search bar filter


    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = productList;
                } else {

                    ArrayList<Product> filteredList = new ArrayList<>();

                    for (Product product : productList) {

                        if (product.getName ().toLowerCase().contains(charString) || product.getPrice ().toLowerCase().contains(charString) || product.getUnitMeasure ().toLowerCase().contains(charString)) {

                            filteredList.add(product);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredList = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //End of Search filter

    @Override
    public int getItemCount() {
        //return productList.size ();
        return mFilteredList.size();

    }

     class MyViewHolder extends RecyclerView.ViewHolder {
         TextView name, unitMeasure, price, hotel, sellingStatus;
         ImageView imageView;

         MyViewHolder(View itemView) {
            super ( itemView );
            name = (TextView) itemView.findViewById ( R.id.tvName );
            //unitMeasure = (TextView) itemView.findViewById ( R.id.tvUnitMeasure );
            price = (TextView) itemView.findViewById ( R.id.tvPrice );
            //hotel = (TextView) itemView.findViewById ( R.id.tvHotel );
            imageView = (ImageView) itemView.findViewById ( R.id.tvImage );
          //  sellingStatus = (TextView) view.findViewById ( R.id.sellingStatus );
        }
    }

}