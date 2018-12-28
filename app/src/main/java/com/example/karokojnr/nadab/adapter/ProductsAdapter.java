package com.example.karokojnr.nadab.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.karokojnr.nadab.R;
import com.example.karokojnr.nadab.model.Products;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {
    List<Products> productList;
    Context context;
    public ProductsAdapter(List<Products> productsList, Context context) {
        this.productList = productsList;
        this.context = context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_list_row, parent, false);
        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Products products = productList.get(position);
        holder.tvName.setText(products.getName ());
        Glide.with(context)
                .load(products.getImage())
                .centerCrop()
                .into(holder.imageView);
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;
        public TextView tvUnitMeasure;
        public TextView tvPrice;
        public ImageView imageView;
        public MyViewHolder(View view) {
            super(view);
            tvName = (TextView) view.findViewById(R.id.tvName);
            tvUnitMeasure = (TextView) view.findViewById(R.id.tvUnitMeasure);
            tvPrice = (TextView) view.findViewById(R.id.tvPrice);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }
    }
}