package com.example.karokojnr.nadab_hotels;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private Toolbar toolbar;
    private SearchView searchView;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate ( R.layout.fragment_meals, container, false );
        //setHasOptionsMenu(true);//Menu code.


        toolbar = (Toolbar) view.findViewById ( R.id.toolbar );
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);

        // toolbar fancy stuff
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.toolbar_title);

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
                i.putExtra(Constants.M_PRODUCT_ID, product.getHotel());
                i.putExtra(Constants.M_UNITMEASURE, product.getUnitMeasure());
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
        // Implementing ActionBar Search inside a fragment
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getActivity ().getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getActivity ().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchItem.setOnMenuItemClickListener ( new MenuItem.OnMenuItemClickListener () {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
             //   search(searchView);
                return false;
            }
        } );

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                adapter.getFilter().filter(query);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onBackPressed() {
        // close search view on back button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }*/

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


