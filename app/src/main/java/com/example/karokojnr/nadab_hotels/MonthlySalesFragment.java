package com.example.karokojnr.nadab_hotels;



import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.karokojnr.nadab_hotels.adapter.ItemsAdapter;
import com.example.karokojnr.nadab_hotels.model.Product;

import java.util.ArrayList;
import java.util.List;

public class MonthlySalesFragment extends Fragment {

    RecyclerView recyclerView;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_monthly_sales, container, false );

        recyclerView = (RecyclerView) view.findViewById ( R.id.recycler_view );
        // white background notification bar
        whiteNotificationBar(recyclerView);


        return view;

    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate ( savedInstanceState );
        setHasOptionsMenu ( true );
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    private void whiteNotificationBar(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
            getActivity ().getWindow().setStatusBarColor(Color.WHITE);
        }
    }


}


