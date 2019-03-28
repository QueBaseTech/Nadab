package com.example.karokojnr.nadab_hotels;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

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
    private SearchView searchView = null;
    private SearchView.OnQueryTextListener queryTextListener;


    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_meals, container, false );

        recyclerView = (RecyclerView) view.findViewById ( R.id.recycler_view );
        // white background notification bar
        whiteNotificationBar(recyclerView);
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
                i.putExtra(Constants.M_PRODUCT_ID, product.getId());
                i.putExtra(Constants.M_UNITMEASURE, product.getUnitMeasure());
                i.putExtra(Constants.M_STATUS, product.isSelling());
                startActivity ( i );
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ) );


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



        return view;

    }
    public void onCreate(Bundle savedInstanceState){
        super.onCreate ( savedInstanceState );
        setHasOptionsMenu ( true );
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

    //menu

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    Log.i("onQueryTextChange", newText);

                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Log.i("onQueryTextSubmit", query);

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                // Not implemented here
                return false;
            default:
                break;
        }
        searchView.setOnQueryTextListener(queryTextListener);
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



    private void search(SearchView searchView) {

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {



            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return true;
            }
        });
    }


}


