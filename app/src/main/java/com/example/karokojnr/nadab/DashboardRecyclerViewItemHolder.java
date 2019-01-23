package com.example.karokojnr.nadab;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.karokojnr.nadab.orders.OrderList;

public class DashboardRecyclerViewItemHolder extends RecyclerView.ViewHolder {

    private TextView name = null;

    private ImageView imageView = null;
    private final Context context;


    public DashboardRecyclerViewItemHolder(@NonNull View itemView) {
        super ( itemView );
        context = itemView.getContext();
        itemView.setClickable(true);
        itemView.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                final Intent intent;

                switch (getAdapterPosition ()) {
                    case 0:
                        intent = new Intent ( context, AddMeals.class );
                        break;

                    case 1:
                        intent = new Intent ( context, ProfileActivity.class );
                        break;
                    case 2:
                        intent = new Intent(context, OrderList.class);
                        break;
                    default:
                        intent = new Intent ( context, HomeActivity.class );
                        break;
                }
                context.startActivity ( intent );
            }
        } );

            if (itemView != null) {
                name = (TextView) itemView.findViewById ( R.id.tvName );

                imageView = (ImageView) itemView.findViewById ( R.id.tvImage );
            }

    }

    public TextView getItemName() {
        return name;
    }

    public ImageView getImageView() {
        return imageView;
    }
}
