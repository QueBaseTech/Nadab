/*
package com.example.karokojnr.nadab;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.karokojnr.nadab.adapter.ItemsAdapter;
import com.example.karokojnr.nadab.api.HotelService;
import com.example.karokojnr.nadab.api.RetrofitInstance;
import com.example.karokojnr.nadab.model.Products;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MealsFragment extends Fragment {
    ItemsAdapter itemsAdapter;
    RecyclerView recyclerView;
    List<Products> productsList = new ArrayList<Products>();
    private OnFragmentInteractionListener listener;
    public static MealsFragment newInstance() {
        return new MealsFragment();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meals, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        itemsAdapter = new ItemsAdapter (productsList );
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(itemsAdapter);
        getProduct();
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    public void getProduct() {

//        // display a progress dialog
        final ProgressDialog progressDialog = new ProgressDialog(getActivity ());
        progressDialog.setCancelable(false); // set cancelable to false
        progressDialog.setMessage("Please Wait"); // set message
        progressDialog.show(); // show progress dialog
        //made changes
        HotelService apiInterface = RetrofitInstance.getRetrofitInstance ().create(HotelService.class);
        Call <Products> call = apiInterface.getProduct ();
        call.enqueue(new Callback <Products>() {
            @Override
            public void onResponse(Call <Products> call, Response <Products> response) {
                if (response==null){
                    Toast.makeText(getActivity(), "Somthing Went Wrong...!!", Toast.LENGTH_SHORT).show();


                    */
/* Edited *//*

//                }else{
//                    for (Products products: response.body ()){
//                        productsList.add(products);
//                    }

                    Log.i("RESPONSE: ", ""+response.toString());
                }
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call <Products> call, Throwable t) {
                Toast.makeText(getActivity(), "Unable to fetch json: " + t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("ERROR: ", t.getMessage());
            }
        });
    }
    public interface OnFragmentInteractionListener {
    }
}*/
