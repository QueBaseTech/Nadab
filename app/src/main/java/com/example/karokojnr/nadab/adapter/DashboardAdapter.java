package com.example.karokojnr.nadab.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.example.karokojnr.nadab.DashboardFragment;
import com.example.karokojnr.nadab.DashboardRecyclerViewItem;
import com.example.karokojnr.nadab.DashboardRecyclerViewItemHolder;
import com.example.karokojnr.nadab.HomeActivity;
import com.example.karokojnr.nadab.Items;
import com.example.karokojnr.nadab.ProfileActivity;
import com.example.karokojnr.nadab.R;
import com.example.karokojnr.nadab.api.RetrofitInstance;
import com.example.karokojnr.nadab.model.Dashboard;
import com.example.karokojnr.nadab.model.Dashboard;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter  extends RecyclerView.Adapter<DashboardRecyclerViewItemHolder> implements View.OnClickListener{

    private List<DashboardRecyclerViewItem> dashboardList;
    private Context context;

    public DashboardAdapter(List<DashboardRecyclerViewItem> dashboardList) {
        this.dashboardList = dashboardList;

    }



    @Override
    public DashboardRecyclerViewItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.custom_list_dashboard, parent, false);
        final DashboardRecyclerViewItemHolder holder = new DashboardRecyclerViewItemHolder ( itemView );

        // Get  title text view object.
        final TextView name = (TextView)itemView.findViewById(R.id.tvName);
        // Get  image view object.
        final ImageView imageView = (ImageView)itemView.findViewById(R.id.tvImage);
        // When click the image.
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get  title text.
                String title = name.getText ().toString ();
                // Create a snackbar and show it.
                Snackbar snackbar = Snackbar.make ( imageView, "You click " + title + " image", Snackbar.LENGTH_LONG );
                snackbar.show ();
              //  int position = holder.getAdapterPosition ();
               // final Intent intent;
                /*switch (position){
                    case 1:
                        intent =  new Intent(context, ProfileActivity.class);
                        break;

                    case 2:
                        intent =  new Intent(context, Items.class);
                        break;

                    default:
                        intent =  new Intent(context, HomeActivity.class);
                        break;
                }
                context.startActivity(intent);*/
                /*if (getAdapterPosition() == 1) {
                     intent = new Intent (context, ProfileActivity.class );
                } else  {
                    intent = new Intent ( context, Items.class );
                }*//*else {
                    intent =  new Intent(context, HomeActivity.class);
                }*//*

                context.startActivity(intent);*/
            }
        });

        // Create and return our custom Car Recycler View Item Holder object.
        //DashboardRecyclerViewItemHolder ret = new DashboardRecyclerViewItemHolder (itemView);
        return new DashboardRecyclerViewItemHolder ( itemView );
    }

    @Override
    public void onBindViewHolder(DashboardRecyclerViewItemHolder holder, int position) {
        if(dashboardList!=null) {
            // Get  item dto in list.
            DashboardRecyclerViewItem dashboardItem = dashboardList.get(position);

            if(dashboardItem != null) {
                // Set  item title.
                holder.getItemName ().setText(dashboardItem.getName ());
                // Set  image resource id.
                holder.getImageView ().setImageResource(dashboardItem.getDashboardImage ());

            }


        }
    }

    @Override
    public int getItemCount() {
        int ret = 0;
        if(dashboardList!=null)
        {
            ret = dashboardList.size();
        }
        return ret;
    }

    @Override
    public void onClick(View v ) {

    }

    public interface mClickListener {
    public void onClick(View v , int position);

    }
}