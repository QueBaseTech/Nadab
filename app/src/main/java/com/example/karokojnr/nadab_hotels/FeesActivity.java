package com.example.karokojnr.nadab_hotels;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.karokojnr.nadab_hotels.adapter.FeesAdapter;
import com.example.karokojnr.nadab_hotels.api.HotelService;
import com.example.karokojnr.nadab_hotels.api.RetrofitInstance;
import com.example.karokojnr.nadab_hotels.model.Fee;
import com.example.karokojnr.nadab_hotels.model.Fees;
import com.example.karokojnr.nadab_hotels.utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeesActivity extends AppCompatActivity {
    private ArrayList<Fee> fees = new ArrayList<>();
    private FeesAdapter adapter;
    private RecyclerView recyclerView;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        HotelService service = RetrofitInstance.getRetrofitInstance ().create ( HotelService.class );
        Call<Fees> call = service.getFees( SharedPrefManager.getInstance(this).getToken() );
        call.enqueue ( new Callback<Fees>() {
            @Override
            public void onResponse(Call<Fees> call, Response<Fees> response) {
                if(response.body().isSuccess()){
                    for (int i = 0; i < response.body().getFees().size(); i++) {
                        Log.wtf("FeesActivity", "onResponse: "+ response.body ().getFees ().get ( i ) );
                        fees.add ( response.body ().getFees().get(i) );
                    }
                }
                generateFees();
            }

            @Override
            public void onFailure(Call<Fees> call, Throwable t) {
                Toast.makeText ( getApplicationContext (), "Something went wrong...Please try later!", Toast.LENGTH_SHORT ).show ();
            }
        } );
    }

    private void generateFees() {
        recyclerView = (RecyclerView) findViewById ( R.id.fees_recyler_view );
        adapter = new FeesAdapter( fees, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter ( adapter );
        recyclerView.addOnItemTouchListener ( new RecyclerTouchListener ( this, recyclerView, new RecyclerTouchListener.ClickListener () {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        } ));
    }
}
