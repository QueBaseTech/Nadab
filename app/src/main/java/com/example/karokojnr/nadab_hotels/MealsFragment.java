package com.example.karokojnr.nadab_hotels;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karokojnr.nadab_hotels.adapter.ItemsAdapter;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Product;
import com.example.karokojnr.nadab_hotels.model.Products;
import com.example.karokojnr.nadab_hotels.utils.Constants;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MealsFragment extends Fragment {
    private ItemsAdapter adapter;
    RecyclerView recyclerView;
    private List<Product> productList = new ArrayList<> ();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_meals, container, false );

        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<Products> call = service.getProducts ( SharedPrefManager.getInstance(getContext()).getToken() );
        call.enqueue ( new Callback<Products> () {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                for (int i = 0; i < response.body ().getProductArrayList ().size (); i++) {
                    productList.add ( response.body ().getProductArrayList ().get ( i ) );
                }
                generateProductsList ( response.body ().getProductArrayList () );
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText ( getActivity (), "Something went wrong...Please try later!", Toast.LENGTH_SHORT ).show ();
            }
        } );


        recyclerView = (RecyclerView) view.findViewById ( R.id.recycler_view );
        recyclerView.addOnItemTouchListener ( new RecyclerTouchListener ( getActivity (), recyclerView, new RecyclerTouchListener.ClickListener () {
            @Override
            public void onClick(View view, int position) {
                Product product = productList.get ( position );
                Toast.makeText ( getActivity (), product.getName () + " is selected!", Toast.LENGTH_SHORT ).show ();
                Intent i = new Intent ( getContext (), EditMeal.class );
                i.putExtra(Constants.M_NAME, product.getName());
                i.putExtra(Constants.M_PRICE, product.getPrice());
                i.putExtra(Constants.M_IMAGE, product.getImage());
                i.putExtra(Constants.M_HOTEL_ID, product.getHotel());
                i.putExtra(Constants.M_PRODUCT_ID, product.getHotel());
                i.putExtra(Constants.M_UNITMEASURE, product.getUnitMeasure());
                startActivity ( i );
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );

        return view;

    }

    private void generateProductsList(ArrayList<Product> empDataList) {
        adapter = new ItemsAdapter ( empDataList, getContext() );

        if (getContext ().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager (getContext (), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager (getContext (), 4));
        }

        recyclerView.setAdapter ( adapter );
    }

}


